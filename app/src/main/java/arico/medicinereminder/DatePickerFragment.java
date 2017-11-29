package arico.medicinereminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by starb on 11/28/2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    onDateSelectedListener mCallback;


    public interface onDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof onDateSelectedListener) {
            mCallback = (onDateSelectedListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement onDateSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        mCallback.onDateSelected(year, month, day);
    }
}
