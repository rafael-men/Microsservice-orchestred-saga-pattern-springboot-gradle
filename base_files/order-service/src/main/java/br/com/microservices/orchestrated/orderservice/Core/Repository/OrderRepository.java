package br.com.microservices.orchestrated.orderservice.Core.Repository;

import br.com.microservices.orchestrated.orderservice.Core.Document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
