package br.com.microservices.orchestrated.productvalidationservice.Core.Producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Value("${spring.kafka.topic.orchestrator}")
    private String orchestratorTopic;

    public void sendEvent(String payload) {
        try{
            log.info("Sending event to topic",orchestratorTopic,payload);
            kafkaTemplate.send(orchestratorTopic,payload);
        }catch (Exception e){
            log.error("Error Trying to send data to topic.",orchestratorTopic,payload,e);
        }
    }
}
