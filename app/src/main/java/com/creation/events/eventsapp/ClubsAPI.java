package com.creation.events.eventsapp;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by adesh on 11/27/16.
 */
public interface ClubsAPI {
    @GET("/clubs")
    public void getClubs(Callback<List<Club>> response);
}
