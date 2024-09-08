package com.f2d.event_planner.service;

import com.f2d.event_planner.domain.*;
import com.f2d.event_planner.feign.F2DGroupServiceFeignClient;
import com.f2d.event_planner.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private F2DGroupServiceFeignClient f2DGroupServiceFeignClient;

    public EventListResponse retrieveAllEventInfo() {
        EventListResponse response = new EventListResponse();
        List<F2DEvent> list = eventRepository.findAll();
        if (!list.isEmpty()) {
            response.setList(list);
            response.setSuccess(true);
            response.setMessage(AppConstants.EVENT_RETRIEVE_LIST_SUCCESS_MSG);
        } else {
            response.setSuccess(false);
            response.setMessage(AppConstants.EVENT_RETRIEVE_LIST_FAILURE_MSG);
        }

        return response;
    }

    public EventSearchResponse retrieveEventById(UUID eventId) {
        EventSearchResponse response = new EventSearchResponse();
        F2DEvent event = eventRepository.findById(eventId).orElseGet(F2DEvent::new);
        response.setEvent(event);
        response.setSuccess(true);
        response.setMessage("SUCCESS");

        return response;
    }

    public Set<UUID> addGroupsToEvent(Set<UUID> groupIdSet, UUID eventId) {
        Set<UUID> validGroupIdSet = new HashSet<>();

        if (groupIdSet != null && !groupIdSet.isEmpty()) {
            for (UUID groupId : groupIdSet) {
                try {
                    ResponseEntity<F2DGroupSearchResponse> response = f2DGroupServiceFeignClient.retrieveGroupById(groupId);
                    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                        F2DGroup group = response.getBody().getF2dGroup();
                        if (!Objects.nonNull(group.getGroupId())) {
                            validGroupIdSet.add(group.getGroupId());
                        }
                    }
                } catch (Exception e) {
                    // Log the error or handle it as necessary
                    e.printStackTrace();
                }
            }
        }

        return validGroupIdSet;
    }

    public EventAddUpdateResponse createEvent(EventAddUpdateRequest request) {
        EventAddUpdateResponse response = new EventAddUpdateResponse();
        request.setCreateTime(LocalDate.now());
        request.setLastUpdateTime(LocalDate.now());
        F2DEvent event = eventRepository.save(request);

        try {
            if (Objects.nonNull(event.getEventId())) {
                response.setMessage(AppConstants.EVENT_ADD_UPDATE_SUCCESS_MSG);
                response.setSuccess(true);
            } else {
                response.setMessage(AppConstants.EVENT_ADD_UPDATE_FAILURE_MSG);
                response.setSuccess(false);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Exception occurred for creating/updating event: " + e.getMessage());
        }

        response.setEvent(event);
        return response;
    }

    public EventAddUpdateResponse updateEvent(EventAddUpdateRequest request, UUID eventId) {
        EventAddUpdateResponse response = new EventAddUpdateResponse();
        F2DEvent event = eventRepository.findById(eventId).orElseGet(F2DEvent::new);

        try {
            if (Objects.nonNull(event)) {
                event.setEventId(request.getEventId());
                event.setEventDate(request.getEventDate());
                event.setEventType(request.getEventType());
                event.setDescription(request.getDescription());

                Set<UUID> groupIdSet = addGroupsToEvent(request.getGroupIdSet(),eventId);
                event.setGroupIdSet(groupIdSet);

                event.setCreateTime(request.getCreateTime());
                event.setLastUpdateTime(LocalDate.now());

                F2DEvent updatedEventDetails = eventRepository.save(event);
                if (Objects.nonNull(updatedEventDetails.getEventId())) {
                    response.setSuccess(true);
                    response.setMessage(AppConstants.EVENT_ADD_UPDATE_SUCCESS_MSG);
                } else {
                    response.setSuccess(false);
                    response.setMessage(AppConstants.EVENT_ADD_UPDATE_FAILURE_MSG);
                }
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Exception occurred for creating/update event: " + e.getMessage());
        }

        response.setEvent(event);
        return response;
    }

    public EventSearchResponse deleteEventInfo(UUID eventId) {
        EventSearchResponse response = new EventSearchResponse();
        F2DEvent event = eventRepository.findById(eventId).orElseGet(F2DEvent::new);
        if (Objects.nonNull(event)) {
            eventRepository.deleteById(eventId);
        }

        response.setEvent(event);
        return response;
    }
}
