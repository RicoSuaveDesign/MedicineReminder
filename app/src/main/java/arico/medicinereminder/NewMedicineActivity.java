package arico.medicinereminder;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

//  this is the form where user enters fresh medicine
//  this will be triggered by a new tag being scanned by the rfid
//  before that, it can be navigated to manually

// TODO: Make dose units into a spinner, so research all dosage unit types
// TODO: Comment more
// TODO: Add error checks so user can't just break things by leaving fields blank.
// TODO: Add case where user takes meds more than once in a time period
// TODO: Isn't the clock format in TimePicker deprecated? Figure out slider format

public class NewMedicineActivity extends AppCompatActivity
            implements TimePickerFragment.onTimeSelectedListener, DatePickerFragment.onDateSelectedListener{

    private Medicine newMed; // the medicine we will be entering into the database
    private GregorianCalendar remindTime; // temp fix, does not work for multiple doses in a period

    EditText medName;
    EditText whenRemind;
    EditText dosage;
    EditText expiration;
    EditText doseUnit;
    EditText medFreqNum;
    EditText howManyDoses;

    Spinner freq;

    Button done;

    MedInterface medfetch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmedicine);


        // initialize EVERYTHING
        medfetch = MedClient.getClient().create(MedInterface.class);

        newMed = new Medicine();
        remindTime = new GregorianCalendar();

        medName = (EditText) findViewById(R.id.medNameText);
        whenRemind = (EditText) findViewById(R.id.timeText);
        dosage = (EditText) findViewById(R.id.dosageNum);
        expiration = (EditText) findViewById(R.id.expiration);
        doseUnit = (EditText) findViewById(R.id.doseUnit);
        medFreqNum = (EditText) findViewById(R.id.medFreqNum);
        howManyDoses = (EditText) findViewById(R.id.howMany);

        freq = (Spinner) findViewById(R.id.medFreqSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        freq.setAdapter(adapter);


        done = (Button) findViewById(R.id.submitButton);


        // set click listeners
        whenRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                FragmentManager fm = getFragmentManager();
                TimePickerFragment tpf = new TimePickerFragment();
                tpf.show(fm,"Time Picker");


            }
        });
        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(fm, "Date Picker");

            }
        });

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                newMed.setMedName(medName.getText().toString());
                newMed.setMedFreqPerTime(Integer.parseInt(medFreqNum.getText().toString()));
                newMed.setMedFreqInterval(86400000); //TODO: make this not just a day
                newMed.setDosage(Float.parseFloat(dosage.getText().toString()));
                newMed.setDosageUnit(doseUnit.getText().toString());
                newMed.setDosesLeft(Integer.parseInt(howManyDoses.getText().toString()));
                newMed.addCheckTime(remindTime);


               /* int freq = newMed.getMedFreqPerTime(); // Save a few function calls
                if(freq > 1)
                {
                    long realInterval = newMed.getMedFreqInterval() / newMed.getMedFreqPerTime();
                    for(int i = 0; i<freq; i++)
                    {

                    }
                }*/

                if(!TextUtils.isEmpty(medName.getText()) && !TextUtils.isEmpty(medFreqNum.getText())
                        && !TextUtils.isEmpty(dosage.getText()) && !TextUtils.isEmpty(doseUnit.getText()) ) {

                    Call<Medicine> call = medfetch.postMedicine(1, newMed.getMedName(), newMed.getMedFreqPerTime(), newMed.getMedFreqInterval(), newMed.getDosage(),
                            newMed.getDosageUnit(), newMed.getExpirationDate(), 30);

                    call.enqueue(new Callback<Medicine>() {
                        @Override
                        public void onResponse(Call<Medicine>call, Response<Medicine> response) {

                            if(response.isSuccessful()) {
//                                showResponse(response.body().toString());
                                Log.i(TAG, "post submitted to medicine");
                            }

                        }

                        @Override
                        public void onFailure(Call<Medicine>call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, t.toString());
                        }
                    });
                }
            }

        });


    }


    @Override
    public void onTimeSelected(int hour, int minute)
    {
        System.out.println(hour);
        System.out.println(minute);

        GregorianCalendar time = new GregorianCalendar();
        time.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, hour, minute);
        remindTime = time;

        // fills out the time box so user feels that their time went through
        if(hour > 12) {
            whenRemind.setText((hour - 12) + ":" + minute + "PM");
        }
        else
        {
            whenRemind.setText(hour + ":" + minute + "AM");
        }

    }

    @Override
    public void onDateSelected(int year, int month, int day)
    {
        System.out.println(day);
        System.out.println(month);
        System.out.println(year);

       /* GregorianCalendar date = new GregorianCalendar();
        date.set(year, month, day);
        newMed.setExpirationDate(date);*/
       String date = new String("");
        date += year + "-";
        date += (month+1) + "-";
        date += day;
        newMed.setExpirationDate(date);

        // fills out exp date box so user sees their input
        expiration.setText((month + 1) + "/" + day + "/" + year);
    }


}
