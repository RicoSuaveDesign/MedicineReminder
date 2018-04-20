// Technically a form of editing medicine.
// When a user swipes an RFID tag over the scanner on the server, it is entered with vanilla values, and "new" set to true.
// When they next navigate to MedListActivity, they will automatically be redirected here to give details.
// They can also delete from here if the scan was a mistake.

package arico.medicinereminder;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

//  this is the form where user enters fresh medicine
//  this will be triggered by a new tag being scanned by the rfid
//  before that, it can be navigated to manually

// TODO: Comment more
// TODO: Add error checks so user can't just break things by leaving fields blank.
// TODO: Add case where user takes meds more than once in a time period
// TODO: Isn't the clock format in TimePicker deprecated? Figure out slider format

public class NewMedicineActivity extends AppCompatActivity
            implements TimePickerFragment.onTimeSelectedListener, DatePickerFragment.onDateSelectedListener{

    private Medicine newMed; // the medicine we will be entering into the database
    private String remindTime; // temp fix, does not work for multiple doses in a period

    EditText medName;
    EditText whenRemind;
    EditText dosage;
    EditText expiration;
    EditText doseUnit;
    EditText medFreqNum;
    EditText howManyDoses;
    EditText purpose;
    TextView tagid;

    int realid;

    Spinner freq;

    Button done, deleteMed;

    MedInterface medfetch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmedicine);


        // initialize EVERYTHING
        medfetch = MedClient.getClient().create(MedInterface.class);

        newMed = new Medicine();
        remindTime = "";

        medName = (EditText) findViewById(R.id.medNameText);
        whenRemind = (EditText) findViewById(R.id.timeText);
        dosage = (EditText) findViewById(R.id.dosageNum);
        expiration = (EditText) findViewById(R.id.expiration);
        doseUnit = (EditText) findViewById(R.id.doseUnit);
        medFreqNum = (EditText) findViewById(R.id.medFreqNum);
        howManyDoses = (EditText) findViewById(R.id.howMany);
        purpose = (EditText) findViewById(R.id.purpose);

        tagid = (TextView) findViewById(R.id.tag_id);
        Bundle extras = getIntent().getExtras();

        if(extras != null)
        {
            String theid = "Tag ID: ";
            realid = extras.getInt("tag_id");
            theid += realid;
            tagid.setText(theid);

        }

        freq = (Spinner) findViewById(R.id.medFreqSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        freq.setAdapter(adapter);


        done = (Button) findViewById(R.id.submitButton);
        deleteMed = (Button) findViewById(R.id.deleteButton);


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

        deleteMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                MedInterface medfetch = MedClient.getClient().create(MedInterface.class);

                Call<Medicine> call = medfetch.deleteMedicine(realid);
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
                Context context = view.getContext();
                Intent intent = new Intent(context, MedListActivity.class);
                context.startActivity(intent); // redirect to medlist activity since the med is gone now

            }
        });

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                newMed.setMedName(medName.getText().toString());
                newMed.setTag_id(realid);
                newMed.setMedFreqPerTime(Integer.parseInt(medFreqNum.getText().toString()));
                newMed.setMed_desc(purpose.getText().toString());

                if(freq.getSelectedItem().toString().equals("Daily"))
                {
                    newMed.setMedFreqInterval(86400000);
                }
                else if(freq.getSelectedItem().toString().equals("Weekly"))
                {
                    newMed.setMedFreqInterval(604800000);
                }
                else if(freq.getSelectedItem().toString().equals("Monthly"))
                {
                    newMed.setMedFreqInterval(2628000000L);

                }

                newMed.setDosage(Float.parseFloat(dosage.getText().toString()));
                newMed.setDosageUnit(doseUnit.getText().toString());
                newMed.setDosesLeft(Integer.parseInt(howManyDoses.getText().toString()));
                newMed.addCheckTime(remindTime);
                GregorianCalendar time = (GregorianCalendar) Calendar.getInstance();
                int year = time.get(Calendar.YEAR);
                int month = time.get(Calendar.MONTH);
                int date = time.get(Calendar.DAY_OF_MONTH);

                month += 1; // months are indexed from 0
                newMed.addCheckDate(makeDBDate(month, date, year));

                // We're going to generate times based on the intervals, and user can later edit them.
                int freq = newMed.getMedFreqPerTime(); // Save a few function calls
                if(freq > 1)
                {
                    // we need to get the initial hour and minute first

                    System.out.println(time.getTimeInMillis());
                    String wordTime = newMed.getCheckTime(0);
                    int hour = Integer.parseInt(wordTime.substring(0,1));
                    int minute = Integer.parseInt(wordTime.substring(3,4));

                    // now we're matching data types, also figuring out the interval between check times.
                    time.set(year, month, date, hour, minute);
                    long realInterval = newMed.getMedFreqInterval() / newMed.getMedFreqPerTime();
                    long timeinit = time.getTimeInMillis();
                    int nextHour;
                    int nextMinute;
                    int nextdate;
                    int nextmonth;
                    int nextyear;


                    for(int i = 1; i<freq; i++)
                    {
                        // take times to ms and add them, then put into medicine to go to db.
                        System.out.println(timeinit);
                        timeinit += realInterval;
                        System.out.println(timeinit);
                        time.setTimeInMillis(timeinit);
                        nextHour = time.get(Calendar.HOUR_OF_DAY);
                        nextMinute = time.get(Calendar.MINUTE);
                        nextdate = time.get(Calendar.DAY_OF_MONTH);
                        nextmonth = time.get(Calendar.MONTH);
                        nextyear = time.get(Calendar.YEAR);

                        System.out.println(makeDBTime(nextHour, nextMinute));

                        newMed.addCheckTime(makeDBTime(nextHour, nextMinute));
                        newMed.addCheckDate(makeDBDate(nextmonth, nextdate, nextyear));
                    }
                }

                if(!TextUtils.isEmpty(medName.getText()) && !TextUtils.isEmpty(medFreqNum.getText())
                        && !TextUtils.isEmpty(dosage.getText()) && !TextUtils.isEmpty(doseUnit.getText()) ) {

                    /*Call<Medicine> call = medfetch.postMedicine(1, newMed.getMedName(), newMed.getMedFreqPerTime(), newMed.getMedFreqInterval(), newMed.getDosage(),
                            newMed.getDosageUnit(), newMed.getExpirationDate(), 30, newMed.getCheckTime());*/
                    newMed.setUser_id(1); //TODO: If login, change this to an actual user id
                    Call<Medicine> call = medfetch.postMedicine(newMed);

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

                Context context = view.getContext();
                Intent intent = new Intent(context, MedListActivity.class);
                context.startActivity(intent);
            }

        });




    }


    @Override
    public void onTimeSelected(int hour, int minute)
    {
        System.out.println(hour);
        System.out.println(minute);


        remindTime = makeDBTime(hour, minute);

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
       String date = "";
        date += year + "-";
        if(month > 8) {
            date += (month + 1) + "-";
        }
        else{
            date += "0" + (month+1) + "-";
        }
        date += day;
        newMed.setExpirationDate(date);

        // fills out exp date box so user sees their input
        expiration.setText((month + 1) + "/" + day + "/" + year);
    }

    private String makeDBTime(int hour, int minute)
    {
        String retVal = "";
        if(hour < 10)
        {
            retVal += "0";
        }
        retVal += Integer.toString(hour);

        retVal += ":";
        if(minute < 10)
        {
            retVal += "0"; // The db can handle no leading 0s, but java needs consistency

        }
        retVal += Integer.toString(minute);

        retVal += ":00";

        return retVal;
    }

    private String makeDBDate(int month, int date, int year)
    {
        String retVal = "";

        retVal += Integer.toString(year);
        retVal += "-";

        if(month < 10) {
            retVal += "0";
            retVal += Integer.toString(month);
        }
        else {
            retVal += Integer.toString(month);
        }
        retVal += "-";
        if(date > 9) {
            retVal += Integer.toString(date);
        }
        else {
            retVal += "0";
            retVal += Integer.toString(date);
        }



        return retVal;
    }


}
