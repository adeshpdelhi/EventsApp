package com.creation.events.eventsapp;
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

        // Populate the data into the template view using the data object
        aName.setText(event.name);
        aClub.setText(event.club);
        aDate.setText(event.date);


        // Return the completed view to render on screen
        return convertView;
    }
}
