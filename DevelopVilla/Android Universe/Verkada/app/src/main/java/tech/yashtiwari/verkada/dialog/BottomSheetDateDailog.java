package tech.yashtiwari.verkada.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.internal.Util;
import tech.yashtiwari.verkada.Picker.DatePicker;
import tech.yashtiwari.verkada.Picker.TimePicker;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.Utils.CommonUtils;
import tech.yashtiwari.verkada.Utils.Constant;
import tech.yashtiwari.verkada.adapter.GVSelectZones;
import tech.yashtiwari.verkada.databinding.BottomSheetDateDialogLayoutBinding;

public class BottomSheetDateDailog extends BottomSheetDialogFragment implements View.OnClickListener, DatePicker.DatePickerListener, TimePicker.TimePicerListener {

    private static BottomSheetDateDailog instance = null;
    private BottomSheetDateDialogLayoutBinding binding;
    public static final String TAG = "BottomSheetDateDailog";
    private Constant.Date whichDate;
    private Constant.Time whichTime;
    TimePicker timePicker;
    DatePicker datePicker;
    TimeZone americaPacific = TimeZone.getTimeZone("UTC");
    private Calendar startCalender = Calendar.getInstance(americaPacific);
    private Calendar endCalender = Calendar.getInstance(americaPacific);
    private DateTimeListener listener;
    int widthpx = Resources.getSystem().getDisplayMetrics().widthPixels;


    public interface DateTimeListener{
        public void getDateTimeValue(long startDate, long endDate);
    }

    private BottomSheetDateDailog(DateTimeListener listener) {
        this.listener = listener;
    }

    public static BottomSheetDateDailog getInstance(DateTimeListener listener) {
        if (instance == null) {
            instance = new BottomSheetDateDailog(listener);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_date_dialog_layout, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnStart.setOnClickListener(this);
        binding.btnEnd.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);


        float width = widthpx;
        float height = (float) (width / 4.0);
        height = 3 * height;
        FrameLayout.LayoutParams newLP = new FrameLayout.LayoutParams((int) width, (int) height);
        binding.gvSelectZone.setAdapter(new GVSelectZones(getContext(), height));
        binding.gvSelectZone.setLayoutParams(newLP);

        binding.iv.setLayoutParams(newLP);

        final float w10 = width/10;
        final float h10 = height/10;

        binding.gvSelectZone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();

                Log.d(TAG, x+" "+y);
                Log.d(TAG, "i : "+(x/w10)+" j:"+(y/h10));

                return true;
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void removeListener(){
        listener = null;
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
            listener.getDateTimeValue(startCalender.getTimeInMillis()/1000, endCalender.getTimeInMillis()/1000);
            this.dismiss();
        }

    }


    private void openTimePicker() {
        timePicker = new TimePicker(this);
        timePicker.show(getFragmentManager(), TimePicker.TAG);
    }

    private void openDatePicker() {
        datePicker = new DatePicker(this);
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
            binding.tvStart.setText(startCalender.getTimeInMillis()/1000+"");

        } else if (whichTime == Constant.Time.END) {
            endCalender.set(Calendar.HOUR, hourOfDay);
            endCalender.set(Calendar.MINUTE, minute);
            binding.tvEnd.setText(endCalender.getTimeInMillis()/1000+"");
        }
    }



}