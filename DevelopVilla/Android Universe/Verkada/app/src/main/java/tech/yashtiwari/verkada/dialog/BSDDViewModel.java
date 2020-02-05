package tech.yashtiwari.verkada.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.yashtiwari.verkada.Navigator;

import static tech.yashtiwari.verkada.Utils.Constant.TAG_YASH;

public class BSDDViewModel extends ViewModel {


    private Context context;
    private Navigator navigator;
    public List<Integer> zones = new ArrayList<>();
    public MutableLiveData<Integer> mldZones = new MutableLiveData<>();
    private static final String TAG = "BSDDViewModel";
    ObservableBoolean configurationChange = new ObservableBoolean(false);


    public BSDDViewModel(Context context, Navigator navigator) {
        this.context = context;
        this.navigator = navigator;
    }


    public void updateListZones(int position, boolean add) {
        boolean contains = zones.contains(position);
        if (!contains && add){
            zones.add(position);
            mldZones.setValue(position);

        } else if (contains && !add){
            zones.remove((Integer) position);
            mldZones.setValue(position);

        }

    }


    public ArrayList<Integer> getListZones() {
        return (ArrayList<Integer>) this.zones;
    }

    public void moveDataToHomePage(long start, long end) {

        Bundle bundle = new Bundle();
        bundle.putLong("start_time", start);
        bundle.putLong("end_time", end);
        bundle.putIntegerArrayList("zones", getListZones());
        navigator.moveToHomeFragment(bundle);

    }
}
