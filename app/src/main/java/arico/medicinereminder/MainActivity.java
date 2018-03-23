package arico.medicinereminder;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlarmManager;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button medButton, cancelButton, goToNewMedButton, viewMedsButton;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        medButton = (Button) findViewById(R.id.medButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        goToNewMedButton = (Button) findViewById(R.id.goToNewMed);
        viewMedsButton = (Button) findViewById(R.id.viewMeds);
        medButton.setOnClickListener(this);
        viewMedsButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        goToNewMedButton.setOnClickListener(this);
    }

    public void startAlert(View v){
        intent = new Intent(this, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, pendingIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.medButton) {
            startAlert(v);
        }
        else if(v.getId() == R.id.goToNewMed){
            launchNewMedsActivity();
        }
        else if(v.getId() == R.id.viewMeds){
            launchViewMedsActivity();
        }
        else {
            if (alarmManager != null){

                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Alarm Disabled !!",Toast.LENGTH_LONG).show();

            }

        }
    }

    private void launchNewMedsActivity() {

        Intent intent = new Intent(this, NewMedicineActivity.class);
        startActivity(intent);
    }

    private void launchViewMedsActivity() {

        Intent intent = new Intent(this, MedListActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }


    }


}
