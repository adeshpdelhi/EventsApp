package com.creation.events.eventsapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Rishabh on 10/15/2016.
 */
public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ListEventsFragment.Refresh{
    public static final String TAG = "HomeActivity";
    private int CALENDAR_PERMISSION_CODE = 20;
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
        take_permission();
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
       setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setRecurringAlarm(getApplicationContext());
    }

    private void setRecurringAlarm(Context context) {

        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getDefault());
        updateTime.set(Calendar.HOUR_OF_DAY, 12);
        updateTime.set(Calendar.MINUTE, 30);
        Intent downloader = new Intent(context, FetchEventsReceiver.class);
        downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, downloader,
                PendingIntent.FLAG_CANCEL_CURRENT);
//
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

//        Log.d("Home Activity", "Set alarmManager.setRepeating to: " + updateTime.getTime().toLocaleString());


//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 30*1000,
//                pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1*1000,
                1*1000, pendingIntent);
//        Toast.makeText(getApplicationContext(),"Service started!", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(this,SettingsActivity.class));
                return true;
            case R.id.addcluboption:
                startActivity(new Intent(this,AddClubActivity.class));
                return true;
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
    /////////////////////////////////////////////////////////////////////////////////////////

    public void take_permission()
    {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
        {
//            Toast.makeText(getApplicationContext(),"You already have the permission",Toast.LENGTH_LONG).show();
            return;
        }

        //If the app has not the permission then asking for the permission
        Toast.makeText(getApplicationContext(),"You DO NOT have the permission",Toast.LENGTH_LONG).show();
        requestCalendarPermission();
    }
    //Requesting permission
    private void requestCalendarPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_CALENDAR)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CALENDAR},CALENDAR_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == CALENDAR_PERMISSION_CODE){
            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can access calendar",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }
}
    ////////////////////////////////////////////////////////////////////////////////////////

