package com.laurent.eventmanage.backend.core.service;

import com.laurent.eventmanage.backend.api.dto.CreateEventRequest;
import com.laurent.eventmanage.backend.core.dto.EventDto;
import com.laurent.eventmanage.backend.core.mapper.EventMapper;
import com.laurent.eventmanage.backend.infra.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventMapper eventMapper;


    public List<EventDto> getAllEvents() {
        logger.info("EventService - getAllEvents");
        return eventMapper.toListEventDto(eventRepository.findAll());
    }

    public EventDto createEvent(CreateEventRequest createEventRequest) {
        logger.info("EventService - createEvent");
        return eventMapper.toEventDto(eventRepository.save(eventMapper.toEventDao(createEventRequest)));
    }


}
