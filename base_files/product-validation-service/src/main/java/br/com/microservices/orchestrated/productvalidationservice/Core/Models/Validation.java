package br.com.microservices.orchestrated.productvalidationservice.Core.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Validation")
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public Validation(Integer id, String orderId, String transactionId, boolean success, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public static ValidationBuilder builder() {
        return new ValidationBuilder();
    }

    public static class ValidationBuilder {
        private Integer id;
        private String orderId;
        private String transactionId;
        private boolean success;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ValidationBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ValidationBuilder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public ValidationBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public ValidationBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public ValidationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ValidationBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Validation build() {
            return new Validation(id, orderId, transactionId, success, createdAt, updatedAt);
        }
    }

    @PrePersist
    public void prePersist() {
        var now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
