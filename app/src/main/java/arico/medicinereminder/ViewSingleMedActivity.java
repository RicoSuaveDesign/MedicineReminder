// Displays detailed information for an individual medicine.
// Can navigate to: EditMedicineActivity or ViewCheckTimes Activity.
// Can also delete a medicine from here; will redirect to MedList and essentially refresh the list.
// Data for this activity is passed from MedListActivity.

package arico.medicinereminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 3/31/2018.
 */

public class ViewSingleMedActivity extends AppCompatActivity implements View.OnClickListener {

    TextView med_name, purpose, instructions, inout, tagid, dosesleft, expiration;
    Button editButton, deleteButton, editTimesButton;
    private ArrayList<CheckTime> times;
    float dosing;
    String unit;
    long interv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsinglemed);

        med_name = (TextView) findViewById(R.id.med_name);
        purpose = (TextView) findViewById(R.id.purpose);
        instructions = (TextView) findViewById(R.id.instructions);
        inout = (TextView) findViewById(R.id.inOrOut);
        tagid = (TextView) findViewById(R.id.tagid);
        dosesleft = (TextView) findViewById(R.id.dosesLeft);
        expiration = (TextView) findViewById(R.id.expiration);

        editButton = (Button) findViewById(R.id.editMedButton);
        editTimesButton = (Button) findViewById(R.id.viewTimes);
        deleteButton = (Button) findViewById(R.id.deleteMed);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Boolean inOrOut = extras.getBoolean("inout");
            if(inOrOut)
            {
                inout.setText("In cabinet");
            }
            else
            {
                inout.setText("Out of cabinet");
            }
            String instruct = "Take ";
            dosing = extras.getFloat("dosage");
            instruct += dosing;

            unit =extras.getString("unit");
            instruct += unit;

            interv = extras.getLong("interval");

            long inter = interv;
            if(inter == 86400000)
            {
                instruct += " daily ";
            }
            else if(inter == 604800000)
            {
                instruct += " weekly ";
            }
            else if(inter == 2628000000L)
            {
                instruct += " monthly ";
            }
            else
            {
                float days = (inter/100/60/60/24);
                instruct += " every " + days + " days.";
            }

            instructions.setText(instruct);

            med_name.setText(extras.getString("med_name"));
            purpose.setText(extras.getString("med_desc"));
            dosesleft.setText(String.valueOf(extras.getInt("doses_left")));
            expiration.setText(extras.getString("expiration"));

            int thetag = extras.getInt("tag_id");
            tagid.setText(String.valueOf(thetag));

            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
            editTimesButton.setOnClickListener(this);

            //getTimes(thetag);
        }

    }



    @Override
    public void onClick(View v) {
        // TODO: Add an "are you sure" prompt
        if(v.getId() == R.id.deleteMed)
        {
            MedInterface medfetch = MedClient.getClient().create(MedInterface.class);

            Call<Medicine> call = medfetch.deleteMedicine(Integer.parseInt(tagid.getText().toString()));
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
            Context context = v.getContext();
            Intent intent = new Intent(context, MedListActivity.class);
            context.startActivity(intent); // redirect to medlist activity since the med is gone now
        }
        else if(v.getId() == R.id.editMedButton) {
            Context context = v.getContext();
            Intent intent = new Intent(context, EditMedicineActivity.class);

            intent.putExtra("tag_id", Integer.parseInt(tagid.getText().toString()));
            intent.putExtra("med_name", med_name.getText().toString());
            intent.putExtra("purpose", purpose.getText().toString());
            intent.putExtra("dose", dosing);
            intent.putExtra("unit", unit);
            intent.putExtra("interval", interv);
            intent.putExtra("dosesLeft", Integer.parseInt(dosesleft.getText().toString())); // lets be consistent and always pass as int
            intent.putExtra("expiration", expiration.getText().toString());

            context.startActivity(intent);

        }
        else if(v.getId() == R.id.viewTimes) {
            Context context = v.getContext();
            Intent intent = new Intent(context, ListTimesActivity.class);
            intent.putExtra("mid", Integer.parseInt(tagid.getText().toString()));
            context.startActivity(intent);

        }
    }
}
