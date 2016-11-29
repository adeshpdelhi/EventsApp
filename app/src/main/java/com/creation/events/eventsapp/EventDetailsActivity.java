package com.creation.events.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rishabh on 10/21/2016.
 */
public class EventDetailsActivity extends AppCompatActivity {
    Event e;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Intent i=getIntent();
        e=(Event)i.getSerializableExtra("event");

        getSupportActionBar().setTitle(e.name);

        TextView aName = (TextView) findViewById(R.id.eventdetail_name);
        TextView aClub = (TextView) findViewById(R.id.eventdetail_club);
        TextView aDate = (TextView) findViewById(R.id.eventdetail_date);
        TextView atime_from = (TextView) findViewById(R.id.eventdetail_time_from);
        TextView avenue = (TextView) findViewById(R.id.eventdetail_venue);
        TextView aDescription = (TextView)findViewById(R.id.eventdetail_description);
        TextView aOrganisers = (TextView) findViewById(R.id.eventdetail_organisers);

        // Populate the data into the template view using the data object
        aName.setText(e.name);
        aClub.setText(e.clubs[0].getName());
        aDate.setText(e.getDate());
        avenue.setText(e.venue);
        atime_from.setText(e.time);
        aDescription.setText(e.getDescription());
        aOrganisers.setText(e.getAdmins()[0].getName());
    }
    public void deleteEvent(View view){
        EventDB db = new EventDB(this);
        db.deleteEvent(e.name);
        finish();
    }
}
