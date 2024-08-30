package br.com.microservices.orchestrated.orchestratorservice.Core.Dto;

import br.com.microservices.orchestrated.orchestratorservice.Core.Enums.EEventSource;
import br.com.microservices.orchestrated.orchestratorservice.Core.Enums.ESagaStatus;
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
    private EEventSource source;
    private ESagaStatus status;
    public List<History> eventHistory;
}
