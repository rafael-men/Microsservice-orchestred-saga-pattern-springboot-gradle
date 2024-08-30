package br.com.microservices.orchestrated.orchestratorservice.Core.Utils;


import br.com.microservices.orchestrated.orchestratorservice.Core.Dto.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public String toJson(Object object) {
        try{
            return objectMapper.writeValueAsString(object);
        }catch (Exception e){
            return "";
        }
    }

    public Event toEvent(String json) {
        try{
            return objectMapper.readValue(json,Event.class);

        }catch (Exception ex){
            return null;
        }
    }
}
