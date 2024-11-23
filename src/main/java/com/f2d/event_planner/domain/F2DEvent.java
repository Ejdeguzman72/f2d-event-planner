package com.f2d.event_planner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "f2d_event")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class F2DEvent {

    UUID eventId;
    String eventName;
    String eventType;
    String description;
    LocalDate eventDate;
    LocalDate createTime;
    LocalDate lastUpdateTime;
    List<UUID> attendees;
    List<UUID> confirmedAttendees;
    List<UUID> tentativeAttendees;
    List<UUID> declinedAttendees;
    F2DGroup f2dGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generation strategy for UUID
    @Column(name = "event_id", columnDefinition = "UUID")
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    @Column(name = "event_name")
    @Nonnull
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Column(name = "event_type")
    @Nonnull
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Column(name = "description")
    @Nonnull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "event_date")
    @Nonnull
    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
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

    @Column(name = "attendees")
    public List<UUID> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<UUID> attendees) {
        this.attendees = attendees;
    }

    @Column(name = "confirmed_attendees")
    public List<UUID> getConfirmedAttendees() {
        return confirmedAttendees;
    }

    public void setConfirmedAttendees(List<UUID> confirmedAttendees) {
        this.confirmedAttendees = confirmedAttendees;
    }

    @Column(name = "tentative_attendees")
    public List<UUID> getTentativeAttendees() {
        return tentativeAttendees;
    }

    public void setTentativeAttendees(List<UUID> tentativeAttendees) {
        this.tentativeAttendees = tentativeAttendees;
    }

    @Column(name = "declined_attendees")
    public List<UUID> getDeclinedAttendees() {
        return declinedAttendees;
    }

    public void setDeclinedAttendees(List<UUID> declinedAttendees) {
        this.declinedAttendees = declinedAttendees;
    }

    @ManyToOne()
    @JoinColumn(name = "group_id")
    public F2DGroup getF2dGroup() {
        return f2dGroup;
    }

    public void setF2dGroup(F2DGroup f2dGroup) {
        this.f2dGroup = f2dGroup;
    }
}
