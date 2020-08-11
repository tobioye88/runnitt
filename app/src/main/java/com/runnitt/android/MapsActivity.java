package com.runnitt.android;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        SelectDestinationBottomFragment.OnFragmentInteractionListener,
        ConfirmDestinationTopFragment.OnFragmentClose,
        ConfirmDestinationBottomFragment.OnConfirmButtonClickListener,
        PriceEstimationBottomFragment.OnConfirmPriceClickListener {

    private final String TAG = MapsActivity.class.getName();

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private FragmentManager fragmentManager;
    private FrameLayout flLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        flLoading = findViewById(R.id.flLoading);

        addSelectDestinationBottomFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Started");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Restarted");
    }

    private void setUpLocationListener() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Log.i("MapActivity",  location.toString());
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation != null) {
                updateLocation(lastKnownLocation);
            }
        }
    }

    public void addSelectDestinationBottomFragment(){
        Fragment selectDestinationBottomFragment = SelectDestinationBottomFragment.newInstance(null, null);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.flBottomWrapper, selectDestinationBottomFragment).commit();
    }

    public void addConfirmDestinationFragmentToScreen(){
//        Log.d(MapsActivity.class.getName(), "Called");
        ConfirmDestinationTopFragment destinationTopFragment = ConfirmDestinationTopFragment.newInstance(null, null);
        ConfirmDestinationBottomFragment destinationBottomFragment = ConfirmDestinationBottomFragment.newInstance(null, null);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack("ADD_DESTINATIONS");
        fragmentTransaction.add(R.id.flTopWrapper, destinationTopFragment);
        fragmentTransaction.add(R.id.flBottomWrapper, destinationBottomFragment).commit();
    }

    public void addPriceEstimationFragment(){
        fragmentManager.popBackStack();
        PriceEstimationBottomFragment priceEstimationBottomFragment = PriceEstimationBottomFragment.newInstance(null, null);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack("PRICE_ESTIMATION");
        fragmentTransaction.add(R.id.flTopWrapper, new EmptyFragment());
        fragmentTransaction.add(R.id.flBottomWrapper, priceEstimationBottomFragment).commit();
    }

    public void addLoadingScreen(){
        fragmentManager.popBackStack();
        LoadingFragment loadingFragment = LoadingFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flBottomWrapper, loadingFragment).commit();

//        flLoading.setVisibility(View.VISIBLE);
//        fragmentTransaction.add(R.id.flLoading, loadingFragment).commit();
        new CountDownTimer(2000, 1000){
            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }

            public void onFinish() {
                removeLoadingScreen();
                addFoundMatchedRiderFragment();
            }
        }.start();
    }

    public void removeLoadingScreen(){
        flLoading.setVisibility(View.GONE);
    }

    public void addFoundMatchedRiderFragment(){
        FoundRiderBottomFragment riderFragment = FoundRiderBottomFragment.newInstance(null, null);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flBottomWrapper, riderFragment).commit();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setUpLocationListener();
    }

    private void updateLocation(Location location) {
        mMap.clear();
        LatLng markerPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(markerPosition).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 15));
    }

    @Override
    public void onFragmentInteraction() {
        addConfirmDestinationFragmentToScreen();
    }

    @Override
    public void closeConfirmDestination() {
        //Todo: set the the view back to Select destination on back press
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "Back pressed");
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirmButtonClick() {
        addPriceEstimationFragment();
    }


    @Override
    public void onConfirmPriceClick() {
        addLoadingScreen();
    }
}
