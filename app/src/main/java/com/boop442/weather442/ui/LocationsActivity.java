package com.boop442.weather442.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.adapters.ForecastListAdapter;
import com.boop442.weather442.adapters.LocationListAdapter;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.services.MetaWeatherService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {

    private SharedPreferences mSharedPreferences;
    private String mRecentLocation;

    @BindView(R.id.addButton) Button mAddButton;


    String mWoeid = "";
    String[] locationsTest = new String[] {"Portland", "Moscow", "Berlin", "44418", "London"};
    ArrayList<Location> locations = new ArrayList<>();

    @BindView(R.id.locationsRecyclerView) RecyclerView mRecyclerView;
    private LocationListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);

        locations.add(new Location("Moscow", "123"));
        locations.add(new Location("Portland", "456"));

        mAdapter = new LocationListAdapter(locations, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LocationsActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        mAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {

        if (v == mAddButton) {

//            Bundle bundle = new Bundle();
//            bundle.putParcelable("locationObject", Parcels.wrap(newLocation));
//            bundle.putString("dataToShow", dataToShow);

            FragmentManager fm = getFragmentManager();
            AddLocationDialogFragment addLocationDialogFragment = new AddLocationDialogFragment();
//            addLocationDialogFragment.setArguments(bundle);
            addLocationDialogFragment.show(fm, "Sample Fragment");
        }
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Log.d("ON_DISMISS-------------", "--------------------------------");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mRecentLocation = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        Log.d("SHARED PREF LOCATION---", mRecentLocation);


        final Location locationObject = new Location(mRecentLocation, "123");



        final MetaWeatherService weatherService = new MetaWeatherService();

        weatherService.getWoeid(mRecentLocation, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("LOCATIONS_ACTIVITY", "weatherService.getWoeid callback function --- onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("LOCATIONS_ACTIVITY", "weatherService.getWoeid callback function --- onResponse");
                mWoeid = MetaWeatherService.processWoeidCall(response);
                Log.d("LOCATIONS_ACTIVITY", mWoeid);
                locationObject.setWoeid(mWoeid);
                locations.add(locationObject);
                //displaying asynchronously in a ui thread
                LocationsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
}
