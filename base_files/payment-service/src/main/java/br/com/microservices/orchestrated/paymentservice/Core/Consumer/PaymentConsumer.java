package br.com.microservices.orchestrated.paymentservice.Core.Consumer;


import br.com.microservices.orchestrated.paymentservice.Core.Service.PaymentService;
import br.com.microservices.orchestrated.paymentservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);
    private final PaymentService paymentService;
    private final JsonUtils jsonUtils;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-success}"
    )
    public void ConsumerSuccessEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
        paymentService.realizePayment(event);
    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-fail}"
    )
    public void ConsumerFailEvent(String payload) {
        log.info("Receiving Rollback event.",payload);
        var event = jsonUtils.toEvent(payload);
        paymentService.realizeRefund(event);
    }

}
