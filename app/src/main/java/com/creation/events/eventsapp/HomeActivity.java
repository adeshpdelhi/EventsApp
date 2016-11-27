package com.creation.events.eventsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Rishabh on 10/15/2016.
 */
public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    public static final String TAG= "HomeActivity";
    private static final String ROOT_URL = "http://192.168.0.104:3000/";
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
       // Log.v(TAG, "Username is "+user.getName().toString());
        getSupportActionBar().setTitle("Events");
        fetchAndUpdateList();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        fetchAndUpdateList();
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

    private void fetchAndUpdateList(){
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Creating an object of our api interface
        EventAPI api = adapter.create(EventAPI.class);

        Call<List<Event>> call = api.getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    Log.v(TAG, response.body().toString());
                    updateListView(new ArrayList<Event>(response.body()));
                } else {
                    int statusCode = response.code();

                    // handle request errors yourself
                    Log.e(TAG,response.toString());
                    Toast.makeText(getApplicationContext(),"Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG,t.toString());
                Toast.makeText(getApplicationContext(),"Internet Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListView(ArrayList<Event> list) {
        // Construct the data source
//        EventDB mydb=new EventDB(this);
//        ArrayList<Event> arrayOfEvents = mydb.getAllEvents();
        // Create the adapter to convert the array to views
        ArrayList<Event> arrayOfEvents = list;

        EventsAdapter adapter = new EventsAdapter(this, arrayOfEvents);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {



                Event e = (Event)parent.getAdapter().getItem(position);

                Intent intent = new Intent(HomeActivity.this,EventDetailsActivity.class);
                // pass the item information
                Bundle b = new Bundle();
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
        fetchAndUpdateList();
    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(

                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor sharedEditor = sharedPref.edit();
                        sharedEditor.putString("username", null);
                        sharedEditor.putString("email", null);
                        sharedEditor.commit();
                        Toast.makeText(getApplicationContext(),"Sign out successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }
}
