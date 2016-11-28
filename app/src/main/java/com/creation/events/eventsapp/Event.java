package com.creation.events.eventsapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event implements Serializable {

    public Integer eventId;
    public String name;
    @SerializedName("Clubs")
    public Club[] clubs = new Club[1];
    public String date;
    public String description;
    public String venue;
    public String time;
    public String[] announcements = new String[10];
    public User[] Admins = new User[5];
    private boolean isSubscribed = false;
    //2016-12-29T00:00:00.000Z
    public Integer getYear(){
        return Integer.parseInt(date.split("-")[0]);
    }
    public Integer getMonth(){
        return Integer.parseInt(date.split("-")[1]);
    }
    public Integer getDay(){
        return Integer.parseInt(date.split("-")[2].split("T")[0]);
    }
    public Integer getHour(){
        return Integer.parseInt(time.split(":")[0]);
    }
    public Integer getMinute(){
        return Integer.parseInt(time.split(":")[1]);
    }
    public Event(Integer eventId, String name, Club[] clubs, String date, String description, String venue, String time, String[] announcements, User[] admins) {
        this.eventId = eventId;
        this.name = name;
        this.clubs = clubs;
        this.date = date;
        this.description = description;
        this.venue = venue;
        this.time = time;
        this.announcements = announcements;
        Admins = admins;
    }

    public Event(String name, Club[] clubs, String date, String description, String venue, String time, String[] announcements, User[] admins) {
        this.name = name;
        this.clubs = clubs;
        this.date = date;
        this.description = description;
        this.venue = venue;
        this.time = time;
        this.announcements = announcements;
        Admins = admins;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club[] getClubs() {
        return clubs;
    }

    public void setClubs(Club[] clubs) {
        this.clubs = clubs;
    }

    public String getDate() {
        if(date!=null)
            return date.split("T")[0];
        else
            return  date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String[] announcements) {
        this.announcements = announcements;
    }

    public User[] getAdmins() {
        return Admins;
    }

    public void setAdmins(User[] admins) {
        Admins = admins;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }
}
