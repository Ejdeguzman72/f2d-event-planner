package com.f2d.event_planner.service;

import com.f2d.event_planner.domain.*;
import com.f2d.event_planner.feign.F2DGroupServiceFeignClient;
import com.f2d.event_planner.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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
        try {
            if (list.isEmpty()) {
                response.setSuccess(true);
                response.setMessage(AppConstants.EMPTY_EVENT_LIST_MSG);

            } else {
                List<F2DEvent> expiredEventsList = new ArrayList<>();
                for (F2DEvent event : list) {
                    for (UUID groupId : event.getGroupIdSet()) {
                        expiredEventsList = deleteAllExpiredEvents(groupId, list);
                        for (F2DEvent expiredEvent : expiredEventsList) {
                            UUID expiredEventID = expiredEvent.getEventId();
                            if (list.contains(expiredEventID)) {
                                list.remove(expiredEvent);
                            }
                        }
                    }
                }
                response.setList(list);
                response.setSuccess(true);
                response.setMessage(AppConstants.EVENT_RETRIEVE_LIST_SUCCESS_MSG);
            }
        } catch (Exception e) {
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
        if (!request.getGroupIdSet().isEmpty()) {
            Set<UUID> validGroupIdSet = new HashSet<>();
            for (UUID groupId : request.getGroupIdSet()) {
                ResponseEntity<F2DGroupSearchResponse> serviceResponse = f2DGroupServiceFeignClient.retrieveGroupById(groupId);
                if (serviceResponse.getStatusCode() == HttpStatus.OK && Objects.nonNull(serviceResponse.getBody())) {
                    validGroupIdSet.add(groupId);
                    request.setGroupIdSet(validGroupIdSet);
                }
            }
        }
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

                Set<UUID> groupIdSet = addGroupsToEvent(request.getGroupIdSet(), eventId);
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

    public List<F2DEvent> deleteAllExpiredEvents(UUID groupId, List<F2DEvent> list) {
        EventListResponse response = new EventListResponse();
        List<F2DEvent> expiredEventList = new ArrayList<>();
        ResponseEntity<F2DGroupSearchResponse> f2dGroupSearch = f2DGroupServiceFeignClient.retrieveGroupById(groupId);
        if (f2dGroupSearch.getStatusCode() == HttpStatus.OK && Objects.nonNull(Objects.requireNonNull(f2dGroupSearch.getBody()).getF2dGroup())) {
            for (F2DEvent event : list) {
                LocalDate eventDate = event.getEventDate();
                LocalDateTime eventDateTime = eventDate.atStartOfDay();
                LocalDate now = LocalDate.now();

                if (now.isBefore(ChronoLocalDate.from(eventDateTime.plusHours(24))) && now.isAfter(ChronoLocalDate.from(eventDateTime))) {
                    expiredEventList.add(event);
                }

                if (f2dGroupSearch.getStatusCode() == HttpStatus.OK && Objects.requireNonNull(f2dGroupSearch.getBody()).getF2dGroup() == null) {
                    expiredEventList.add(event);
                }
            }
        }

        return expiredEventList;
    }
}
