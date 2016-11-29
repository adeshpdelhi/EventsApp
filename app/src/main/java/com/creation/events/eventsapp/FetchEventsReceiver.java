package com.creation.events.eventsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FetchEventsReceiver extends BroadcastReceiver {
    public FetchEventsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent dailyUpdater = new Intent(context, FetchEventsIntentService.class);

        context.startService(dailyUpdater);

        Log.d("FetchEventsReceiver", "Called context.startService from AlarmReceiver.onReceive");
    }
}
