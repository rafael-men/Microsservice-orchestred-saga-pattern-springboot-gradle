package br.com.microservices.orchestrated.inventoryservice.Core.Repository;

import br.com.microservices.orchestrated.inventoryservice.Core.Models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    static Optional<Inventory> findByProductCode(String productCode) {
        return null;
    }
}
