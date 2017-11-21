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


    Button medButton, cancelButton, goToNewMedButton;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button medButton = (Button) findViewById(R.id.medButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        medButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
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
        } else {
            if (alarmManager != null){

                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Alarm Disabled !!",Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }


    }


}
