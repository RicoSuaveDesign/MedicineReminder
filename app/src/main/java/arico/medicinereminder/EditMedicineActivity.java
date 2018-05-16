// Class for editing a given medicine record. Cannot edit checktimes from this activity.

package arico.medicinereminder;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by starb on 4/6/2018.
 */

public class EditMedicineActivity extends Activity implements DatePickerFragment.onDateSelectedListener{

    private Medicine thisMed; // the medicine we are editing

    EditText medName;
    EditText dosage;
    EditText expiration;
    EditText doseUnit;
    EditText medFreqNum;
    EditText howManyDoses;
    EditText purpose;
    TextView tagid;

    int realid;

    Spinner freq;

    Button done, addTime;

    MedInterface medfetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmed);


        // initialize EVERYTHING
        medfetch = MedClient.getClient().create(MedInterface.class);

        thisMed = new Medicine();

        medName = (EditText) findViewById(R.id.medNameText);
        dosage = (EditText) findViewById(R.id.dosageNum);
        expiration = (EditText) findViewById(R.id.expiration);
        doseUnit = (EditText) findViewById(R.id.doseUnit);
        medFreqNum = (EditText) findViewById(R.id.medFreqNum);
        howManyDoses = (EditText) findViewById(R.id.howMany);
        purpose = (EditText) findViewById(R.id.purpose);

        tagid = (TextView) findViewById(R.id.tag_id);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String theid = "Tag ID: ";
            realid = extras.getInt("tag_id");
            theid += realid;
            tagid.setText(theid);

            medName.setText(extras.getString("med_name"));
            dosage.setText(String.valueOf(extras.getFloat("dose")));
            expiration.setText(extras.getString("expiration"));
            doseUnit.setText(extras.getString("unit"));
            medFreqNum.setText(String.valueOf(extras.getInt("freq")));
            howManyDoses.setText(String.valueOf(extras.getInt("dosesLeft")));
            purpose.setText(extras.getString("purpose"));

        }

        freq = (Spinner) findViewById(R.id.medFreqSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        freq.setAdapter(adapter);


        done = (Button) findViewById(R.id.submitButton);
        addTime = (Button) findViewById(R.id.addcheck);

        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(fm, "Date Picker");

            }
        });

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Context context = view.getContext();
                Intent intent = new Intent(context, AddNewTimeActivity.class);
                intent.putExtra("tagid", realid);
                context.startActivity(intent);

            }
        });

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                thisMed.setMedName(medName.getText().toString());
                thisMed.setTag_id(realid);
                thisMed.setMedFreqPerTime(Integer.parseInt(medFreqNum.getText().toString()));
                thisMed.setMed_desc(purpose.getText().toString());

                if(freq.getSelectedItem().toString().equals("Daily"))
                {
                    thisMed.setMedFreqInterval(86400000);
                }
                else if(freq.getSelectedItem().toString().equals("Weekly"))
                {
                    thisMed.setMedFreqInterval(604800000);
                }
                else if(freq.getSelectedItem().toString().equals("Monthly"))
                {
                    thisMed.setMedFreqInterval(2628000000L);

                }


                thisMed.setDosage(Float.parseFloat(dosage.getText().toString()));
                thisMed.setDosageUnit(doseUnit.getText().toString());
                thisMed.setDosesLeft(Integer.parseInt(howManyDoses.getText().toString()));



                //int freq = thisMed.getMedFreqPerTime(); // Save a few function calls

                if(!TextUtils.isEmpty(medName.getText()) && !TextUtils.isEmpty(medFreqNum.getText())
                        && !TextUtils.isEmpty(dosage.getText()) && !TextUtils.isEmpty(doseUnit.getText()) ) {

                    thisMed.setUser_id(1); //TODO: If login, change this to an actual user id
                    Call<Medicine> call = medfetch.postMedicine(thisMed);

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
        thisMed.setExpirationDate(date);

        // fills out exp date box so user sees their input
        expiration.setText((month + 1) + "/" + day + "/" + year);
    }


    }
