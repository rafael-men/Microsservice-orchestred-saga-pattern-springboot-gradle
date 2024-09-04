package br.com.microservices.orchestrated.productvalidationservice.Core.Dto;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    @Id
    private String id;
    private List<OrderProducts> products;
    private LocalDateTime createdAt;
    private String transactionId;
    private double totalAmount;
    private int totalItems;


    private Order(OrderBuilder builder) {
        this.id = builder.id;
        this.products = builder.products;
        this.createdAt = builder.createdAt;
        this.transactionId = builder.transactionId;
        this.totalAmount = builder.totalAmount;
        this.totalItems = builder.totalItems;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderProducts> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProducts> products) {
        this.products = products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private String id;
        private List<OrderProducts> products;
        private LocalDateTime createdAt;
        private String transactionId;
        private double totalAmount;
        private int totalItems;

        public OrderBuilder id(String id) {
            this.id = id;
            return this;
        }

        public OrderBuilder products(List<OrderProducts> products) {
            this.products = products;
            return this;
        }

        public OrderBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public OrderBuilder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderBuilder totalItems(int totalItems) {
            this.totalItems = totalItems;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
