package br.com.microservices.orchestrated.orchestratorservice.Core.Producer;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SagaOrchestratorProducer {

    private static final Logger log = LoggerFactory.getLogger(SagaOrchestratorProducer.class);
    private final KafkaTemplate<String,String> kafkaTemplate;


    public void sendEvent(String payload,String topic) {
        try{
            log.info("Sending event to topic",topic,payload);
            kafkaTemplate.send(topic,payload);
        }catch (Exception e){
            log.error("Error Trying to send data to topic.",topic,payload,e);
        }
    }
}
