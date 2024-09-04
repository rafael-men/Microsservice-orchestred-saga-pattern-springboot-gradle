package br.com.microservices.orchestrated.productvalidationservice.Core.Repository;

import br.com.microservices.orchestrated.productvalidationservice.Core.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Boolean existsByCode(String code);
}
