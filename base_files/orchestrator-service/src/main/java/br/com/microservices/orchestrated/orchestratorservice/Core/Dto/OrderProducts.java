package br.com.microservices.orchestrated.orchestratorservice.Core.Dto;

import lombok.Data;

@Data
public class OrderProducts {

    private Product product;
    private int quantity;

    public OrderProducts(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
