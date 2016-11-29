package com.creation.events.eventsapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rishabh on 10/1/2016.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    private static final String TAG = "EventAdapter";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;
    ListEventsFragment listFragment;

    public EventsAdapter(ListEventsFragment listFragment ,Context context, ArrayList<Event> event) {
        super(context, 0, event);
        this.listFragment = listFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        // Lookup view for data population
        TextView aName = (TextView) convertView.findViewById(R.id.viewAllEvents_name);
        TextView aClub = (TextView) convertView.findViewById(R.id.viewAllEvents_club);
        TextView aDate = (TextView) convertView.findViewById(R.id.viewAllEvents_date);
        final Button aToggleSubscription = (Button) convertView.findViewById(R.id.viewAllEvents_toggle);

        // Populate the data into the template view using the data object
        aName.setText(event.name);
        Log.v(TAG, event.name);
        aClub.setText(event.clubs[0].getName());
        aDate.setText(event.getDate());
        if(event.getSubscribed()!=null)
            Log.v(TAG,event.getSubscribed().toString());
        else
            Log.v(TAG, "Null event found!");
        if(event.getSubscribed())
            aToggleSubscription.setBackgroundColor(Color.GREEN);
        else
            aToggleSubscription.setBackgroundColor(Color.RED);
        aToggleSubscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toggleSubscription(v.getContext().getApplicationContext(),event);
                try {
                    if (!event.getSubscribed()) {
//                    Calendar cal = Calendar.getInstance();
//                    Intent intent = new Intent(Intent.ACTION_EDIT);
//                    intent.setType("vnd.android.cursor.item/event");
//                    intent.putExtra("beginTime", cal.getTimeInMillis());
//                    intent.putExtra("allDay", true);
//                    intent.putExtra("rrule", "FREQ=YEARLY");
//                    intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//                    intent.putExtra("title", "A Test Event from android app");
//                    listFragment.startActivity(intent);
                        long calID = 1;
                        long startMillis = 0;
                        long endMillis = 0;
                        Log.v(TAG, "Date : " + event.getDate());
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(event.getYear(), event.getMonth() - 1, event.getDay(), event.getHour(), event.getMinute());
                        startMillis = beginTime.getTimeInMillis();
                        Calendar endTime = Calendar.getInstance();
                        endTime.set(event.getYear(), event.getMonth() - 1, event.getDay(), event.getHour() + 1, event.getMinute());

                        endMillis = endTime.getTimeInMillis();

                        ContentResolver cr = listFragment.homeActivity.getContentResolver();
                        ContentValues values = new ContentValues();
                        values.put(CalendarContract.Events.DTSTART, startMillis);
                        values.put(CalendarContract.Events.DTEND, endMillis);
                        values.put(CalendarContract.Events.TITLE, event.getName());
                        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
                        values.put(CalendarContract.Events.CALENDAR_ID, calID);
                        values.put(CalendarContract.Events.EVENT_TIMEZONE, "India");
                        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                        long eventID = Long.parseLong(uri.getLastPathSegment());
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(listFragment.getContext());
                        Integer reminderTime = Integer.parseInt(sharedPrefs.getString("reminder1", "0"));
                        if (reminderTime != null && reminderTime != 0) {

                            values = new ContentValues();
                            values.put(CalendarContract.Reminders.MINUTES, reminderTime);
                            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
                            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                            cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                        }
                        reminderTime = Integer.parseInt(sharedPrefs.getString("reminder2", "0"));
                        if (reminderTime != null && reminderTime != 0) {
                            values = new ContentValues();
                            values.put(CalendarContract.Reminders.MINUTES, reminderTime);
                            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
                            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                            cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                        }

                        Log.v(TAG, "URI is " + uri);
                    } else {
                        Uri uri = CalendarContract.Events.CONTENT_URI;
//                        String[] projection = new String[] {
//                            CalendarContract.Calendars._ID,
//                            CalendarContract.Calendars.ACCOUNT_NAME,
//                            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
//                            CalendarContract.Calendars.NAME,
//                            CalendarContract.Calendars.CALENDAR_COLOR
//                    };
                        String[] projection = new String[]{
                                event.getName()
                        };
                        listFragment.homeActivity.getContentResolver().delete(uri, CalendarContract.Events.TITLE + " LIKE ?", projection);
//                    Cursor calendarCursor = managedQuery(uri, projection, null, null, null);

                    }
                }
                catch(Exception e){
                    Log.e(TAG,e.getMessage().toString());
                            Toast.makeText(listFragment.getContext(),"Please grant permission for calendar", Toast.LENGTH_SHORT).show();
                }
                event.setSubscribed(!event.getSubscribed());
                if(event.getSubscribed()) {
                    aToggleSubscription.setBackgroundColor(Color.GREEN);
                    aToggleSubscription.setText("UnSubscribe");

                }
                else
                    aToggleSubscription.setBackgroundColor(Color.RED);
                aToggleSubscription.setText("Subscribe");


            }
        });
//
//        aToggleSubscription.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                toggleSubscription(v.getContext().getApplicationContext(),event);
//
//            }
//        });
        RelativeLayout rel = (RelativeLayout) convertView.findViewById(R.id.item_nonbutton);
        rel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getContext().getApplicationContext(),EventDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // pass the item information
                Bundle b = new Bundle();
                b.putSerializable("event",event);
                intent.putExtras(b);
                getContext().startActivity(intent);
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    private void toggleSubscription(final Context context, Event event){

        User user = HomeActivity.getCurrentUser();

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        UsersAPI api = adapter.create(UsersAPI.class);
        if(!event.getSubscribed()){
            //Defining the method
            api.addSubscribedEvent(user.email, event.eventId,new Callback<String>() {
                @Override
                public void success(String str, Response response) {
                    //Dismissing the loading progressbar
    //                loading.dismiss();
                    Toast.makeText(context, "Added notification!", Toast.LENGTH_SHORT).show();
//                    listFragment.fetchAndUpdateList();
//                    listFragment.homeActivity.refreshUser();
//                    listFragment.updateUserSubscriptions();
//                    listFragment.adapter.notifyDataSetChanged();
//                    listFragment.onResume();
                }

                @Override
                public void failure(RetrofitError error) {
                    //you can handle the errors here
                    Log.e(TAG,error.toString());
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
    //                loading.dismiss();
                }
            });
        }

        else{
            //Defining the method
            api.deleteSubscribedEvent(user.email, event.eventId,new Callback<String>() {
                @Override
                public void success(String str, Response response) {
                    //Dismissing the loading progressbar
                    //                loading.dismiss();
                    Toast.makeText(context, "Removed notification!", Toast.LENGTH_SHORT).show();
//                    listFragment.homeActivity.refreshUser();
//                    listFragment.updateUserSubscriptions();
//                    listFragment.adapter.notifyDataSetChanged();
//                    listFragment.onResume();

                }

                @Override
                public void failure(RetrofitError error) {
                    //you can handle the errors here
                    Log.e(TAG,error.toString());
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    //                loading.dismiss();
                }
            });
        }

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////


}
