package com.laurent.eventmanage.backend.core.service;


import com.laurent.eventmanage.backend.api.dto.CreateEventRequest;
import com.laurent.eventmanage.backend.core.dto.EventDto;
import com.laurent.eventmanage.backend.core.mapper.EventMapper;
import com.laurent.eventmanage.backend.infra.dao.EventDao;
import com.laurent.eventmanage.backend.infra.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventRepository mockEventRepository;

    @Spy
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService = new EventService();

    @Test
    public void givenEventInDatabase_whenFindAll_returnOneEvent() {

        EventDao event = createEventDao();

        List<EventDao> events = Arrays.asList(event);

        given(mockEventRepository.findAll()).willReturn(events);

        List<EventDto> results = eventService.getAllEvents();

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo(event.getName());

    }

    @Test
    public void givenNoEventInDatabase_whenFindAll_returnEmptyList() {

        List<EventDao> events = Arrays.asList();

        given(mockEventRepository.findAll()).willReturn(events);

        List<EventDto> results = eventService.getAllEvents();

        assertThat(results.size()).isEqualTo(0);

    }

    @Test
    public void givenCreateEventRequest_whenCreateEvent_thenReturnEventCreated() {
        CreateEventRequest request = this.createEventRequest();
        given(mockEventRepository.save(Mockito.any())).willReturn(createEventDao());
        EventDto result = eventService.createEvent(request);
        assertThat(result.getName()).isEqualTo(request.getName());

    }

    @Test
    public void givenRepositorySaveReturnNull_whenCreateEvent_thenReturnNull() {
        CreateEventRequest request = this.createEventRequest();
        given(mockEventRepository.save(Mockito.any())).willReturn(null);
        EventDto result = eventService.createEvent(request);
        assertThat(result).isNull();
    }

    @Test
    public void givenCreateEventRequestNull_whenCreateEvent_thenReturnNull() {
        given(mockEventRepository.save(Mockito.any())).willReturn(null);
        EventDto result = eventService.createEvent(null);
        assertThat(result).isNull();
    }

    private EventDao createEventDao() {
        EventDao event = new EventDao();
        event.setId(1);
        event.setName("name");
        event.setDescription("description");
        event.setBeginDate(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("America/Montreal")));
        event.setEndDate(ZonedDateTime.of(2022, 1, 1, 12, 30, 0, 0, ZoneId.of("America/Montreal")));
        return event;
    }

    private CreateEventRequest createEventRequest() {
        CreateEventRequest request = new CreateEventRequest();
        request.setName("name");
        request.setDescription("description");
        request.setBeginDate(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("America/Montreal")));
        request.setEndDate(ZonedDateTime.of(2022, 1, 1, 12, 30, 0, 0, ZoneId.of("America/Montreal")));
        return request;
    }
}