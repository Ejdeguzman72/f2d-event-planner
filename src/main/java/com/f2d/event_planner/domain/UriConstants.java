package com.f2d.event_planner.domain;

public class UriConstants {

    public static final String LOCALHOST_URL = "http://localhost:8081";
    public static final String RETRIEVE_ALL_EVENTS_URI = "/events/all";
    public static final String RETRIEVE_EVENT_BY_ID_URI = "/events/search/id/{eventId}";
    public static final String CREATE_EVENT_URI = "/events/create";
    public static final String UPDATE_EVENT_URI = "/events/update/{eventId}";
    public static final String DELETE_EVENT_URI = "/events/delete/{eventId}";
    public static final String F2D_GROUP_SERVICE_RETRIEVE_BY_ID_RELATIVE_PATH = "/groups/search/id/{groupId}";
}
