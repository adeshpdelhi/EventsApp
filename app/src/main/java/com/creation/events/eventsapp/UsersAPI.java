package com.creation.events.eventsapp;

import android.telecom.Call;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by adesh on 11/27/16.
 */
public interface UsersAPI {
    @GET("/users")
    public void getUsers(Callback<List<User>> response);
    @GET("/users/{id}")
    public void getUser(@Path("id") String email, Callback<User> response);
    @PUT("/users/{id}")
    public void updateUser(@Path("id") String email, @Body User user, Callback <Void> response);
    @POST("/users/{id}/{eventId}")
    public void addSubscribedEvent(@Path("id") String email, @Path("eventId") int eventId, Callback <String> response);
    @DELETE("/users/{id}/{eventId}")
    public void deleteSubscribedEvent(@Path("id") String email, @Path("eventId") int eventId, Callback <String> response);

}
