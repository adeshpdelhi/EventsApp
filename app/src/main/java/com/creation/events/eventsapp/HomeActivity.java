package com.creation.events.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

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
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Toast.makeText(getApplicationContext(), "Item clicked",
                        Toast.LENGTH_SHORT).show();

                Event e = (Event)parent.getAdapter().getItem(position);

                Intent intent = new Intent(HomeActivity.this,EventDetailsActivity.class);
                // pass the item information
                Bundle b=new Bundle();
                b.putSerializable("event",e);
                intent.putExtras(b);
                startActivity(intent);

                /* Here instead Toast I want to show details of each Tutorial Topic in another fragment. */
            }
        });

    }


   /* public void onItemClick(ListView listView, View view, int position, long id) {
       // ListView myListView = getListView();
        Event e = (Event)listView.getAdapter().getItem(position);
        Toast.makeText(getApplicationContext(), "Item Clicked: ",Toast.LENGTH_SHORT).show();
     //  Event e = (Event)parent.getAdapter().getItem(position);

       Intent intent = new Intent(HomeActivity.this,EventDetails.class);
       // pass the item information
       Bundle b=new Bundle();
       b.putSerializable("event",e);
       intent.putExtras(b);
       startActivity(intent);
    } */
    public void GotoAddEvent(View view)
    {
        Intent intent = new Intent(this, AddEventActivity.class);
        int requestCode = 10;
        // intent.putExtra("requestCode", requestCode);
        //intent.putExtra("username", username);
        startActivity(intent);
    }
}
