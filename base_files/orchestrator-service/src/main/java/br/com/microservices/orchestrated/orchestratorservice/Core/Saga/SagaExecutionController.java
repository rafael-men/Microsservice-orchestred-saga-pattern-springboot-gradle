package br.com.microservices.orchestrated.orchestratorservice.Core.Saga;


import br.com.microservices.orchestrated.orchestratorservice.Config.Exception.ValidationException;
import br.com.microservices.orchestrated.orchestratorservice.Core.Dto.Event;
import br.com.microservices.orchestrated.orchestratorservice.Core.Enums.Etopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Arrays;

import static br.com.microservices.orchestrated.orchestratorservice.Core.Saga.SagaHandler.SAGA_HANDLER;


@Component
@Slf4j
public class SagaExecutionController {

    public Etopics getNextTopic(Event event) {
        if(event.getSource() == null || event.getStatus() == null) {
            throw new ValidationException("Source and status must be informed.");
        }
        return Etopics.BASE_ORCHESTRATOR;
    }

    private Etopics findTopicBySourceAndStatus(Event event) {
        return Arrays.stream(SAGA_HANDLER)
                .filter()
    }
}
