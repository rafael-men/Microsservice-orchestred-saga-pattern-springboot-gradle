package br.com.microservices.orchestrated.orderservice.Core.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "order")
public class EventFilters {

    @Id
    private String orderId;
    private String transactionId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public EventFilters(String orderId, String transactionId) {
        this.orderId = orderId;
        this.transactionId = transactionId;
    }
}
