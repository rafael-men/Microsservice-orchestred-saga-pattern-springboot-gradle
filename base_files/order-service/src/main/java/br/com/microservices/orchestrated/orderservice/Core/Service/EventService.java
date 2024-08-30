package br.com.microservices.orchestrated.orderservice.Core.Service;


import br.com.microservices.orchestrated.orderservice.Core.Document.Event;
import br.com.microservices.orchestrated.orderservice.Core.Repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Event save(Event event) {
        return repository.save(event);
    }

}
