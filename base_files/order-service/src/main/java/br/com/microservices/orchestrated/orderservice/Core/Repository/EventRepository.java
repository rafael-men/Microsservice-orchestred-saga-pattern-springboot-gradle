package br.com.microservices.orchestrated.orderservice.Core.Repository;

import br.com.microservices.orchestrated.orderservice.Core.Document.Event;
import br.com.microservices.orchestrated.orderservice.Core.Document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event,String> {
}
