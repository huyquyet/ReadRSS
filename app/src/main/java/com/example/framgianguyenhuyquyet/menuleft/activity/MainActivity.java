package com.example.framgianguyenhuyquyet.menuleft.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.framgianguyenhuyquyet.menuleft.R;
import com.example.framgianguyenhuyquyet.menuleft.activity.fragment.FragmentListPost;
import com.example.framgianguyenhuyquyet.menuleft.activity.fragment.FragmentMain;
import com.example.framgianguyenhuyquyet.menuleft.controller.ReadRssAsyncTask;
import com.example.framgianguyenhuyquyet.menuleft.models.Data;
import com.example.framgianguyenhuyquyet.menuleft.models.Informations;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Data> items = null;
    Informations informations = null;
    ArrayList<Data> categoryArrList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ReadRssAsyncTask readRssAsyncTask = new ReadRssAsyncTask(this);
        if (checkInternetConnection()) {
            readRssAsyncTask.execute();
            this.informations = readRssAsyncTask.getInformations();
            this.items = readRssAsyncTask.getItems();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        selectFrag(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
//            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private void setFragment() {
        // get fragment manager
        FragmentManager fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment, new FragmentMain(informations));
        ft.commit();
    }

    public void selectFrag(int idItem) {
        Fragment fr;
        switch (idItem) {
            case R.id.nav_usa:
                getArrListCatrgory("USA");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "USA");
                break;
            case R.id.nav_africa:
                getArrListCatrgory("Africa");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Africa");
                break;
            case R.id.nav_asia:
                getArrListCatrgory("Asia");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Asia");
                break;
            case R.id.nav_middle_east:
                getArrListCatrgory("Middle East");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Middle East");
                break;
            case R.id.nav_europe:
                getArrListCatrgory("Europe");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Europe");
                break;
            case R.id.nav_americas:
                getArrListCatrgory("Americas");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Americas");
                break;
            case R.id.nav_science_technology:
                getArrListCatrgory("Science & Technology");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Science & Technology");
                break;
            case R.id.nav_economy:
                getArrListCatrgory("Economy");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Economy");
                break;
            case R.id.nav_health:
                getArrListCatrgory("Health");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Health");
                break;
            case R.id.nav_arts_entertainment:
                getArrListCatrgory("Arts & Entertainment");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Arts & Entertainment");
                break;
            case R.id.nav_usa_vote:
                getArrListCatrgory("2016 USA Votes");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "2016 USA Votes");
                break;
            case R.id.nav_features:
                getArrListCatrgory("One-Minute Features");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "One-Minute Features");
                break;
            case R.id.nav_voa_editors_picks:
                getArrListCatrgory("VOA Editors Picks");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "VOA Editors Picks");
                break;
            case R.id.nav_day_in_photo:
                getArrListCatrgory("Day in Photos");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Day in Photos");
                break;
            case R.id.nav_extra_time:
                getArrListCatrgory("Shaka: Extra Time");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Shaka: Extra Time");
                break;
            case R.id.nav_visiting:
                getArrListCatrgory("Visiting the USA");
                fr = new FragmentListPost(categoryArrList, MainActivity.this, "Visiting the USA");
                break;
            case R.id.nav_exit:
                fr = new FragmentMain(informations);
                break;
            default:
                fr = new FragmentMain(informations);
                break;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fr);
        fragmentTransaction.commit();

    }

    private void getArrListCatrgory(String category) {
        categoryArrList = new ArrayList<>();
        for (Data item : items) {
            if (item.getCategory().equalsIgnoreCase(category.trim()))
                categoryArrList.add(item);
        }
    }
}
