package br.com.microservices.orchestrated.orderservice.Core.Controllers;


import br.com.microservices.orchestrated.orderservice.Core.Document.Event;
import br.com.microservices.orchestrated.orderservice.Core.Dto.EventFilters;
import br.com.microservices.orchestrated.orderservice.Core.Service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;


    @GetMapping
    public Event findByFilters (EventFilters filters) {
        return eventService.findByFilters(filters);
    }

    @GetMapping("all")
    public List<Event> findAll() {
        return eventService.findAll();
    }
}
