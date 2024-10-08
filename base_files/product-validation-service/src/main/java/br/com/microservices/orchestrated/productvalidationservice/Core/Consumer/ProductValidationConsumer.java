package br.com.microservices.orchestrated.productvalidationservice.Core.Consumer;


import br.com.microservices.orchestrated.productvalidationservice.Core.Service.ProductValidationService;
import br.com.microservices.orchestrated.productvalidationservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductValidationConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductValidationConsumer.class);
    private final ProductValidationService productValidationService;
    private final JsonUtils jsonUtils;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-success}"
    )
    public void ConsumerSuccessEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
        productValidationService.validateExistingProducts(event);
    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-fail}"
    )
    public void ConsumerFailEvent(String payload) {
        log.info("Receiving Rollback event.",payload);
        var event = jsonUtils.toEvent(payload);
        productValidationService.rollbackEvent(event);
    }
}
