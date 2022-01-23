package com.laurent.eventmanage.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laurent.eventmanage.backend.api.dto.CreateEventRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class EventManageApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenCreateEvent_whenGetAllEvents_thenReturnOneEvent() throws Exception {

        // No events when getAllEvents
        mvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        CreateEventRequest request = createEventRequest();

        // Create one event
        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(request.getName())));

        // One event when getAllEvents
        mvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].name", is(request.getName())))
                .andExpect(jsonPath("$[0].description", is(request.getDescription())))
                .andExpect(jsonPath("$[0].beginDate", is("2022-01-01T12:00:00-05:00")))
                .andExpect(jsonPath("$[0].endDate", is("2022-01-01T12:30:00-05:00")));
        ;

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
