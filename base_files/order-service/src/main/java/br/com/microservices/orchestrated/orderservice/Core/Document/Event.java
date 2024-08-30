package br.com.microservices.orchestrated.orderservice.Core.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "event")
public class Event {

    @Id
    private String id;
    private String transactionId;
    private String orderId;
    private Order payload;
    private String source;
    private String status;
    public List<History> eventHistory;
}
