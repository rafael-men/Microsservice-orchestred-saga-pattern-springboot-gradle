package br.com.microservices.orchestrated.orderservice.Core.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class EventFilters {

    @Id
    private String orderId;
    private String transactionId;

}
