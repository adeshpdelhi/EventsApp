package com.creation.events.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Rishabh on 10/15/2016.
 */
public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    public static final String TAG= "HomeActivity";
    GoogleApiClient mGoogleApiClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SharedPreferences sharedPref = this.getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        User user = new User(sharedPref.getString("username",null),sharedPref.getString("email",null));
        Log.v(TAG, "Username is "+user.getName().toString());
        getSupportActionBar().setTitle("Events");
        populateEventsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.signoutoption:
                signOut();
                return true;
            case R.id.addeventoption:
                GotoAddEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
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



    public void GotoAddEvent()
    {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(

                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor sharedEditor = sharedPref.edit();
                        sharedEditor.putString("username", null);
                        sharedEditor.commit();
                        Toast.makeText(getApplicationContext(),"Sign out successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }
}
