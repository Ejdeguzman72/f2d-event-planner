package com.f2d.event_planner.controller;

import com.f2d.event_planner.domain.*;
import com.f2d.event_planner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = UriConstants.RETRIEVE_ALL_EVENTS_URI)
    public EventListResponse retrieveAllEventInformation() {
        return eventService.retrieveAllEventInfo();
    }

    @GetMapping(value = UriConstants.RETRIEVE_EVENT_BY_ID_URI)
    public EventSearchResponse retrieveEventById(@PathVariable UUID eventId) {
        return eventService.retrieveEventById(eventId);
    }

    @PostMapping(value = UriConstants.CREATE_EVENT_URI)
    public EventAddUpdateResponse createEvent(@RequestBody EventAddUpdateRequest request) {
        return eventService.createEvent(request);
    }

    @PutMapping(value = UriConstants.UPDATE_EVENT_URI)
    public EventAddUpdateResponse updateEvent(@RequestBody EventAddUpdateRequest request, @PathVariable UUID eventId) {
        return eventService.updateEvent(request,eventId);
    }

    @DeleteMapping(value = UriConstants.DELETE_EVENT_URI)
    public EventSearchResponse deleteEvent(@PathVariable UUID eventId) {
        return eventService.deleteEventInfo(eventId);
    }
}
