package com.f2d.event_planner.repository;

import com.f2d.event_planner.domain.F2DEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<F2DEvent, UUID> {
    List<F2DEvent> findByF2dGroup_GroupId(UUID groupId);
}
