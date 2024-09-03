package br.com.microservices.orchestrated.orderservice.Core.Service;


import br.com.microservices.orchestrated.orderservice.Core.Document.Event;
import br.com.microservices.orchestrated.orderservice.Core.Document.Order;
import br.com.microservices.orchestrated.orderservice.Core.Dto.OrderRequest;
import br.com.microservices.orchestrated.orderservice.Core.Producer.SagaProducer;
import br.com.microservices.orchestrated.orderservice.Core.Repository.OrderRepository;
import br.com.microservices.orchestrated.orderservice.Core.Utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final EventService eventService;
    private final SagaProducer producer;
    private final JsonUtils jsonUtils;


    private final OrderRepository repository;


    public Order createOrder(OrderRequest orderRequest) {
        var order = Order
                .builder()
                .products(orderRequest.getProducts())
                .createdAt(LocalDateTime.now())
                .transactionId(String.format("%s_%s",Instant.now().toEpochMilli(), UUID.randomUUID()))
                .build();
        repository.save(order);
        var event = createPayload(order);
        producer.sendEvent(jsonUtils.toJson(createPayload(order)));
        return order;
    }

    public Event createPayload(Order order) {
        var event = Event
                .builder()
                .setOrderId(order.getId())
                .setTransactionId(order.getTransactionId())
                .setPayload(order)
                .build();
        eventService.save(event);
        return event;
    }
}
