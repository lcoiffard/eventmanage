package com.laurent.eventmanage.backend.api.controller;

import com.laurent.eventmanage.backend.api.dto.CreateEventRequest;
import com.laurent.eventmanage.backend.core.dto.EventDto;
import com.laurent.eventmanage.backend.core.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/events")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;


    @GetMapping
    public List<EventDto> getEvents() {
        logger.info("EventController - getEvents");
        return eventService.getAllEvents();
    }


    @PostMapping
    public ResponseEntity createEvent(@Valid @RequestBody @NotNull CreateEventRequest createEventRequest) throws URISyntaxException {
        logger.info("EventController - createEvent : " + createEventRequest.getName());
        EventDto savedEvent = eventService.createEvent(createEventRequest);
        return ResponseEntity.created(new URI("/events/" + savedEvent.getId())).body(savedEvent);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
