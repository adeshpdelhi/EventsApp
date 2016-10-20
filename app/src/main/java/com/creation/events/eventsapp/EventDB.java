package com.creation.events.eventsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rishabh on 9/30/2016.
 */
public class EventDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "event.db";
    public static final String EVENTS_TABLE_NAME = "events";
    public static final String EVENTS_COLUMN_ID = "id";
    public static final String EVENTS_COLUMN_NAME = "name";
    public static final String EVENTS_COLUMN_DATE ="date" ;
    public static final String EVENTS_COLUMN_TIME_FROM ="time_from" ;
    public static final String EVENTS_COLUMN_TIME_TO ="time_to" ;
    public static final String EVENTS_COLUMN_CLUB = "club";
    public static final String EVENTS_COLUMN_DESCRIPTION = "description";
    public static final String EVENTS_COLUMN_ORGANISERS = "organisers";
    private HashMap hp;

    public EventDB(Context context)
    {

        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table events " +
                        "(id integer primary key autoincrement, name text, club text, date text,time_from text,time_to text, description text, organisers text)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }

    public boolean insertevent(Event new_event)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", new_event.name);
        contentValues.put("club", new_event.club);
        contentValues.put("description", new_event.description);
        contentValues.put("organisers", new_event.organisers);
        contentValues.put("date", new_event.date);
        contentValues.put("time_from", new_event.time_from);
        contentValues.put("time_to", new_event.time_to);

      /* SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            Date date = new Dat
            String datetime = dateFormat.format(date);*/

        db.insert("events", null, contentValues);
        return true;
    }

   /* public Cursor getData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where username="+"'"+username+"'"+"", null );
        return res;
    }*/

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EVENTS_TABLE_NAME);
        return numRows;
    }

   /* public boolean updateEvent (String name, String username, String qualification)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("username", username);
        contentValues.put("qualification", qualification);

        db.update("users", contentValues, "username = ? ", new String[] {username } );
        return true;
    }*/

   /* public Integer deleteUser(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "username = ? ",
                new String[] { username });
    }*/

    public ArrayList<Event> getAllEvents()
    {
        ArrayList<Event> array_list = new ArrayList<Event>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from events", null);
        res.moveToFirst();


    while (res.isAfterLast() == false) {
        String id = res.getString(res.getColumnIndex(EVENTS_COLUMN_ID));
        String name = res.getString(res.getColumnIndex(EVENTS_COLUMN_NAME));
        String club = res.getString(res.getColumnIndex(EVENTS_COLUMN_CLUB));
        String date = res.getString(res.getColumnIndex(EVENTS_COLUMN_DATE));
        String time_from = res.getString(res.getColumnIndex(EVENTS_COLUMN_TIME_FROM));
        String time_to = res.getString(res.getColumnIndex(EVENTS_COLUMN_TIME_TO));
        String description = res.getString(res.getColumnIndex(EVENTS_COLUMN_DESCRIPTION));
        String organisers = res.getString(res.getColumnIndex(EVENTS_COLUMN_ORGANISERS));

        Event newevent = new Event(name, club, date,time_from,time_to,description, organisers);
        array_list.add(newevent);
        res.moveToNext();
    }

        return array_list;
    }
}