package com.creation.events.eventsapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FetchEventsIntentService extends IntentService {
    public static final String TAG= "IntentService";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;
    static List <Event> eventList;
    public FetchEventsIntentService() {
        super("FetchEventsIntentService");
    }
    static Integer counter = 1;
    private void sendNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.bottom_bar)
                        .setContentTitle("New Event!")
                        .setContentText("New event added. Check it out!");

        Intent resultIntent = new Intent(this, HomeActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = counter;
        counter++;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());





    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.v(TAG, "Service running now");
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ROOT_URL)
                    .build();
            if(eventList!=null)
                Log.v(TAG,"Size of earlier list is "+eventList.size());
            else
                Log.v(TAG,"Size of earlier list is 0(null)");

            //Creating an object of our api interface
            EventsAPI api = adapter.create(EventsAPI.class);

            //Defining the method
            api.getEvents(new Callback<List<Event>>() {
                @Override
                public void success(List<Event> list, Response response) {

                    Log.v(TAG, "success in intentservice");
                    if(eventList!=null){
                        Log.v(TAG,"Size of new list is "+list.size());
                        for(int i=0;i<list.size();i++ ){
                            boolean flag = false;
                            Log.v(TAG, "Comparing "+list.get(i).getName());
                            for(int j=0;j<eventList.size();j++){
                                Log.v(TAG, "Comparing "+eventList.get(j).getName());

                                if(eventList.get(j).getEventId().equals(list.get(i).getEventId())) {
                                    flag = true;
                                    Log.v(TAG, "Found!");
                                    break;
                                }
                            }
                            if(flag == false){
                                sendNotification();
                                Log.v(TAG, " Sending notification");
                                break;
                            }
                        }
                    }


                    eventList = list;

                }

                @Override
                public void failure(RetrofitError error) {
                    //you can handle the errors here
                    Log.e(TAG,error.toString());

                }
            });

        }
    }

}
