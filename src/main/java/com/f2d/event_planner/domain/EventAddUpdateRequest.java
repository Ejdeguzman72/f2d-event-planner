package com.f2d.event_planner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventAddUpdateRequest {

    UUID eventId;
    String eventName;
    String eventType;
    String description;
    LocalDate eventDate;
    LocalDate createTime;
    LocalDate lastUpdateTime;
    List<Long> attendees;
    List<Long> confirmedAttendees;
    List<Long> tentativeAttendees;
    List<Long> declinedAttendees;
    UUID groupId;

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<Long> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Long> attendees) {
        this.attendees = attendees;
    }

    public List<Long> getConfirmedAttendees() {
        return confirmedAttendees;
    }

    public void setConfirmedAttendees(List<Long> confirmedAttendees) {
        this.confirmedAttendees = confirmedAttendees;
    }

    public List<Long> getTentativeAttendees() {
        return tentativeAttendees;
    }

    public void setTentativeAttendees(List<Long> tentativeAttendees) {
        this.tentativeAttendees = tentativeAttendees;
    }

    public List<Long> getDeclinedAttendees() {
        return declinedAttendees;
    }

    public void setDeclinedAttendees(List<Long> declinedAttendees) {
        this.declinedAttendees = declinedAttendees;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
}
