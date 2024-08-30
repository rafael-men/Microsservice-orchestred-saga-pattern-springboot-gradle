package br.com.microservices.orchestrated.orderservice.Core.Producer;

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
public class SagaProducer {

    private static final Logger log = LoggerFactory.getLogger(SagaProducer.class);
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Value("${spring.kafka.topic.start-saga}")
    private String startSagaTopic;

    public void sendEvent(String payload) {
        try{
            log.info("Sending event to topic",startSagaTopic,payload);
            kafkaTemplate.send(startSagaTopic,payload);
        }catch (Exception e){
            log.error("Error Trying to send data to topic.",startSagaTopic,payload,e);
        }
    }

}
