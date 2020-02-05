package tech.yashtiwari.verkada.Picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import tech.yashtiwari.verkada.Utils.Constant;

public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerListener mListener;
    public static final String TAG = "DatePicker";
    private long maxDate;
    private long minDate;

    public DatePicker(DatePickerListener listener,  long maxDate, long minDate){
        this.mListener = listener;
        this.minDate = minDate;
        this.maxDate =maxDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        android.widget.DatePicker picker = dialog.getDatePicker();
        picker.setMaxDate(maxDate);
        picker.setMinDate(minDate);

        return dialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        mListener.onDateSet(year, ++month, dayOfMonth);
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface DatePickerListener {
        void onDateSet(int year, int month, int dayOfMonth);
    }

    public void removeListener(){
        this.mListener = null;
    }
}
