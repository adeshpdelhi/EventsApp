package com.creation.events.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rishabh on 10/21/2016.
 */
public class ClubDetailsActivity extends AppCompatActivity {
    Club club;
    public static final String TAG= "ClubDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        Intent i=getIntent();
        club=(Club)i.getSerializableExtra("club");

        getSupportActionBar().setTitle(club.getName());

        TextView aName = (TextView) findViewById(R.id.clubdetail_name);
        TextView aDetail = (TextView) findViewById(R.id.clubdetail_details);
//        TextView aEvents = (TextView) findViewById(R.id.clubdetail_events);

        // Populate the data into the template view using the data object
        Log.e(TAG, "here");
        aName.setText(club.getName());
        aDetail.setText(club.getDetails());
        String eventList = "";
        if(club.getEvents()!=null)
            for(int j=0;j<club.getEvents().length;j++)
                eventList = eventList +" "+ club.getEvents()[j].getName();
        Log.e(TAG, eventList.toString());
        Log.e(TAG, "again");
//        aEvents.setText(eventList);
    }

    public void addEventOpen (View view) {

        Intent intent = new Intent(this,AddEventActivity.class);
        // pass the item information
        Bundle b = new Bundle();
        b.putSerializable("club",club);
        intent.putExtras(b);
        startActivity(intent);
    }

//    public void deleteClub(View view){
//        ClubDB db = new ClubDB(this);
//        db.deleteClub(e.name);
//        finish();
//    }
}
