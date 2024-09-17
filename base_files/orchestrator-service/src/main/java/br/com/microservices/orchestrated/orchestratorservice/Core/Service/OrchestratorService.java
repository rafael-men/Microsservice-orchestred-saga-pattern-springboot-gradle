package br.com.microservices.orchestrated.orchestratorservice.Core.Service;


import br.com.microservices.orchestrated.orchestratorservice.Core.Dto.Event;
import br.com.microservices.orchestrated.orchestratorservice.Core.Dto.History;
import br.com.microservices.orchestrated.orchestratorservice.Core.Enums.Etopics;
import br.com.microservices.orchestrated.orchestratorservice.Core.Producer.SagaOrchestratorProducer;
import br.com.microservices.orchestrated.orchestratorservice.Core.Saga.SagaExecutionController;
import br.com.microservices.orchestrated.orchestratorservice.Core.Utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.microservices.orchestrated.orchestratorservice.Core.Enums.EEventSource.ORCHESTRATOR;
import static br.com.microservices.orchestrated.orchestratorservice.Core.Enums.ESagaStatus.FAIL;
import static br.com.microservices.orchestrated.orchestratorservice.Core.Enums.ESagaStatus.SUCCESS;
import static br.com.microservices.orchestrated.orchestratorservice.Core.Enums.Etopics.NOTIFY_ENDING;

// criar construtores manualmente

@Service
@Slf4j
public class OrchestratorService {

    private static final Logger log = LoggerFactory.getLogger(OrchestratorService.class);
    private final SagaOrchestratorProducer producer;
    private final JsonUtils jsonUtils;
    private final SagaExecutionController sagaExecutionController;

    public void StartSaga(Event event) {
        event.getSource(ORCHESTRATOR);
        event.getStatus(SUCCESS);

        var topic = getTopic(event);
        log.info("STARTED");
        addHistory(event,"Saga Started");
        sendToProducerWithTopic(event,topic);
    }

    public void finishSagaSuccess(Event event) {
        event.getSource(ORCHESTRATOR);
        event.getStatus(SUCCESS);
        log.info("SAGA FINISHED SUCCESSFULLY");
        addHistory(event,"Saga finished");
        notifyFinishedSaga(event);
    }

    public void finishSagaFail(Event event) {
        event.getSource(ORCHESTRATOR);
        event.getStatus(FAIL);
        log.info("SAGA FINISHED WITH ERRORS :(",event.getId());
        addHistory(event,"Saga finished with errors");
        notifyFinishedSaga(event);
    }

    public void continueSaga(Event event) {
        var topic = getTopic(event);
        log.info("SAGA CONTINUING BY INFO",event.getId());
        sendToProducerWithTopic(event, topic);
    }

    private Etopics getTopic(Event event) {
        return sagaExecutionController.getNextTopic(event);
    }

    private void addHistory(Event event,String message) {
        var history = History
                .builder()
                .source(event.getSource(ORCHESTRATOR))
                .status(event.getStatus(SUCCESS))
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        event.addToHistory(history);
    }

    private void sendToProducerWithTopic(Event event,Etopics topic) {
        producer.sendEvent(jsonUtils.toJson(event), topic.getTopic());
    }

    private void notifyFinishedSaga(Event event) {
        producer.sendEvent(jsonUtils.toJson(event), NOTIFY_ENDING.getTopic());
    }

    public OrchestratorService(SagaOrchestratorProducer producer, JsonUtils jsonUtils, SagaExecutionController sagaExecutionController) {
        this.producer = producer;
        this.jsonUtils = jsonUtils;
        this.sagaExecutionController = sagaExecutionController;
    }
}
