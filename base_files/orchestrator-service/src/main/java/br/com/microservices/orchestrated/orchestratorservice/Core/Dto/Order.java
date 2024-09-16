package br.com.microservices.orchestrated.orchestratorservice.Core.Dto;


import java.time.LocalDateTime;
import java.util.List;


public class Order {

    private String id;
    private List<OrderProducts> products;
    private LocalDateTime createdAt;
    private String transactionId;
    private double totalAmount;
    private int totalItems;

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

    public Order(String id, List<OrderProducts> products, LocalDateTime createdAt, String transactionId, double totalAmount, int totalItems) {
        this.id = id;
        this.products = products;
        this.createdAt = createdAt;
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }
}
