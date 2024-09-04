package br.com.microservices.orchestrated.productvalidationservice.Core.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String code;

    public Product(Integer id, String code) {
        this.id = id;
        this.code = code;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Integer id;
        private String code;

        public ProductBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ProductBuilder code(String code) {
            this.code = code;
            return this;
        }

        public Product build() {
            return new Product(id, code);
        }
    }
}
