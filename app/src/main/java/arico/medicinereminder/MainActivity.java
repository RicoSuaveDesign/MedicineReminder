// Right now this is honestly just an entry point for the app.
// Will probably put instructions on this activity.
// Will be able to view all medicines as well as allergy info.

package arico.medicinereminder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlarmManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button viewMedsButton;
    TextView tvtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewMedsButton = (Button) findViewById(R.id.viewMeds);
        viewMedsButton.setOnClickListener(this);

        Context ctx = this.getApplicationContext();
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
        } else {
            //if token is null that means something wrong
            tvtoken.setText("Token not generated");
        }

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



}
