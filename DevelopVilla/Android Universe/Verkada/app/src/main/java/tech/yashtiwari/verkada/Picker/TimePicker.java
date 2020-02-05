package tech.yashtiwari.verkada.Picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "TimePicker";

    public interface TimePicerListener{
        public void onTimeSet(int hourOfDay, int minute);
    }

    TimePicerListener listener;

    public TimePicker(TimePicerListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, true);
        // Create a new instance of TimePickerDialog and return it
        return dialog;
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        listener.onTimeSet(hourOfDay, minute);
    }

    public void removeListener(){
        this.listener = null;
    }
}
