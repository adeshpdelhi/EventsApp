package com.creation.events.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sharedPref = this.getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username",null);
        Intent intentForSubsequentActivity;
        if(username==null){
            intentForSubsequentActivity = new Intent(this,LoginActivity.class);
        }
        else{
            intentForSubsequentActivity = new Intent(this,LoginActivity.class);
        }

        startActivity(intentForSubsequentActivity);
        finish();
    }
}
