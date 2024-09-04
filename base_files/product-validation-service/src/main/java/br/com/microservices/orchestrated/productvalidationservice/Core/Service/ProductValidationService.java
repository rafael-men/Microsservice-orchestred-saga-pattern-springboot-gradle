package br.com.microservices.orchestrated.productvalidationservice.Core.Service;

import br.com.microservices.orchestrated.productvalidationservice.Config.Exception.ValidationException;
import br.com.microservices.orchestrated.productvalidationservice.Core.Dto.Event;
import br.com.microservices.orchestrated.productvalidationservice.Core.Dto.History;
import br.com.microservices.orchestrated.productvalidationservice.Core.Dto.OrderProducts;
import br.com.microservices.orchestrated.productvalidationservice.Core.Models.Validation;
import br.com.microservices.orchestrated.productvalidationservice.Core.Producer.KafkaProducer;
import br.com.microservices.orchestrated.productvalidationservice.Core.Repository.ProductRepository;
import br.com.microservices.orchestrated.productvalidationservice.Core.Repository.ValidationRepository;
import br.com.microservices.orchestrated.productvalidationservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.microservices.orchestrated.productvalidationservice.Core.Enums.ESagaStatus.*;


@Slf4j
@Service
@AllArgsConstructor
public class ProductValidationService {

    private static final Logger log = LoggerFactory.getLogger(ProductValidationService.class);

    private final String CURRENT_SOURCE = "PRODUCT_VALIDATION_SERVICE";


    private final JsonUtils jsonUtils;
    private final KafkaProducer producer;
    private final ProductRepository productRepository;
    private final ValidationRepository validationRepository;

    public void validateExistingProducts(Event event) {
        try {
            checkCurrentValidation(event);
            createValidation(event,true);
            handleSuccess(event);
        } catch (Exception e) {
            log.error("Error Validating Products", e);
            handleFailCurrentNotExecuted(event, e.getMessage());
        }
        producer.sendEvent(jsonUtils.toJson(event));
    }

    private void ValidateProductsInformed(Event event) {
        if (event.getPayload() == null || event.getPayload().getProducts() == null) {
            throw new ValidationException("Product List is Empty.");
        }
        if (event.getPayload().getId() == null || event.getPayload().getTransactionId() == null) {
            throw new ValidationException("Order ID and Transaction ID Must be Informed");
        }
    }

    private void checkCurrentValidation(Event event) {
        ValidateProductsInformed(event);
        if(validationRepository.existsByOrderIdAndTransactionId(event.getOrderId(), event.getTransactionId())) {
            throw new ValidationException("There is another transactionId for this validation.");
        }
        event.getPayload().getProducts().forEach(product -> {
            ValidateProductInformed(product);
        });
    }

    private void ValidateProductInformed(OrderProducts product) {
        if(product.getProduct() == null || product.getProduct().getCode() == null){
            throw new ValidationException("Product must be informed.");
        }
    }

    private void ValidateExistingProduct (String code) {
        if(!productRepository.existsByCode(code)) {
            throw new ValidationException("Product does not exist");
        }
    }

    private void createValidation(Event event,Boolean success) {
        var validation = Validation
                .builder()
                .orderId(event.getPayload().getId())
                .transactionId(event.getTransactionId())
                .success(success)
                .build();
        validationRepository.save(validation);

    }

    private void handleSuccess(Event event) {
        event.setStatus(String.valueOf(SUCCESS));
        event.getSource(CURRENT_SOURCE);
        addHistory(event,"Products Validated Successfully");
    }

    private void addHistory(Event event, String message) {
        var history = History
                .builder()
                .source(event.getSource(CURRENT_SOURCE))
                .status(event.getStatus())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(String.valueOf(ROLLBACK_PENDING));
        event.getSource(CURRENT_SOURCE);
        addHistory(event,message);
    }

    public void rollbackEvent(Event event) {
        changeValidationToFail(event);
        event.setStatus(String.valueOf(FAIL));
        event.getSource(CURRENT_SOURCE);
        addHistory(event,"Rollback executed on product validation.");
        producer.sendEvent(jsonUtils.toJson(event));
    }

    private void changeValidationToFail(Event event) {
        validationRepository
                .findByOrderIdAndTransactionId(event.getPayload().getId(),event.getTransactionId())
                .ifPresentOrElse(validation -> {
                    validation.setSuccess(false);
                    validationRepository.save(validation);
                }, ()-> createValidation(event,false));
    }
}
