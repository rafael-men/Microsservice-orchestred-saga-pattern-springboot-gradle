package br.com.microservices.orchestrated.inventoryservice.Core.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OrderInventory")
public class OrderInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private Integer orderQuantity;

    @Column(nullable = false)
    private Integer oldQuantity;

    @Column(nullable = false)
    private Integer newQuantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public OrderInventory() {
    }

    public OrderInventory(Integer id, Inventory inventory, String orderId, String transactionId, Integer orderQuantity, Integer oldQuantity, Integer newQuantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.inventory = inventory;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.orderQuantity = orderQuantity;
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
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

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(Integer oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
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


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private Integer id;
        private Inventory inventory;
        private String orderId;
        private String transactionId;
        private Integer orderQuantity;
        private Integer oldQuantity;
        private Integer newQuantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder inventory(Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder orderQuantity(Integer orderQuantity) {
            this.orderQuantity = orderQuantity;
            return this;
        }

        public Builder oldQuantity(Integer oldQuantity) {
            this.oldQuantity = oldQuantity;
            return this;
        }

        public Builder newQuantity(Integer newQuantity) {
            this.newQuantity = newQuantity;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }


        public OrderInventory build() {
            return new OrderInventory(id, inventory, orderId, transactionId, orderQuantity, oldQuantity, newQuantity, createdAt, updatedAt);
        }
    }
}
