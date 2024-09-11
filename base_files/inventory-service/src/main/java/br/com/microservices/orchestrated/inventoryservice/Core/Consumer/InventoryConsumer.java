package br.com.microservices.orchestrated.inventoryservice.Core.Consumer;



import br.com.microservices.orchestrated.inventoryservice.Core.Service.InventoryService;
import br.com.microservices.orchestrated.inventoryservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class InventoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);
    private final JsonUtils jsonUtils;
    private final InventoryService inventoryService;



    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-success}"
    )
    public void ConsumerSuccessEvent(String payload) {
        log.info("Receiving event.",payload);
        var event = jsonUtils.toEvent(payload);
        inventoryService.updateInventory(event);
    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-fail}"
    )
    public void ConsumerFailEvent(String payload) {
        log.info("Receiving Rollback event.",payload);
        var event = jsonUtils.toEvent(payload);
        inventoryService.rollbackInventory(event);
    }

}
