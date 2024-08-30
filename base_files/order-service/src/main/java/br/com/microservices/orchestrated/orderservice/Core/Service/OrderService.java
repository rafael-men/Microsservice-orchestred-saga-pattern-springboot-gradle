package br.com.microservices.orchestrated.orderservice.Core.Service;

import br.com.microservices.orchestrated.orderservice.Core.Document.Event;
import br.com.microservices.orchestrated.orderservice.Core.Document.Order;
import br.com.microservices.orchestrated.orderservice.Core.Dto.OrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private Order createOrder(OrderRequest orderRequest) {
        var order = Order.builder().products
    }

    private Event createPayload(Order order) {

    }
}
