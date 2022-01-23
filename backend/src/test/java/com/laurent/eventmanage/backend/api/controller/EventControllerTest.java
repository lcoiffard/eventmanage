package com.laurent.eventmanage.backend.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laurent.eventmanage.backend.api.dto.CreateEventRequest;
import com.laurent.eventmanage.backend.core.dto.EventDto;
import com.laurent.eventmanage.backend.core.service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService mockEventService;


    @Test
    public void givenOneEventInDatabase_whenGetAllEvents_thenReturnOneEvent() throws Exception {
        EventDto event = createEventDto();

        List<EventDto> events = Arrays.asList(event);

        given(mockEventService.getAllEvents()).willReturn(events);

        mvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(event.getName())));

    }

    @Test
    public void givenValidCreateEventRequest_whenCreateEvent_thenReturnEventCreated() throws Exception {

        CreateEventRequest request = createEventRequest();


        given(mockEventService.createEvent(Mockito.any())).willReturn(createEventDto());

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))

                )

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(request.getName())));

    }

    @Test
    public void givenNoFields_whenCreateEvent_thenThrowBadRequest() throws Exception {

        CreateEventRequest request = new CreateEventRequest();


        given(mockEventService.createEvent(Mockito.any())).willReturn(createEventDto());

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))

                )

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name is mandatory")))
                .andExpect(jsonPath("$.description", is("Description is mandatory")))
                .andExpect(jsonPath("$.beginDate", is("Begin date is mandatory")))
                .andExpect(jsonPath("$.endDate", is("End date is mandatory")));

    }

    @Test
    public void givenNameTooLong_whenCreateEvent_thenThrowBadRequest() throws Exception {

        CreateEventRequest request = createEventRequest();
        request.setName("Name too long to be saved because over 32 characters");


        given(mockEventService.createEvent(Mockito.any())).willReturn(createEventDto());

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))

                )

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name must be 32 characters maximum")))
        ;

    }

    private EventDto createEventDto() {
        EventDto event = new EventDto();
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