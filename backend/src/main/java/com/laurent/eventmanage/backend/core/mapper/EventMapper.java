package com.laurent.eventmanage.backend.core.mapper;

import com.laurent.eventmanage.backend.api.dto.CreateEventRequest;
import com.laurent.eventmanage.backend.core.dto.EventDto;
import com.laurent.eventmanage.backend.infra.dao.EventDao;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public EventDao toEventDao(CreateEventRequest createEventRequest) {

        if (createEventRequest == null) {
            return null;
        }

        EventDao event = new EventDao();
        event.setName(createEventRequest.getName());
        event.setDescription(createEventRequest.getDescription());
        event.setBeginDate(createEventRequest.getBeginDate());
        event.setEndDate(createEventRequest.getEndDate());
        return event;

    }

    public EventDto toEventDto(EventDao dao) {

        if (dao == null) {
            return null;
        }

        EventDto event = new EventDto();
        event.setId(dao.getId());
        event.setName(dao.getName());
        event.setDescription(dao.getDescription());
        event.setBeginDate(dao.getBeginDate());
        event.setEndDate(dao.getEndDate());
        return event;

    }

    public List<EventDto> toListEventDto(List<EventDao> listDao) {
        if (CollectionUtils.isEmpty(listDao)) {
            return Collections.emptyList();
        }

        return listDao.stream().map(dao -> this.toEventDto(dao)).collect(Collectors.toList());

    }


}
