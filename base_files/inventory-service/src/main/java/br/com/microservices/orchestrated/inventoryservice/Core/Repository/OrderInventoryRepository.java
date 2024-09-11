package br.com.microservices.orchestrated.inventoryservice.Core.Repository;

import br.com.microservices.orchestrated.inventoryservice.Core.Models.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Integer> {

    static Boolean existsByOrderIdAndTransactionId(String orderId, String transactionId) {
        return null;
    }

   List<OrderInventory> findByOrderIdAndTransactionId(String orderId, String transactionId);
}
