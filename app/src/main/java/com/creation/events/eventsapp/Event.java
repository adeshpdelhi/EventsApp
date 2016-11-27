package com.creation.events.eventsapp;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Rishabh on 10/15/2016.
 */
public class Event implements Serializable {

    public String name;
    public String date;
    public String time_from;
    public String time_to;
    public String club;
    public String description;
    public String organisers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganisers() {
        return organisers;
    }

    public void setOrganisers(String organisers) {
        this.organisers = organisers;
    }

    public Event(String name, String club, String date, String time_from, String time_to, String description, String organisers)
    {
        this.name=name;
        this.club=club;
        this.date=date;
        this.time_from=time_from;
        this.time_to=time_to;
        this.description=description;
        this.organisers=organisers;
    }


}
