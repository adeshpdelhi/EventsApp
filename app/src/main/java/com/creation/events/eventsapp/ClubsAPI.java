package com.creation.events.eventsapp;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by adesh on 11/27/16.
 */
public interface ClubsAPI {
    @GET("/clubs")
    public void getClubs(Callback<List<Club>> response);
    @POST("/clubs")
    public void addClub(@Body Club club, Callback <Club> response);
}
