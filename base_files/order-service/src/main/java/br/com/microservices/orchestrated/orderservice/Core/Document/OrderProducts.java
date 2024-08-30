package br.com.microservices.orchestrated.orderservice.Core.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProducts {

    private String product;
    private int quantity;
}
