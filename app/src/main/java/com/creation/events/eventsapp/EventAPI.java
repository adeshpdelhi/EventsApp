package com.creation.events.eventsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by adesh on 11/27/16.
 */
public interface EventAPI {
    @GET("/events")
    Call<List<Event>> getEvents();

}
