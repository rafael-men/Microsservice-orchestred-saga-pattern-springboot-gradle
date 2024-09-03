package br.com.microservices.orchestrated.orderservice.Core.Consumer;

import br.com.microservices.orchestrated.orderservice.Core.Service.EventService;
import br.com.microservices.orchestrated.orderservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);
    private final EventService service;
    private final JsonUtils jsonUtils;


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.notify-ending}"
    )
    public void ConsumerNotifyEndingTopic(String payload) {
        log.info("Receiving ending notification event.",payload);
        var event = jsonUtils.toEvent(payload);
        service.notifyEnding(event);
    }
}
