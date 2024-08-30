package br.com.microservices.orchestrated.orchestratorservice.Core.Consumer;


import br.com.microservices.orchestrated.orchestratorservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SagaOrchestratorConsumer {

    private static final Logger log = LoggerFactory.getLogger(SagaOrchestratorConsumer.class);
    private final JsonUtils jsonUtils;


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.start-saga}"
    )
    public void ConsumerStartSagaEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.orchestrator}"
    )
    public void ConsumerOrchestratorEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-success}"
    )
    public void ConsumerFinishSuccessEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-fail}"
    )
    public void ConsumerFinishFailEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
    }
}
