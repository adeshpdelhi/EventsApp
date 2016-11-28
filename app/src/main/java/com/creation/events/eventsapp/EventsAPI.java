package com.creation.events.eventsapp;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by adesh on 11/27/16.
 */
public interface EventsAPI {
    @GET("/events")
    public void getEvents(Callback<List<Event>> response);
    @POST("/events")
    public void addEvent(@Body Event event, Callback <Event> response);


}
