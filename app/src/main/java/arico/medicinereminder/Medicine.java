package arico.medicinereminder;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;

import java.util.ArrayList;

/**
 * Created by starb on 11/21/2017.
 */

public class Medicine {

    private String medName;
    private ArrayList<GregorianCalendar> checkTime; // when should the app check if your med has been taken?
    private int medFreqPerTime; // how many times per medFreqInterval should medicine be taken
    long medFreqInterval; // daily, weekly, or monthly in milliseconds. Monthly because there's an osteoperosis med thats monthly
    // TODO: If time, figure out the edge case for two days in a row per month, like Risedronate sometimes has aforementioned dosage
    private float dosage;
    private String dosageUnit;
    private GregorianCalendar expirationDate;
    private ArrayList<GregorianCalendar> timesTaken; // clear this out after the expiration
    private Boolean taken; // this will be determined by fetching from the database. Default is false

    public Medicine()
    {
        medName = new String("");
        checkTime = new ArrayList<>();
        medFreqPerTime = 0;
        medFreqInterval = 0;
        dosage = 0;
        dosageUnit = new String("");
    }
}
