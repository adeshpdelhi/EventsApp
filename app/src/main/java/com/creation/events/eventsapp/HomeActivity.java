package com.creation.events.eventsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Rishabh on 10/15/2016.
 */
public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ListEventsFragment.Refresh{
    public static final String TAG = "HomeActivity";
    public static final String ROOT_URL = "http://192.168.58.241:3000/";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    GoogleApiClient mGoogleApiClient;
    public static User user;
    public static User getCurrentUser(){
        return user;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_viewpager);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SharedPreferences sharedPref = this.getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        user = new User(sharedPref.getString("username",null),sharedPref.getString("email",null));
        fetchUser(user.getEmail());
       // Log.v(TAG, "Username is "+user.getName().toString());
//        getSupportActionBar().setTitle("Events");
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ListEventsFragment listEventsFragment = new ListEventsFragment();
        ListClubsFragment listClubsFragment = new ListClubsFragment();
        Bundle bUser = new Bundle();
        bUser.putSerializable("user",user);
        listEventsFragment.setArguments(bUser);
        listClubsFragment.setArguments(bUser);
        adapter.addFragment(listEventsFragment, "Events");
        adapter.addFragment(listClubsFragment, "Clubs");
        viewPager.setAdapter(adapter);
    }
    public void refreshUser(){
        if(user!=null)
        fetchUser(user.email);
    }
    private void fetchUser(String email){
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching User","Please wait...",false,false);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
       UsersAPI api = adapter.create(UsersAPI.class);

        //Defining the method
        api.getUser(email, new Callback<User>() {
            @Override
            public void success(User retrieved_user, Response response) {
                //Dismissing the loading progressbar
                user = retrieved_user;
                loading.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(TAG,error.toString());
                Toast.makeText(getApplicationContext(), "Error retrieving user!", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();
        if(user!=null)
            fetchUser(user.getEmail());
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
            case R.id.settingsoption:
                startActivity(new Intent(this,AddClubActivity.class));
            case R.id.addcluboption:
                startActivity(new Intent(this,SettingsActivity.class));
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


    public void GotoAddEvent()
    {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
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
