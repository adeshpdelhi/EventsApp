package com.example.rishabh.mc_project;

import android.content.Context;

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
public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(Context context, ArrayList<Event> event) {
        super(context, 0, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        // Lookup view for data population
        TextView aName = (TextView) convertView.findViewById(R.id.viewAllEvents_name);
        TextView aClub = (TextView) convertView.findViewById(R.id.viewAllEvents_club);
        TextView aDate = (TextView) convertView.findViewById(R.id.viewAllEvents_date);
        TextView atime_from = (TextView) convertView.findViewById(R.id.viewAllEvents_time_from);
        TextView atime_to = (TextView) convertView.findViewById(R.id.viewAllEvents_time_to);
        TextView aDescription = (TextView) convertView.findViewById(R.id.viewAllEvents_description);
        TextView aOrganisers = (TextView) convertView.findViewById(R.id.viewAllEvents_organisers);

        // Populate the data into the template view using the data object
        aName.setText(event.name);
        aClub.setText(event.club);
        aDate.setText(event.date);
        atime_from.setText(event.time_from);
        atime_to.setText(event.time_to);
        aDescription.setText(event.description);
        aOrganisers.setText(event.organisers);



        // Return the completed view to render on screen
        return convertView;
    }
}
