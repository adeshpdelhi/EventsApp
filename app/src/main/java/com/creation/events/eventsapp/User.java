package com.creation.events.eventsapp;

import android.app.ProgressDialog;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by adesh on 10/15/16.
 */
public class User implements Serializable{
    public static final String TAG= "UserClass";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;
    String name, email;
    @SerializedName("SubscribedEvents")
    private ArrayList<Event> SubscribedEvents= new ArrayList<Event>();
    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, ArrayList<Event> subscribedEvents) {
        this.name = name;
        this.email = email;
        SubscribedEvents = subscribedEvents;
    }

    public static String getTAG() {
        return TAG;
    }

    public static String getRootUrl() {
        return ROOT_URL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Event> getSubscribedEvents() {
        return SubscribedEvents;
    }

    public void setSubscribedEvents(ArrayList<Event> subscribedEvents) {
        SubscribedEvents = subscribedEvents;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
