package br.com.microservices.orchestrated.productvalidationservice.Core.Repository;

import br.com.microservices.orchestrated.productvalidationservice.Core.Models.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation,Integer> {
    Boolean existsByOrderIdAndTransactionId(String orderId, String transactionId);

    Optional<Validation> findByOrderIdAndTransactionId(String orderId, String transactionId);
}
