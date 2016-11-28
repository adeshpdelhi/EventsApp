package com.creation.events.eventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rishabh on 10/1/2016.
 */
public class ClubsAdapter extends ArrayAdapter<Club> {
    public ClubsAdapter(Context context, ArrayList<Club> club) {
        super(context, 0, club);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Club club = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.club_item, parent, false);
        }
        // Lookup view for data population
        TextView aClub = (TextView) convertView.findViewById(R.id.viewAllClubs_club);

        // Populate the data into the template view using the data object
        aClub.setText(club.getName());


        // Return the completed view to render on screen
        return convertView;
    }
}
