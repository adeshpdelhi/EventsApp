package com.creation.events.eventsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddEventActivity extends AppCompatActivity {
    private static final String TAG = "AddEventActivity";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;

    Club[] club = new Club[1];
    ArrayList <User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent i=getIntent();
        club[0]=(Club)i.getSerializableExtra("club");

        getSupportActionBar().setTitle("Add Event");


//        Spinner spinner = (Spinner) findViewById(R.id.event_club);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.clubs_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);



        //Creating a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        UsersAPI api = restAdapter.create(UsersAPI.class);

        //Defining the method
        api.getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> list, Response response) {
                //Dismissing the loading progressbar
                users = new ArrayList<User>(list);

            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(TAG,error.toString());
            }
        });
    }
public void setDate(View view)
{
    EditText editTextFromDate = (EditText) findViewById(R.id.event_date);
    MyEditTextDatePicker fromDate = new MyEditTextDatePicker(this,editTextFromDate);
    int day=fromDate._day;
    int month=fromDate._month;
    month=month+1;
    int year=fromDate._birthYear;
    /*string_date = day + "-" + month + "-" + year;
    Log.d(TAG,string_date);
    try{
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    string_date = formatter.parse(date);
} catch (ParseException e) {
        System.out.println(e.toString());
        e.printStackTrace();
    }*/

    Log.d(TAG,"in set date");
    }

    public void setTime(View view)
    {
        EditText editTextFromTime = (EditText) findViewById(R.id.event_time);
        SetTime fromTime = new SetTime(editTextFromTime, this);
        int hour=fromTime.hour;
        int minute=fromTime.minute;
       // String string_time_from=hour + ":" + minute;
    }


    public void addEvent(View view)
    {
        String name="",description="", venue="";


        boolean b_name=false;
        boolean b_date=false,b_time=false;
        boolean b_description=false,b_organisers=false, b_venue=false;
        EditText text_name;
        text_name= (EditText) findViewById(R.id.event_name);
        if(!text_name.getText().toString().matches("")){
            name=text_name.getText().toString();
          //  Log.d(TAG,name);
            b_name=true;
        }



//        Spinner mySpinner=(Spinner) findViewById(R.id.event_club);
//        if(mySpinner.getSelectedItem().toString()!=null){
//            club = mySpinner.getSelectedItem().toString();
//            b_club=true;
//
//        }
        EditText text_date;
        String string_date=null;
        text_date= (EditText) findViewById(R.id.event_date);
        if(!text_date.getText().toString().matches("")){
            string_date=text_date.getText().toString().replace('/','-').split(" ")[0];
            //  Log.d(TAG,name);
            b_date=true;
        }

//        EditText text_from;
//        String string_time_from=null;
//        text_from= (EditText) findViewById(R.id.event_time_from);
//        if(!text_from.getText().toString().matches("")) {
//            string_time_from = text_from.getText().toString();
//            //  Log.d(TAG,name);
//            b_time_from = true;
//        }

        EditText text_time;
        String string_time=null;
        text_time= (EditText) findViewById(R.id.event_time);
        if(!text_time.getText().toString().matches("")) {
            string_time = text_time.getText().toString();
            //  Log.d(TAG,name);
            b_time = true;
        }



        EditText text_description;
        text_description= (EditText) findViewById(R.id.event_description);
        if(!text_description.getText().toString().matches("")){
            description=text_description.getText().toString();
            b_description=true;
           // Log.d(TAG,description);
        }

        EditText text_organisers;
        text_organisers= (EditText) findViewById(R.id.event_organisers);
        ArrayList <User> organisers = new ArrayList<User>();
        if(!text_organisers.getText().toString().matches("")){
            String email = text_organisers.getText().toString();
            for(int i=0;i<users.size();i++)
                if(users.get(i).getEmail().equals(email)) {
                    organisers.add( users.get(i));
                    // Log.d(TAG,organisers);
                    b_organisers = true;
                }
        }

        EditText text_venue;
        text_organisers= (EditText) findViewById(R.id.event_venue);
        if(!text_organisers.getText().toString().matches("")){
            venue=text_organisers.getText().toString();
            // Log.d(TAG,organisers);
            b_venue=true;
        }

        if(b_name &&  b_date && b_time && b_description && b_organisers && b_venue)
        {
            Log.d(TAG,name);
            Log.d(TAG,club[0].getName());
            Log.d(TAG,string_date);
            Log.d(TAG,string_time);
            Log.d(TAG,organisers.toString());
            Log.d(TAG,description);
            Log.d(TAG,venue);
//           ---- Event new_event=new Event(name,new Club(1,club,"A new club"),string_date,string_time_from,string_time_to,description,organisers);
            Event new_event = new Event(name, club, string_date, description, venue, string_time, null,organisers.toArray(new User[organisers.size()]));

//            EventDB event_db=new EventDB(this);
//            boolean i=event_db.insertevent(new_event);
//            if(i){
//                Toast toast = Toast.makeText(getApplicationContext(), "EVENT CREATED", Toast.LENGTH_LONG);
//                toast.show();
//                text_organisers.setText("");
//                text_description.setText("");
//                text_name.setText("");
//                //text_.setText("");
//
////                Intent intent = new Intent(this, HomeActivity.class);
////                int requestCode = 10;
//                // intent.putExtra("requestCode", requestCode);
//                //intent.putExtra("username", username);
////                startActivity(intent);
//                finish();
//            }
            final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

            //Creating a rest adapter
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ROOT_URL)
                    .build();

            //Creating an object of our api interface
            EventsAPI api = adapter.create(EventsAPI.class);

            //Defining the method
            api.addEvent(new_event,new Callback<Event>() {
                @Override
                public void success(Event list, Response response) {
                    //Dismissing the loading progressbar
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_SHORT);
            toast.show();
        }

    }



}
