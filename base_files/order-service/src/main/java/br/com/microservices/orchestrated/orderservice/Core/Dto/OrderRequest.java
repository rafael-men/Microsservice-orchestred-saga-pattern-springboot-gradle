package br.com.microservices.orchestrated.orderservice.Core.Dto;

import br.com.microservices.orchestrated.orderservice.Core.Document.OrderProducts;
import br.com.microservices.orchestrated.orderservice.Core.Document.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OrderProducts> products;
}
