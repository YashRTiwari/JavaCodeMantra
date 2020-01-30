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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.Utils.CommonUtils;
import tech.yashtiwari.verkada.adapter.RVMotionZoneTimeAdapter;
import tech.yashtiwari.verkada.databinding.HomePageFragmentBinding;
import tech.yashtiwari.verkada.dialog.BottomSheetDateDailog;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;

public class HomePageFragment extends Fragment implements BottomSheetDateDailog.DateTimeListener {

    private HomePageViewModel mViewModel;
    public static final String TAG = "HomePageFragment";
    private HomePageFragmentBinding binding;
    private BottomSheetDateDailog dialog;
    private  RVMotionZoneTimeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_page_fragment, container, false);
        return binding.getRoot();
    }

    private void subscribeToLiveData(){
        mViewModel.mldMotionAt.observe(this, new Observer<List<List<Long>>>() {
            @Override
            public void onChanged(List<List<Long>> lists) {
                if(lists != null){

                    mAdapter.setList(lists);
                } else {
                    binding.tv.setText("NO Data found");
                }
            }
        });

        mViewModel.mldNextEndTime.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long nxtEndTime) {
                binding.tv.setText(CommonUtils.getDateTimeInString(nxtEndTime));
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);
        mAdapter = new RVMotionZoneTimeAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        binding.rvMotionAt.setLayoutManager(layoutManager);
        binding.rvMotionAt.setAdapter(mAdapter);

        binding.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartEndTimeSelector();
            }
        });

        subscribeToLiveData();
    }

    private void openStartEndTimeSelector(){
        dialog = BottomSheetDateDailog.getInstance(HomePageFragment.this);
        dialog.show(getActivity().getSupportFragmentManager(), BottomSheetDateDailog.TAG);
        dialog.setCancelable(false);
    }

    @Override
    public void getDateTimeValue(long startDate, long endDate) {
        MotionSearchBody body = new MotionSearchBody();
        body.setStartTimeSec(startDate);
        body.setEndTimeSec(endDate);
        List<Integer>  list = new ArrayList<>();
        list.add(5);
        list.add(5);
        List<List<Integer>> ll = new ArrayList<>();
        ll.add(list);
        body.setMotionZones(ll);

        mViewModel.checkIfInCache(startDate, endDate, body);
        /*Check if database contains the object*/


    }


}
