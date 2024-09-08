package com.f2d.event_planner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "f2d_event")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class F2DEvent {

    UUID eventId;
    String eventType;
    String description;
    Set<UUID> groupIdSet;
    LocalDate eventDate;
    LocalDate createTime;
    LocalDate lastUpdateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generation strategy for UUID
    @Column(name = "group_id", columnDefinition = "UUID")
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    @Column(name = "event_type")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "event_date")
    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Set<UUID> getGroupIdSet() {
        return groupIdSet;
    }

    public void setGroupIdSet(Set<UUID> groupIdSet) {
        this.groupIdSet = groupIdSet;
    }

    @Column(name = "create_time")
    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    @Column(name = "last_updatetime")
    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
