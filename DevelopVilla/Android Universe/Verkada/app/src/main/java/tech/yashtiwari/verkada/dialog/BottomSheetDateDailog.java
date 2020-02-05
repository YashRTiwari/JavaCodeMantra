package tech.yashtiwari.verkada.dialog;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Calendar;
import java.util.List;

import tech.yashtiwari.verkada.Navigator;
import tech.yashtiwari.verkada.Picker.DatePicker;
import tech.yashtiwari.verkada.Picker.TimePicker;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.SharedPred.TinyDB;
import tech.yashtiwari.verkada.Utils.*;
import tech.yashtiwari.verkada.Utils.Constant;
import tech.yashtiwari.verkada.adapter.GVSelectZones;
import tech.yashtiwari.verkada.databinding.BottomSheetDateDialogLayoutBinding;

import static tech.yashtiwari.verkada.Utils.Constant.END_DATE;
import static tech.yashtiwari.verkada.Utils.Constant.START_DATE;
import static tech.yashtiwari.verkada.Utils.Constant.TAG_YASH;


public class BottomSheetDateDailog extends Fragment implements View.OnClickListener,
        DatePicker.DatePickerListener,
        TimePicker.TimePicerListener,
        GVSelectZones.TbListener,
        CommunicateInterface {

    private static BottomSheetDateDailog instance = null;
    private BottomSheetDateDialogLayoutBinding binding;
    public static final String TAG = "BottomSheetDateDailog";
    private Constant.Date whichDate;
    private Constant.Time whichTime;
    TimePicker timePicker;
    DatePicker datePicker;
    private Calendar startCalender = Calendar.getInstance();
    private Calendar endCalender = Calendar.getInstance();
    private GVSelectZones gvAdapter;
    BSDDViewModel viewModel;
    static float width;
    static float height;
    private Navigator navigator;
    private boolean revertAction = true;
    private TinyDB tinyDB;


    @Override
    public void tbCheckListener(boolean isChecked, int position) {
        viewModel.updateListZones(position, isChecked);
        Log.d(TAG_YASH, "tbCheckListener: ");
    }


    public BottomSheetDateDailog() {

    }

    private BottomSheetDateDailog(Navigator navigator) {
        this.navigator = navigator;
    }

    public static BottomSheetDateDailog getInstance(Navigator navigator) {
        if (instance == null) {
            instance = new BottomSheetDateDailog(navigator);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG_YASH, "onCreateView: ");
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = (float) ((float) 3 * (width / 4.0));
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_date_dialog_layout,
                container, false);
        viewModel = getViewModel();
        binding.setViewModel(viewModel);
        tinyDB = new TinyDB(getContext());
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setUpRVZones() {
        gvAdapter = new GVSelectZones(getContext(), height, this);
        binding.rvSelectZone.setAdapter(gvAdapter);
        binding.rvSelectZone.setLayoutManager(new GridLayoutManager(getContext(), 10));
        Log.d(TAG_YASH, "setUpRVZones: ");
    }

    private BSDDViewModel getViewModel() {
        Log.d(TAG_YASH, "getViewModel: ");
        return ViewModelProviders
                .of(this, new BSDDViewModelFactory(getContext(), navigator, this))
                .get(BSDDViewModel.class);
    }

    public void setListeners() {
        binding.tvEndDate.setOnClickListener(this);
        binding.tvEndTime.setOnClickListener(this);
        binding.tvStartDate.setOnClickListener(this);
        binding.tvStartTime.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        Log.d(TAG_YASH, "setListeners: ");

        binding.rvSelectZone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final float w10 = width / 10;
                final float h10 = height / 10;

                int x = (int) event.getX();
                int y = (int) event.getY();

                int i = (int) Math.floor(x / w10);
                int j = (int) Math.floor(y / h10);

                if ((i >= 0 || i < 10) && (j >= 0 || j < 10)) {
                    int position = j * 10 + i;
                    viewModel.updateListZones(position, revertAction);
                }
                return false;
            }
        });

        binding.tbSelectDeselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                revertAction = isChecked;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG_YASH, "onViewCreated: ");
        setUpRVZones();
        setListeners();
        subscribeToLiveData();

        viewModel.setOiStartDate(tinyDB.getLong(START_DATE, 0l));
        viewModel.setOiEndDate(tinyDB.getLong(END_DATE, 0l));
        viewModel.setOiStartTime(tinyDB.getLong(START_DATE, 0l));
        viewModel.setOiEndTime(tinyDB.getLong(END_DATE, 0l));

    }

    public void subscribeToLiveData() {

        viewModel.mldZoneList.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                Log.d(TAG_YASH, "onChanged: ");
                if (integers != null)
                    if (integers.size() > 0) {
                        Log.d(TAG_YASH, "onChanged: >0");
                        for (Integer x : integers)
                            gvAdapter.addRemoveZones(x, true);
                    }
            }
        });

        viewModel.oiEndTime.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong != 0l)
                    binding.tvEndTime.setText(CommonUtility.getTimeInString(aLong));
            }
        });

        viewModel.oiStartTime.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong != 0l)
                    binding.tvStartTime.setText(CommonUtility.getTimeInString(aLong));

            }
        });

        viewModel.oiEndDate.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {

                if (aLong != 0l)
                    binding.tvEndDate.setText(CommonUtility.getDateInString(aLong));

            }
        });

        viewModel.oiStartDate.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong != 0l || aLong != null)
                    binding.tvStartDate.setText(CommonUtility.getDateInString(aLong));

            }
        });
    }

    @Override
    public void onDestroy() {
        Log.d(TAG_YASH, "onDestroy: ");
        viewModel.update();
        super.onDestroy();

    }

    @BindingAdapter("layout_height")
    public static void setLayoutHeight(View view, int h) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) height;
        view.setLayoutParams(layoutParams);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (datePicker != null)
            datePicker.removeListener();

        if (timePicker != null)
            timePicker.removeListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvStartDate:
                openDatePicker(Constant.Date.START);
                break;
            case R.id.tvEndDate:
                openDatePicker(Constant.Date.END);
                break;
            case R.id.tvStartTime:
                openTimePicker(Constant.Time.START);
                break;
            case R.id.tvEndTime:
                openTimePicker(Constant.Time.END);
                break;
            case R.id.btnSubmit:
                viewModel.moveDataToHomePage();
                break;
        }
    }


    private void openTimePicker(Constant.Time time) {
        whichTime = time;
        timePicker = new TimePicker(this);
        timePicker.show(getFragmentManager(), TimePicker.TAG);
    }

    private void openDatePicker(Constant.Date date) {
        whichDate = date;
        if (whichDate == Constant.Date.START){
            datePicker = new DatePicker(this, System.currentTimeMillis(), 0);
        } else {
            datePicker = new DatePicker(this, System.currentTimeMillis(), startCalender.getTimeInMillis());
        }
        
        datePicker.show(getFragmentManager(), DatePicker.TAG);
    }


    @Override
    public void onDateSet(int year, int month, int dayOfMonth) {
        Log.d(TAG_YASH, "onDateSet: "+year+" "+month+" "+dayOfMonth);

        if (whichDate == Constant.Date.START) {
            startCalender.set(year, month-1, dayOfMonth);
            viewModel.setOiStartDate(startCalender.getTimeInMillis());
        } else if (whichDate == Constant.Date.END) {
            endCalender.set(year, month-1, dayOfMonth);
            viewModel.setOiEndDate(endCalender.getTimeInMillis());
        }
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        Log.d(TAG_YASH, "onDateSet: "+hourOfDay+" "+minute);

        if (whichTime == Constant.Time.START) {
            startCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startCalender.set(Calendar.MINUTE, minute);
            viewModel.setOiStartTime(startCalender.getTimeInMillis());
        } else if (whichTime == Constant.Time.END) {
            endCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endCalender.set(Calendar.MINUTE, minute);
            viewModel.setOiEndTime(endCalender.getTimeInMillis());
        }
    }


    @Override
    public void pushDataToAdapterList(int pos, boolean add) {
        Log.d(TAG_YASH, "pushDataToAdapterList: ");
        if (gvAdapter != null)
            gvAdapter.addRemoveZones(pos, add);
    }
}