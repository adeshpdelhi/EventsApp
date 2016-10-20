package com.example.rishabh.mc_project;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class AddEventActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
   // public String string_date;
    public String string_time_from ;
    public String string_time_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setTitle("Add Event");
        string_time_from="";
        string_time_to=null;

        Spinner spinner = (Spinner) findViewById(R.id.event_club);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.clubs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

    public void setTimeFrom(View view)
    {
        EditText editTextFromTime = (EditText) findViewById(R.id.event_time_from);
        SetTime fromTime = new SetTime(editTextFromTime, this);
        int hour=fromTime.hour;
        int minute=fromTime.minute;
       // String string_time_from=hour + ":" + minute;
    }
    public void setTimeTo(View view)
    {
        EditText editTextToTime = (EditText) findViewById(R.id.event_time_to);
        SetTime toTime = new SetTime(editTextToTime, this);
        int hour=toTime.hour;
        int minute=toTime.minute;
        String string_time_to=hour + ":" + minute;
    }

    public void addEvent(View view)
    {
        String name="",description="",organisers="",club="";


        boolean b_name=false,b_club=false;
        boolean b_date=false,b_time_from=false,b_time_to=false;
        boolean b_description=false,b_organisers=false;
        EditText text_name;
        text_name= (EditText) findViewById(R.id.event_name);
        if(!text_name.getText().toString().matches("")){
            name=text_name.getText().toString();
          //  Log.d(TAG,name);
            b_name=true;
        }



        Spinner mySpinner=(Spinner) findViewById(R.id.event_club);
        if(mySpinner.getSelectedItem().toString()!=null){
            club = mySpinner.getSelectedItem().toString();
            b_club=true;

        }
        EditText text_date;
        String string_date=null;
        text_date= (EditText) findViewById(R.id.event_date);
        if(!text_date.getText().toString().matches("")){
            string_date=text_date.getText().toString();
            //  Log.d(TAG,name);
            b_date=true;
        }

        EditText text_from;
        String string_time_from=null;
        text_from= (EditText) findViewById(R.id.event_time_from);
        if(!text_from.getText().toString().matches("")) {
            string_time_from = text_from.getText().toString();
            //  Log.d(TAG,name);
            b_time_from = true;
        }

        EditText text_to;
        String string_time_to=null;
        text_to= (EditText) findViewById(R.id.event_time_to);
        if(!text_to.getText().toString().matches("")) {
            string_time_to = text_to.getText().toString();
            //  Log.d(TAG,name);
            b_time_to = true;
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
        if(!text_organisers.getText().toString().matches("")){
            organisers=text_organisers.getText().toString();
           // Log.d(TAG,organisers);
            b_organisers=true;
        }



        if(b_name && b_club && b_date && b_time_from && b_time_to && b_description && b_organisers)
        {
            Log.d(TAG,name);
            Log.d(TAG,club);
            Log.d(TAG,string_date);
            Log.d(TAG,string_time_from);
           Log.d(TAG,string_time_to);
            Log.d(TAG,description);
            Log.d(TAG,organisers);
             Event new_event=new Event(name,club,string_date,string_time_from,string_time_to,description,organisers);
            EventDB event_db=new EventDB(this);
            boolean i=event_db.insertevent(new_event);
            if(i){
                Toast toast = Toast.makeText(getApplicationContext(), "EVENT CREATED", Toast.LENGTH_LONG);
                toast.show();
                text_organisers.setText("");
                text_description.setText("");
                text_name.setText("");
                //text_.setText("");

                Intent intent = new Intent(this, HomeActivity.class);
                int requestCode = 10;
                // intent.putExtra("requestCode", requestCode);
                //intent.putExtra("username", username);
                startActivity(intent);

            }
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_SHORT);
            toast.show();
        }

    }



}
