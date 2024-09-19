package com.f2d.event_planner.schedule;

import com.f2d.event_planner.domain.EventListResponse;
import com.f2d.event_planner.domain.F2DEvent;
import com.f2d.event_planner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventScheduler {

    @Autowired
    private EventService eventService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void validateEvents() {
        System.out.println("Running scheduled job at 2 AM - " + LocalDateTime.now());
        List<F2DEvent> events = eventService.retrieveAllEventInfo().getList();
        eventService.deleteEventInformation(events);
    }
}
