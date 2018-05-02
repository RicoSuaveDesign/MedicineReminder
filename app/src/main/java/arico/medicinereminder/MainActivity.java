// Right now this is honestly just an entry point for the app.
// Will probably put instructions on this activity.
// Will be able to view all medicines as well as allergy info.

package arico.medicinereminder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlarmManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

import android.Manifest;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;


    Button viewMedsButton;
    TextView airquality, grass, mold, ragweed, pollen;
    Context ctx;

    LocationClient lc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airquality = (TextView) findViewById(R.id.airquality);
        grass = (TextView) findViewById(R.id.grass);
        mold = (TextView) findViewById(R.id.mold);
        ragweed = (TextView) findViewById(R.id.ragweed);
        pollen = (TextView) findViewById(R.id.tree);





        viewMedsButton = (Button) findViewById(R.id.viewMeds);
        viewMedsButton.setOnClickListener(this);

        ctx = this.getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        String token = preferences.getString("token", "no token");

        if (token != null) {
            //displaying the token
            MedInterface medfetch = MedClient.getClient().create(MedInterface.class);

            Call<Medicine> call = medfetch.updateUser(token);
            call.enqueue(new Callback<Medicine>() {
                @Override
                public void onResponse(Call<Medicine>call, Response<Medicine> response) {

                    //times = new ArrayList<>();

                }

                @Override
                public void onFailure(Call<Medicine>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
            System.out.println(token);
        }

        if(checkPlayServices()){
            if(checkInetPerm())
            {
                if(lc == null){
                lc = new LocationClient(ctx); // if we already enabled the permission then it will never fire
                     }
            }


        }

        if(lc != null){
            getAllergy();

        }
        else{
            System.out.println("No location key, no forecast");
        }

    }

    public void getAllergy(){
        String lockey = lc.getLocationKey();
        AccuInterface aui = AccuClient.getClient().create(AccuInterface.class);

        Call<Weather> call = aui.getPollutants(lockey, "kJg45AjDEXOIksWs8ZcS8nab7kVGU0Or", true);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather>call, Response<Weather> response) {

                
                ArrayList<AirAndPollen> allergies = new ArrayList<>(response.body().getDailyForecasts().get(0).getAirAndPollen());
                String aqString = "";
                aqString += "Air quality is ";
                aqString += allergies.get(0).getCategory();
                airquality.setText(aqString);
                grass.setText(createAllergyString(allergies.get(1)));
                mold.setText(createAllergyString(allergies.get(2)));
                ragweed.setText(createAllergyString(allergies.get(3)));
                pollen.setText(createAllergyString(allergies.get(4)));
            }

            @Override
            public void onFailure(Call<Weather>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        getAllergy();
    }

    public String createAllergyString(AirAndPollen aller){
        String retVal = "";
        retVal += aller.getName();
        retVal += ": ";
        retVal += aller.getValue();
        retVal += ". Risk is ";
        retVal += aller.getCategory();
        return retVal;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.viewMeds){
            launchViewMedsActivity();
        }
    }

    private void launchViewMedsActivity() {

        Intent intent = new Intent(this, MedListActivity.class);
        startActivity(intent);

    }

    private boolean checkPlayServices() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ctx);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
            }


        return true;
    }

    private boolean checkInetPerm() {
        int permissionCheck = ContextCompat.checkSelfPermission(ctx,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean retVal = false;

        if (ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
            // app-defined int constant. The callback method gets the
            // result of the request
        }
        else {
            retVal = true;

        }
        return retVal;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if(lc == null){
                    lc = new LocationClient(ctx);
                    }
                } else {
                    System.out.println("No location for allergies due to privacy concerns.");
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



}
