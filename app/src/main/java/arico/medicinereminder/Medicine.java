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
    private long medFreqInterval; // daily, weekly, or monthly in milliseconds. Monthly because there's an osteoperosis med thats monthly
    // TODO: If time, figure out the edge case for two days in a row per month, like Risedronate sometimes has aforementioned dosage
    private float dosage;
    private String dosageUnit;
    private GregorianCalendar expirationDate;
    private GregorianCalendar lastTimeTaken; // for our purposes we only need the last time the med was taken
    private int timesTaken; //reset to 0 after expiration and confirmed refill
    private int dosesLeft; // this will be used to determine when to refill
    private Boolean taken; // this will be determined by fetching from the database. Default is false

    public Medicine()
    {
        medName = new String("");
        checkTime = new ArrayList<>();
        medFreqPerTime = 0;
        medFreqInterval = 0;
        dosage = 0;
        dosageUnit = new String("");
        expirationDate = new GregorianCalendar();
        lastTimeTaken = new GregorianCalendar();
        timesTaken = 0;
        taken = false;
        dosesLeft = 0;
    }

    // getters and setters
    public String getMedName()
    {
        return medName;
    }
    public void setMedName(String name)
    {
        medName = name;
    }

    public ArrayList<GregorianCalendar> getCheckTime() // returns all check times
    {
        return checkTime;
    }
    public GregorianCalendar getCheckTime(int index) // get a time based on index
    {
        return checkTime.get(index);
    }
    public GregorianCalendar getCheckTime(GregorianCalendar time) // get a time based on a gregcalendar. Returns null if does not exist
    {
        int index = checkTime.indexOf(time);
        if(index == -1)
        {
            return null;
        }
        return checkTime.get(index);
    }
    public void addCheckTime(GregorianCalendar time)
    {
        checkTime.add(time);
    }
    public void removeCheckTime(GregorianCalendar time) // removes time if it exists
    {
        checkTime.remove(time);
    }
    public void removeCheckTime(int index) // removes an index
    {
        checkTime.remove(index);
    }
    public void clearTimes()
    {
        checkTime.clear();
    }

    public int getMedFreqPerTime()
    {
        return medFreqPerTime;
    }
    public void setMedFreqPerTime(int freq)
    {
        medFreqPerTime = freq;
    }

    public long getMedFreqInterval()
    {
        return medFreqInterval;
    }
    public void setMedFreqInterval(long interval)
    {
        medFreqInterval = interval;
    }

    public float getDosage()
    {
        return dosage;
    }
    public void setDosage(float dose)
    {
        dosage = dose;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }
    public void setDosageUnit(String doseUnit)
    {
        dosageUnit = doseUnit;
    }

    public GregorianCalendar getExpirationDate()
    {
        return expirationDate;
    }
    public void setExpirationDate(GregorianCalendar exp)
    {
        expirationDate = exp;
    }

    public GregorianCalendar getLastTimeTaken()
    {
        return lastTimeTaken;
    }
    public void setLastTimeTaken(GregorianCalendar time)
    {
        lastTimeTaken = time;
    }

    public int getTimesTaken()
    {
        return timesTaken;
    }
    public void setTimesTaken(int times)
    {
        timesTaken = times;
    }
    public void incrementTimesTaken()
    {
        timesTaken += 1;
    } // there's not a down increment because you can't untake a medicine

    public Boolean getTaken()
    {
        return taken;
    }
    public void setTaken(Boolean take)
    {
        taken = take;
    }

    public int getDosesLeft()
    {
        return dosesLeft;
    }
    public void setDosesLeft(int left)
    {
        dosesLeft = left;
    }





}
