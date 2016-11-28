package com.creation.events.eventsapp;

import java.io.Serializable;

/**
 * Created by adesh on 11/27/16.
 */
public class Club implements Serializable{
    Integer clubId;
    String name;
    String details;
    Event[] events = new Event[20];
    User[] admins = new User[5];
    User[] subscribers = new User[20];

    public Club(Integer clubId, String name, String details, Event[] events, User[] admins, User[] subscribers) {
        this.clubId = clubId;
        this.name = name;
        this.details = details;
        this.events = events;
        this.admins = admins;
        this.subscribers = subscribers;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public User[] getAdmins() {
        return admins;
    }

    public void setAdmins(User[] admins) {
        this.admins = admins;
    }

    public User[] getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(User[] subscribers) {
        this.subscribers = subscribers;
    }
}
