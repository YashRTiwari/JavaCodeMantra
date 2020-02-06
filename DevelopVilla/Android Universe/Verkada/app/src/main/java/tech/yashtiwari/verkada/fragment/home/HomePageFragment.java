package tech.yashtiwari.verkada.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.reactivex.Observable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tech.yashtiwari.verkada.Navigator;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.SharedPred.TinyDB;
import tech.yashtiwari.verkada.Utils.CommonUtility;
import tech.yashtiwari.verkada.adapter.RVMotionZoneTimeAdapter;
import tech.yashtiwari.verkada.databinding.HomePageFragmentBinding;
import tech.yashtiwari.verkada.dialog.BottomSheetDateDailog;
import tech.yashtiwari.verkada.retrofit.entity.DateAndDuration;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;

public class HomePageFragment extends Fragment{

    private Navigator navigator;
    public static final String TAG = "HomePageFragment";
    private HomePageFragmentBinding binding;
    private BottomSheetDateDailog dialog;
    private  RVMotionZoneTimeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static HomePageFragment instance = null;
    private TinyDB tinyDB;

    private HomePageFragment(Navigator navigator){
        this.navigator = navigator;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_page_fragment, container, false);
        binding.setViewModel(getmViewModel());

        if (getArguments() != null){
            Bundle data = getArguments();
            long startDate = data.getLong("start_time");
            long endDate = data.getLong("end_time");
            final ArrayList<Integer> zones = data.getIntegerArrayList("zones");
            MotionSearchBody body = new MotionSearchBody();
            body.setStartTimeSec(startDate/1000);
            body.setEndTimeSec(endDate/1000);
            ArrayList<List<Integer>> k = CommonUtility.getListOfArray(zones);
            body.setMotionZones(k);
            String hashCode = CommonUtility.generateUniqueHashCodeForArrayList(zones);
            getmViewModel().checkIfInCache(body, hashCode);
        }


        return binding.getRoot();
    }



    private void subscribeToLiveData(){
        getmViewModel().mldMotionAt.observe(this, new Observer<List<DateAndDuration>>() {
            @Override
            public void onChanged(List<DateAndDuration> lists) {
                if(lists != null){
                    mAdapter.setList(lists);
                }
            }
        });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new RVMotionZoneTimeAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        binding.rvMotionAt.setLayoutManager(layoutManager);
        binding.rvMotionAt.setAdapter(mAdapter);
        subscribeToLiveData();
    }




}
