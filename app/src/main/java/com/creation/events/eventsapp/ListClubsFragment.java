package com.creation.events.eventsapp;

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
public class ListClubsFragment extends Fragment{
    public static final String TAG= "ListClubsFragment";
    private static final String ROOT_URL = HomeActivity.ROOT_URL;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    GoogleApiClient mGoogleApiClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_clubs, container, false);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchAndUpdateList();
    }


    @Override
    public void onResume(){
        super.onResume();
        fetchAndUpdateList();
    }



    public void fetchAndUpdateList(){
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        ClubsAPI api = adapter.create(ClubsAPI.class);

        //Defining the method
        api.getClubs(new Callback<List<Club>>() {
            @Override
            public void success(List<Club> list, Response response) {
                //Dismissing the loading progressbar
                updateListView(new ArrayList<Club>(list));
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

    private void updateListView(ArrayList<Club> list) {
        // Construct the data source
//        ClubDB mydb=new ClubDB(this);
//        ArrayList<Club> arrayOfClubs = mydb.getAllClubs();
        // Create the adapter to convert the array to views
        ArrayList<Club> arrayOfClubs = list;

        ClubsAdapter adapter = new ClubsAdapter(getActivity().getApplicationContext(), arrayOfClubs);
        // Attach the adapter to a ListView
        ListView listView = (ListView) getActivity().findViewById(R.id.clubs_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {



                Club e = (Club)parent.getAdapter().getItem(position);

                Intent intent = new Intent(getActivity(),ClubDetailsActivity.class);
                // pass the item information
                Bundle b = new Bundle();
                b.putSerializable("club",e);
                intent.putExtras(b);
                startActivity(intent);

                /* Here instead Toast I want to show details of each Tutorial Topic in another fragment. */
            }
        });




    }


}
