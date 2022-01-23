package com.laurent.eventmanage.backend.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class EventDto {
    private long id;
    private String name;
    private String description;
    private ZonedDateTime beginDate;
    private ZonedDateTime endDate;
}
