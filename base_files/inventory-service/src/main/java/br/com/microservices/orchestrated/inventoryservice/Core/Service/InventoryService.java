package br.com.microservices.orchestrated.inventoryservice.Core.Service;

import br.com.microservices.orchestrated.inventoryservice.Config.Exceptions.ValidationException;
import br.com.microservices.orchestrated.inventoryservice.Core.Dto.Event;
import br.com.microservices.orchestrated.inventoryservice.Core.Dto.History;
import br.com.microservices.orchestrated.inventoryservice.Core.Dto.Order;
import br.com.microservices.orchestrated.inventoryservice.Core.Dto.OrderProducts;
import br.com.microservices.orchestrated.inventoryservice.Core.Models.Inventory;
import br.com.microservices.orchestrated.inventoryservice.Core.Models.OrderInventory;
import br.com.microservices.orchestrated.inventoryservice.Core.Producer.KafkaProducer;
import br.com.microservices.orchestrated.inventoryservice.Core.Repository.InventoryRepository;
import br.com.microservices.orchestrated.inventoryservice.Core.Repository.OrderInventoryRepository;
import br.com.microservices.orchestrated.inventoryservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.microservices.orchestrated.inventoryservice.Core.Enums.ESagaStatus.*;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryService {

    private static final String CURRENT_SOURCE = "INVENTORY_SERVICE";
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final JsonUtils jsonUtils;
    private final KafkaProducer producer;
    private final InventoryRepository inventoryRepository;
    private final OrderInventoryRepository orderInventoryRepository;

    public void updateInventory(Event event) {
        try{
            checkCurrentValidation(event);
            createOrderInventory(event);
            updateInventory(event.getPayload());
            handleSuccess(event);
        }
        catch (Exception e) {
            log.error("Error trying to make payment.");
        }
        producer.sendEvent(jsonUtils.toJson(event));
    }

    private void handleSuccess(Event event) {
        event.setStatus(String.valueOf(SUCCESS));
        event.setSource(CURRENT_SOURCE);
        addHistory(event,"Message updated successfully ");
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
    
    private void handleFailCurrentNotExecuted(Event event,String message) {
        event.setStatus(String.valueOf(ROLLBACK_PENDING));
        event.setSource(CURRENT_SOURCE);
        addHistory(event,"Fail to update inventory: ".concat(message));
    }

    public void rollbackInventory(Event event) {
        event.setStatus(String.valueOf(FAIL));
        event.setSource(CURRENT_SOURCE);
        try {
            returnEventToPreviousValues(event);
            addHistory(event,"Rollback executed");
        }
        catch (Exception ex) {
            addHistory(event,"Rollback not executed.".concat(ex.getMessage()));
        }
        producer.sendEvent(jsonUtils.toJson(event));
    }

    private void returnEventToPreviousValues(Event event) {
        orderInventoryRepository
                .findByOrderIdAndTransactionId(event.getPayload().getId(), event.getTransactionId())
                .forEach(orderInventory -> {
                    var inventory = orderInventory.getInventory();
                    inventory.setAvaliable(orderInventory.getOldQuantity());
                    inventoryRepository.save(inventory);
                    log.info("Restored inventory");
                });
    }

    private void createOrderInventory(Event event) {
        event
                .getPayload()
                .getProducts()
                .forEach(products -> {
                    var inventory = findInventoryByProductCode(products.getProduct().getCode());
                    var orderInventory = createOrderInventory(event,products,inventory);
                    orderInventoryRepository.save(orderInventory);
                });
    }

    private OrderInventory createOrderInventory (Event event, OrderProducts products, Inventory inventory) {
        return OrderInventory
                .builder()
                .inventory(inventory)
                .oldQuantity(inventory.getAvaliable())
                .orderQuantity(products.getQuantity())
                .newQuantity(inventory.getAvaliable() - products.getQuantity())
                .orderId(event.getPayload().getId())
                .transactionId(event.getTransactionId())
                .build();
    }

    public void updateInventory(Order order) {
        order
                .getProducts()
                .forEach(products -> {
                    var inventory = findInventoryByProductCode(products.getProduct().getCode());
                    checkInventory(inventory.getAvaliable(), products.getQuantity());
                    inventory.setAvaliable(inventory.getAvaliable() - products.getQuantity());
                    inventoryRepository.save(inventory);

                });
    }

    private void checkInventory(int avaliable, int orderQuantity) {
        if(orderQuantity > avaliable) {
            throw new ValidationException("Product out of Stock.");
        }
    }

    private void checkCurrentValidation(Event event) {
        if(OrderInventoryRepository.existsByOrderIdAndTransactionId(event.getOrderId(), event.getTransactionId())) {
            throw new ValidationException("There is another transactionId for this validation");
        }
    }

    private Inventory findInventoryByProductCode(String productCode) {
        return InventoryRepository
                .findByProductCode(productCode)
                .orElseThrow(()-> new ValidationException("Inventory not found by informed product."));
    }


}
