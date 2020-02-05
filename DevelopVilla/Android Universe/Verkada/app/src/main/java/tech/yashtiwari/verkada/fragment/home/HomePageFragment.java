package tech.yashtiwari.verkada.fragment.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tech.yashtiwari.verkada.Navigator;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.Utils.*;
import tech.yashtiwari.verkada.adapter.RVMotionZoneTimeAdapter;
import tech.yashtiwari.verkada.databinding.HomePageFragmentBinding;
import tech.yashtiwari.verkada.dialog.BottomSheetDateDailog;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;

public class HomePageFragment extends Fragment{

    private Navigator navigator;
    public static final String TAG = "HomePageFragment";
    private HomePageFragmentBinding binding;
    private BottomSheetDateDailog dialog;
    private  RVMotionZoneTimeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static HomePageFragment instance = null;

    private HomePageFragment(Navigator navigator){
        this.navigator = navigator;
    }

    public HomePageFragment(){

    }

    private HomePageViewModel getmViewModel(){
        return ViewModelProviders
                .of(this)
                .get(HomePageViewModel.class);
    }

    public static HomePageFragment newInstance(Navigator navigator) {
        if (instance == null )
            return instance = new HomePageFragment(navigator);
        else
            return instance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_page_fragment, container, false);
        binding.setViewModel(getmViewModel());

        Bundle data = getArguments();
        long startDate = data.getLong("start_time");
        long endDate = data.getLong("end_time");
        ArrayList<Integer> zones = data.getIntegerArrayList("zones");
        Log.d(TAG, "onCreateView: "+zones);
        MotionSearchBody body = new MotionSearchBody();
        body.setStartTimeSec(startDate);
        body.setEndTimeSec(endDate);
        body.setMotionZones( CommonUtility.getListOfArray(zones));
        getmViewModel().makeAPICall(body);
        return binding.getRoot();
    }



    private void subscribeToLiveData(){
        getmViewModel().mldMotionAt.observe(this, new Observer<List<List<Long>>>() {
            @Override
            public void onChanged(List<List<Long>> lists) {
                if(lists != null){
                    mAdapter.setList(lists);
                } else {
                    binding.tv.setText("No Data found");
                }
            }
        });

        getmViewModel().mldNextEndTime.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long nxtEndTime) {
                binding.tv.setText(CommonUtility.getDateTimeInString(nxtEndTime));
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new RVMotionZoneTimeAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        binding.rvMotionAt.setLayoutManager(layoutManager);
        binding.rvMotionAt.setAdapter(mAdapter);
        subscribeToLiveData();
    }




}
