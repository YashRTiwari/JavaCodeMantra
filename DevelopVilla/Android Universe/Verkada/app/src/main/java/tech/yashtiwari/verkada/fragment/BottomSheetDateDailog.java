package tech.yashtiwari.verkada.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import tech.yashtiwari.verkada.Picker.DatePicker;
import tech.yashtiwari.verkada.Picker.TimePicker;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.Utils.Constant;
import tech.yashtiwari.verkada.databinding.BottomSheetDateDialogLayoutBinding;

public class BottomSheetDateDailog extends BottomSheetDialogFragment implements View.OnClickListener, DatePicker.DatePickerListener, TimePicker.TimePicerListener {

    private static BottomSheetDateDailog instance = null;
    private BottomSheetDateDialogLayoutBinding binding;
    public static final String TAG = "BottomSheetDateDailog";
    private Constant.Date whichDate;
    private Constant.Time whichTime;
    TimePicker timePicker;
    DatePicker datePicker;
    TimeZone americaPacific = TimeZone.getTimeZone("America/Los_Angeles");
    private Calendar startCalender = Calendar.getInstance(americaPacific);
    private Calendar endCalender = Calendar.getInstance(americaPacific);


    private BottomSheetDateDailog() {
    }

    public static BottomSheetDateDailog getInstance() {

        if (instance == null) {
            instance = new BottomSheetDateDailog();
        }

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_date_dialog_layout, container, false);
        timePicker = new TimePicker(this);
        datePicker = new DatePicker(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnStart.setOnClickListener(this);
        binding.btnEnd.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        datePicker.removeListener();
        timePicker.removeListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnStart) {
            whichDate = Constant.Date.START;
            openDatePicker();
        } else if (id == R.id.btnEnd) {
            whichDate = Constant.Date.END;
            openDatePicker();
        } else if (id == R.id.btnSubmit) {
            Log.d(TAG, "Start Time : " + startCalender.getTimeInMillis() + "");
            Log.d(TAG, "End Time : " + endCalender.getTimeInMillis() + "");
        }

    }


    private void openTimePicker() {
        timePicker.show(getFragmentManager(), TimePicker.TAG);
    }

    private void openDatePicker() {
        datePicker.show(getFragmentManager(), DatePicker.TAG);
    }

    @Override
    public void onDateSet(int year, int month, int dayOfMonth) {
        if (whichDate == Constant.Date.START) {
            startCalender.set(year, month, dayOfMonth);
            whichTime = Constant.Time.START;
        } else if (whichDate == Constant.Date.END) {
            whichTime = Constant.Time.END;
            endCalender.set(year, month, dayOfMonth);
        }
        openTimePicker();
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        if (whichTime == Constant.Time.START) {
            startCalender.set(Calendar.HOUR, hourOfDay);
            startCalender.set(Calendar.MINUTE, minute);
        } else if (whichTime == Constant.Time.END) {
            endCalender.set(Calendar.HOUR, hourOfDay);
            endCalender.set(Calendar.MINUTE, minute);
        }
    }


}
//{
//        "motionZones": [[5,5]],
//        "startTimeSec" : 1500005480,
//        "endTimeSec" :1580186124
//        }
