package com.example.cristian.restaurante;

import android.content.IntentSender;
import android.database.Cursor;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private CameraUpdate cameraUpdate;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private final LatLng MEDELLIN_LOCATION = new LatLng(6.247899,-75.576239);
    private LatLng currentLocation;
    private boolean newLocationReady = false;

    DataBaseManager Manager = gestionMapa.getManager();
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);

        mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(MEDELLIN_LOCATION, 11);
        mMap.animateCamera(cameraUpdate);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        LoadMarkers();
    }

    public void onClick(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(MEDELLIN_LOCATION, 13);
        mMap.animateCamera(cameraUpdate);
    }

    public void onClick1(View view){
        if(newLocationReady){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation,16);
            mMap.animateCamera(cameraUpdate);
        }
    }

    public void LoadMarkers(){
        DataBaseManager Manager = gestionMapa.getManager();
        cursor = Manager.cargarCursorContactos();
        if (cursor.moveToFirst()){
            do{
                String nombre = cursor.getString(cursor.getColumnIndex(Manager.CN_NAME)).toString();
                String latitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LATITUD)).toString();
                String longitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LONGITUD)).toString();

                float latNum = Float.parseFloat(latitud);
                float longNum = Float.parseFloat(longitud);
                final LatLng LOCATION_VAR = new LatLng(latNum,longNum);
                Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions()
                        .position(LOCATION_VAR)
                        .title(nombre)
                        .snippet(latitud+", "+longitud)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }while (cursor.moveToNext());

        }
        else{
            Toast.makeText(getApplicationContext(), "No se ha ingresado ningún restaurante ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation!=null) {
            handleNewLocation(mLastLocation);
            newLocationReady=true;
        }
        else{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    private void handleNewLocation(Location location) {

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        currentLocation = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options1 = new MarkerOptions()
                .position(currentLocation)
                .title("Ubicacion actual");
        mMap.addMarker(options1);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
        newLocationReady=true;
    }
}//main
