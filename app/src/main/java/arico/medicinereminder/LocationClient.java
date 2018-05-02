package arico.medicinereminder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 4/25/2018.
 */

public class LocationClient implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Location mLastLocation;
    private static GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationCallback lcallback;

    private String locationKey;

    private double lat;
    private double longi;


    Context context;

    public LocationClient(Context thecontext) {

        context = thecontext;
        this.locationKey = "331521"; // using kenosha's area code as default until the location is later acquired in the app
        // Building the GoogleApi client
        buildGoogleApiClient();

        createLocationRequest();



    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println("is connected now yay!");
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("On Location Changed");

        createLocationRequest();

        this.lat = location.getLatitude();
        this.longi = location.getLongitude();

        System.out.println(this.lat);
        System.out.println(this.longi);

        displayLocation();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

    }

    protected void createLocationRequest() {

        lcallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    System.out.println("no location u_u");
                    return;
                }
                System.out.println(" "); // for whatever reason it only gives me lat + long if I have a string???
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...

                    lat = location.getLatitude();
                    longi = location.getLongitude();
                    System.out.print("Latitude from locresult: ");
                    System.out.print(lat);

                    System.out.print("Longitude: ");
                    System.out.print(longi);

                    locationKey = setLocationKey(lat, longi);
                }
            };
        };


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10);
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("Location permission denied.");
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, lcallback, null);
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            longi = mLastLocation.getLongitude();
            System.out.print("Latitude: ");
            System.out.print(lat);

            System.out.print("Longitude: ");
            System.out.print(longi);

            locationKey = setLocationKey(lat, longi);



        } else {

            System.out.println("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    public String setLocationKey(double latitude, double longitude){
        String retVal = "";
        String slatitude = Double.toString(latitude);
        String slongitude = Double.toString(longitude);

        String loc = slatitude;
        loc += ",";
        loc += slongitude;

        AccuInterface locfetch = AccuClient.getClient().create(AccuInterface.class);

        Call<accuLocation> call = locfetch.getLocKey(loc);
        call.enqueue(new Callback<accuLocation>() {
            @Override
            public void onResponse(Call<accuLocation>call, Response<accuLocation> response) {

                String lock = response.body().getLocKey();
                System.out.println("The next line is the key: ");
                System.out.println(lock);
                locationKey = lock;

            }

            @Override
            public void onFailure(Call<accuLocation>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



        return retVal;
    }

    public String getLocationKey(){
        return this.locationKey;
    }
}
