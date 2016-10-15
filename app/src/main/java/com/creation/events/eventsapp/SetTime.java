package com.example.rishabh.mc_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Rishabh on 10/15/2016.
 */
class SetTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    public Calendar myCalendar;
    public int hour;
    public int minute;
    private Context _ctx;

    public SetTime(EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.myCalendar = Calendar.getInstance();
        this._ctx = ctx;

    }

    @Override
    public void onClick(View v) {
        hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        minute = myCalendar.get(Calendar.MINUTE);
        new TimePickerDialog(_ctx, this, hour, minute, true).show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        this.editText.setText( hourOfDay + ":" + minute);
    }

}