package arico.medicinereminder;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 5/9/2018.
 */

public class AddNewTimeActivity extends Activity implements TimePickerFragment.onTimeSelectedListener, DatePickerFragment.onDateSelectedListener {

    EditText time;
    EditText date;

    Button doneTime;
    View deleteTime;

    String checktime;
    String checkdate;
    int tagid;

    MedInterface timefetch;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittimes);

        timefetch = MedClient.getClient().create(MedInterface.class);

        time = (EditText) findViewById(R.id.timebox);
        date = (EditText) findViewById(R.id.datebox);

        doneTime = (Button) findViewById(R.id.donetime);
        deleteTime =  findViewById(R.id.removetime);
        deleteTime.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        checktime = "";
        checkdate = "";


        tagid = extras.getInt("tagid");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(fm, "Date Picker");

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                TimePickerFragment tpf = new TimePickerFragment();
                tpf.show(fm, "Time Picker");

            }
        });

        doneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Call<CheckTime> call = timefetch.addTime(tagid, checktime, checkdate);

                call.enqueue(new Callback<CheckTime>() {
                    @Override
                    public void onResponse(Call<CheckTime>call, Response<CheckTime> response) {

                        if(response.isSuccessful()) {
//                                showResponse(response.body().toString());
                            Log.i(TAG, "time update submitted");
                        }

                    }

                    @Override
                    public void onFailure(Call<CheckTime>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });

                Context context = view.getContext();
                Intent intent = new Intent(context, ListTimesActivity.class);
                intent.putExtra("mid", tagid);
                context.startActivity(intent);
            }





        });






    }
    @Override
    public void onTimeSelected(int hour, int minute)
    {
        System.out.println(hour);
        System.out.println(minute);
        checktime = makeDBTime(hour, minute);
        String ampm = "AM";

        String timeString = "";
        if(hour > 12){
            hour -= 12;
            ampm = "PM";
        }
        else if(hour == 12){
            ampm = "PM";
        }
        if(hour < 10){
            timeString += "0";

        }
        timeString += hour;
        timeString += ":";

        if(minute < 10){
            timeString += "0";
        }
        timeString += minute;

        timeString += ":00 ";
        timeString += ampm;

        time.setText(timeString);


    }

    @Override
    public void onDateSelected(int year, int month, int day)
    {
        System.out.println(day);
        System.out.println(month);
        System.out.println(year);
        checkdate = makeDBDate((month + 1), day, year);

       /* GregorianCalendar date = new GregorianCalendar();
        date.set(year, month, day);
        newMed.setExpirationDate(date);*/
        String tdate = "";
        tdate += year + "-";
        if(month > 8) {
            tdate += (month + 1) + "-";
        }
        else{
            tdate += "0" + (month+1) + "-";
        }
        if(day < 10){
            tdate += "0";
        }
        tdate += day;

        date.setText(tdate);



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
