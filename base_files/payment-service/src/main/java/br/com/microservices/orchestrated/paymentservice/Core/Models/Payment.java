package br.com.microservices.orchestrated.paymentservice.Core.Models;

import br.com.microservices.orchestrated.paymentservice.Core.Enums.EPaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private int totalItems;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public Payment() {
    }

    public Payment(Integer id, String orderId, String transactionId, int totalItems, double totalAmount, EPaymentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public EPaymentStatus getStatus(EPaymentStatus success) {
        return status;
    }

    public void setStatus(EPaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }



    public static class PaymentBuilder {
        private Integer id;
        private String orderId;
        private String transactionId;
        private int totalItems;
        private double totalAmount;
        private EPaymentStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PaymentBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public PaymentBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public PaymentBuilder totalItems(int totalItems) {
            this.totalItems = totalItems;
            return this;
        }

        public PaymentBuilder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public PaymentBuilder status(EPaymentStatus status) {
            this.status = status;
            return this;
        }

        public PaymentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PaymentBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Payment build() {
            return new Payment(id, orderId, transactionId, totalItems, totalAmount, status, createdAt, updatedAt);
        }
    }

    @PrePersist
    public void prePersist() {
        var now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        status = EPaymentStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

