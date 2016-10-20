package com.creation.events.eventsapp;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Date;

/**
 * Created by Rishabh on 10/15/2016.
 */
public class Event {

    public String name;
    public String date;
   public String time_from;
   public String time_to;
    public String club;
    public String description;
    public String organisers;

    public Event(String name,String club,String date,String time_from,String time_to,String description,String organisers)
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
