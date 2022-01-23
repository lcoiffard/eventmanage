package com.laurent.eventmanage.backend.infra.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class EventDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private ZonedDateTime beginDate;
    private ZonedDateTime endDate;

}
