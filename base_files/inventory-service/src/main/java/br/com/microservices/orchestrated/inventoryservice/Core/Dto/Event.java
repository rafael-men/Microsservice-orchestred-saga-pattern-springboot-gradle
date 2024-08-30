package br.com.microservices.orchestrated.inventoryservice.Core.Dto;



import br.com.microservices.orchestrated.inventoryservice.Core.Enums.ESagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private String id;
    private String transactionId;
    private String orderId;
    private Order payload;
    private String source;
    private ESagaStatus status;
    public List<History> eventHistory;
}
