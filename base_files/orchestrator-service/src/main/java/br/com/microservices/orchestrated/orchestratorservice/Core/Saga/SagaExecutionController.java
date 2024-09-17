package br.com.microservices.orchestrated.orchestratorservice.Core.Saga;


import br.com.microservices.orchestrated.orchestratorservice.Config.Exception.ValidationException;
import br.com.microservices.orchestrated.orchestratorservice.Core.Dto.Event;
import br.com.microservices.orchestrated.orchestratorservice.Core.Enums.Etopics;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

import static br.com.microservices.orchestrated.orchestratorservice.Core.Enums.EEventSource.ORCHESTRATOR;
import static br.com.microservices.orchestrated.orchestratorservice.Core.Enums.ESagaStatus.SUCCESS;
import static java.lang.String.format;


@Component
@Slf4j
public class SagaExecutionController {

    private static final String SAGA_LOG_ID = "Order Id: %s | Transaction Id: %s | Event id: %s";

    private static final Logger log = LoggerFactory.getLogger(SagaExecutionController.class);

    public Etopics getNextTopic(Event event) {
        if(event.getSource(ORCHESTRATOR) == null || event.getStatus(SUCCESS) == null) {
            throw new ValidationException("Source and status must be informed.");
        }
        var topic = findTopicBySourceAndStatus(event);
        logCurrentSaga(event,topic);
        return topic;
    }


    private Etopics findTopicBySourceAndStatus(Event event) {
        return (Etopics) (Arrays.stream(SagaHandler.SAGA_HANDLER)
                .filter(row -> isEventSourceAndStatusValid(event,row))
                .map(i-> i[SagaHandler.TOPIC_INDEX])
                .findFirst()
                .orElseThrow(()-> new ValidationException("Topic not found")));
    }

    private Boolean isEventSourceAndStatusValid(Event event,Object[] row ) {
        var source = row[SagaHandler.EVENT_SOURCE_INDEX];
        var status = row[SagaHandler.SAGA_STATUS_INDEX];
        return event.getSource(ORCHESTRATOR).equals(source) && event.getStatus(SUCCESS).equals(status);
    }

    private void logCurrentSaga(Event event, Etopics etopics) {
        var sagaId = createSagaId(event);
        var source = event.getSource(ORCHESTRATOR);
        switch(event.getStatus(SUCCESS)) {
            case SUCCESS -> log.info("Success");
            case FAIL -> log.info("Fail");
            case ROLLBACK_PENDING -> log.info("## ROLLBACK_PENDING, SENDING TO ROLLBACK_SERVICE");
        }
    }

    private String createSagaId(Event event) {
        return format( SAGA_LOG_ID,
                event.getPayload().getId(),event.getTransactionId(),event.getId());
    }


}
