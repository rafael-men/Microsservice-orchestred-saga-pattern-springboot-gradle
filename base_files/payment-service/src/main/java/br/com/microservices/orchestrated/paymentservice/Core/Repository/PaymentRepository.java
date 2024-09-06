package br.com.microservices.orchestrated.paymentservice.Core.Repository;

import br.com.microservices.orchestrated.paymentservice.Core.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    static Boolean existsByOrderIdAndTransactionId(String orderId, String transactionId) {
        return null;
    }

    Optional<Payment> findByOrderIdAndTransactionId(String orderId,String transactionId);
}
