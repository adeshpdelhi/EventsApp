package com.creation.events.eventsapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddClubActivity extends AppCompatActivity {
    private static final String TAG = "AddClubActivity";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;
    EditText mName;
    EditText mDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);
        mName = (EditText)findViewById(R.id.newclub_name);
        mDescription = (EditText) findViewById(R.id.newclub_description);
    }

    public void addClub(View view){

        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        ClubsAPI api = adapter.create(ClubsAPI.class);
        User[] admin = new User[1];
        admin [0] = HomeActivity.getCurrentUser();
        Club new_club = new Club(0,mName.getText().toString(),mDescription.getText().toString(),null, admin, null);
        //Defining the method
        api.addClub(new_club,new Callback<Club>() {
            @Override
            public void success(Club list, Response response) {
                //Dismissing the loading progressbar
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(TAG,error.toString());
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
