package br.com.microservices.orchestrated.paymentservice.Core.Service;

import br.com.microservices.orchestrated.paymentservice.Config.Exception.ValidationException;
import br.com.microservices.orchestrated.paymentservice.Core.Dto.Event;
import br.com.microservices.orchestrated.paymentservice.Core.Dto.History;
import br.com.microservices.orchestrated.paymentservice.Core.Enums.EPaymentStatus;
import br.com.microservices.orchestrated.paymentservice.Core.Models.Payment;
import br.com.microservices.orchestrated.paymentservice.Core.Producer.KafkaProducer;
import br.com.microservices.orchestrated.paymentservice.Core.Repository.PaymentRepository;
import br.com.microservices.orchestrated.paymentservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.microservices.orchestrated.paymentservice.Core.Enums.EPaymentStatus.SUCCESS;
import static br.com.microservices.orchestrated.paymentservice.Core.Enums.ESagaStatus.FAIL;
import static br.com.microservices.orchestrated.paymentservice.Core.Enums.ESagaStatus.ROLLBACK_PENDING;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {
    private static final String CURRENT_SOURCE = "PAYMENT_SERVICE";
    private static final Double REDUCE_SUM_VALUE = 0.0;
    private static final Double MIN_AMOUNT_VALUE = 0.1;
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final JsonUtils jsonUtils;
    private final KafkaProducer producer;
    private final PaymentRepository repository;

    public void realizePayment(Event event) {
        try {
            checkCurrentValidation(event);
            createPendingPayment(event);
            var payment = findByOrderIdAndTransactionId(event);
            validateAmount(payment.getTotalAmount());
            changePaymentToSuccess(payment);
            handleSuccess(event);
        } catch (Exception e) {
            log.error("Error trying to validate products.", e);
            handleFailCurrentNotExecuted(event, e.getMessage());
        }
        producer.sendEvent(jsonUtils.toJson(event));
    }

    private void createPendingPayment(Event event) {
        var totalAmount = calculateAmount(event);
        var totalItems = calculateTotalItems(event);

        var payment = Payment.builder()
                .orderId(event.getPayload().getId())
                .transactionId(event.getTransactionId())
                .totalAmount(totalAmount)
                .totalItems(totalItems)
                .build();
        repository.save(payment);
        setEventAmountItems(event, payment);
    }

    private double calculateAmount(Event event) {
        return event.getPayload().getProducts().stream()
                .map(product -> product.getQuantity() * product.getProduct().getUnitValue())
                .reduce(REDUCE_SUM_VALUE, Double::sum);
    }

    private int calculateTotalItems(Event event) {
        return event.getPayload().getProducts().stream()
                .map(orderProducts -> orderProducts.getQuantity())
                .reduce(REDUCE_SUM_VALUE.intValue(), Integer::sum);
    }

    private void setEventAmountItems(Event event, Payment payment) {
        event.getPayload().setTotalAmount(payment.getTotalAmount());
        event.getPayload().setTotalItems(payment.getTotalItems());
    }

    private void validateAmount(double amount) {
        if (amount < MIN_AMOUNT_VALUE) {
            throw new ValidationException("Minimum amount available is " + MIN_AMOUNT_VALUE);
        }
    }

    private void changePaymentToSuccess(Payment payment) {
        payment.setStatus(SUCCESS);
        repository.save(payment);
    }

    private void handleSuccess(Event event) {
        event.setStatus(String.valueOf(ROLLBACK_PENDING));
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Products validated successfully.");
    }

    private void addHistory(Event event, String message) {
        var history = History.builder()
                .source(event.getSource())
                .status(event.getStatus())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(String.valueOf(ROLLBACK_PENDING));
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Failed to realize payment: " + message);
    }

    public void realizeRefund(Event event) {
        changePaymentStatusToRefund(event);
        event.setStatus(String.valueOf(FAIL));
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Rollback done.");
        producer.sendEvent(jsonUtils.toJson(event));
    }

    private void changePaymentStatusToRefund(Event event) {
        var payment = findByOrderIdAndTransactionId(event);
        payment.setStatus(EPaymentStatus.REFUND);
        setEventAmountItems(event, payment);
        repository.save(payment);
    }

    private Payment findByOrderIdAndTransactionId(Event event) {
        return repository.findByOrderIdAndTransactionId(event.getPayload().getId(), event.getTransactionId())
                .orElseThrow(() -> new ValidationException("Payment not found by OrderID and TransactionID."));
    }

    private void checkCurrentValidation(Event event) {
        if (PaymentRepository.existsByOrderIdAndTransactionId(event.getPayload().getId(), event.getTransactionId())) {
            throw new ValidationException("There is another transactionId for this validation.");
        }
    }
}
