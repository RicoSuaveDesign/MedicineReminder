package arico.medicinereminder;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

/**
 * Created by starb on 11/25/2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    onTimeSelectedListener mCallback;


    interface onTimeSelectedListener {
        void onTimeSelected(int hour, int minute);
    }

    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    return inflater.inflate(R.layout.activity_newmedicine, container, false);
    //}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof onTimeSelectedListener) {
            mCallback = (onTimeSelectedListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement onTimeSelectedListener");
        }
    }



    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        mCallback.onTimeSelected(hourOfDay, minute);
    }
}
