package com.creation.events.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Rishabh on 10/15/2016.
 */
public class HomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Events");
        populateEventsList();
    }
    private void populateEventsList() {
        // Construct the data source
        EventDB mydb=new EventDB(this);
        ArrayList<Event> arrayOfEvents = mydb.getAllEvents();
        // Create the adapter to convert the array to views
        EventsAdapter adapter = new EventsAdapter(this, arrayOfEvents);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.allEvents);
        listView.setAdapter(adapter);
    }
    public void GotoAddEvent(View view)
    {
        Intent intent = new Intent(this, AddEventActivity.class);
        int requestCode = 10;
        // intent.putExtra("requestCode", requestCode);
        //intent.putExtra("username", username);
        startActivity(intent);
    }
}
