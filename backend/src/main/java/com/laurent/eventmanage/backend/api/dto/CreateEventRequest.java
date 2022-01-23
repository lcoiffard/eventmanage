package com.laurent.eventmanage.backend.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Getter
@Setter
public class CreateEventRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 32, message = "Name must be 32 characters maximum")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Begin date is mandatory")
    private ZonedDateTime beginDate;
    @NotNull(message = "End date is mandatory")
    private ZonedDateTime endDate;

}
