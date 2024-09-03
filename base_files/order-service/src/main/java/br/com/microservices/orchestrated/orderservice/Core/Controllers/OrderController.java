package br.com.microservices.orchestrated.orderservice.Core.Controllers;


import br.com.microservices.orchestrated.orderservice.Core.Document.Order;
import br.com.microservices.orchestrated.orderservice.Core.Dto.OrderRequest;
import br.com.microservices.orchestrated.orderservice.Core.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
