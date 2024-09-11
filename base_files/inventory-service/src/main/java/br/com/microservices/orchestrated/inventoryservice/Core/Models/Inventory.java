package br.com.microservices.orchestrated.inventoryservice.Core.Models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private Integer avaliable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Integer avaliable) {
        this.avaliable = avaliable;
    }

    public Inventory(String productCode, Integer id, Integer avaliable) {
        this.productCode = productCode;
        this.id = id;
        this.avaliable = avaliable;
    }
}
