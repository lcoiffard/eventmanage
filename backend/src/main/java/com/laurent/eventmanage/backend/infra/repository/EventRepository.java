package com.laurent.eventmanage.backend.infra.repository;

import com.laurent.eventmanage.backend.infra.dao.EventDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventDao, Long> {
}
