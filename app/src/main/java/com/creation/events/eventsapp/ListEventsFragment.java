package com.creation.events.eventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Rishabh on 10/15/2016.
 */
public class ListEventsFragment extends Fragment{
    public static final String TAG= "ListEventsFragment";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    GoogleApiClient mGoogleApiClient;
    public HomeActivity homeActivity;
    EventsAdapter adapter;
    ArrayList<Event> originalEventList = new ArrayList<Event>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_events, container, false);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchAndUpdateList();
    }
    public interface Refresh{
        public void refreshUser();
    }

    @Override
    public void onResume(){
        super.onResume();
        fetchAndUpdateList();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.homeActivity = ((HomeActivity)activity);
    }

    public void fetchAndUpdateList(){

        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        EventsAPI api = adapter.create(EventsAPI.class);

        //Defining the method
        api.getEvents(new Callback<List<Event>>() {
            @Override
            public void success(List<Event> list, Response response) {
                //Dismissing the loading progressbar
                originalEventList = new ArrayList<Event>(list);
                updateUserSubscriptions();
                updateListView();
                loading.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(TAG,error.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    public void updateUserSubscriptions(){
        if(HomeActivity.getCurrentUser()==null)
            return;
        ArrayList<Event> subscribedEvents = HomeActivity.getCurrentUser().getSubscribedEvents();
        Log.v(TAG, "Current user is "+HomeActivity.getCurrentUser().getName());
        for(int i=0;i<subscribedEvents.size();i++){
            Log.v(TAG,"Running for event "+subscribedEvents.get(i).getName()+" "+subscribedEvents.get(i).getEventId());
            for(int j=0;j<originalEventList.size();j++) {
                Log.v(TAG,"Checking with event "+originalEventList.get(j).getName()+" "+originalEventList.get(j).getEventId());

                if (subscribedEvents.get(i).getEventId().equals(originalEventList.get(j).getEventId())) {
                    originalEventList.get(j).setSubscribed(true);
                    Log.v(TAG, "Subs event " + originalEventList.get(j).getName() + " " + originalEventList.get(j).getSubscribed());
                }
            }
        }
        Log.v(TAG, originalEventList.toString());
    }

    private void updateListView() {
        // Construct the data source
//        EventDB mydb=new EventDB(this);
//        ArrayList<Event> arrayOfEvents = mydb.getAllEvents();
        // Create the adapter to convert the array to views


        adapter = new EventsAdapter(this, getActivity().getApplicationContext(), originalEventList);
        // Attach the adapter to a ListView
        ListView listView = (ListView) getActivity().findViewById(R.id.events_list);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//
//
//
//                Event e = (Event)parent.getAdapter().getItem(position);
//
//                Intent intent = new Intent(getActivity(),EventDetailsActivity.class);
//                // pass the item information
//                Bundle b = new Bundle();
//                b.putSerializable("event",e);
//                intent.putExtras(b);
//                startActivity(intent);
//
//            }
//        });

    }


}
