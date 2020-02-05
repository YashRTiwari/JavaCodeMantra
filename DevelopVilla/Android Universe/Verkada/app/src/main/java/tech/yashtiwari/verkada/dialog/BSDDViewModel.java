package tech.yashtiwari.verkada.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.yashtiwari.verkada.Navigator;
import tech.yashtiwari.verkada.SharedPred.TinyDB;
import tech.yashtiwari.verkada.Utils.Constant;


public class BSDDViewModel extends ViewModel {

    private static final String TAG = "BSDDViewModel";
    private final CommunicateInterface communicateInterface;
    private Context context;
    private Navigator navigator;

    public ObservableArrayList<Integer> zones = new ObservableArrayList<>();

    public MutableLiveData<List<Integer>> mldZoneList = new MutableLiveData<>();

    public MutableLiveData<Long> oiStartDate = new MutableLiveData<Long>();
    public MutableLiveData<Long> oiEndDate = new MutableLiveData<Long>();
    public MutableLiveData<Long> oiStartTime = new MutableLiveData<Long>();
    public MutableLiveData<Long> oiEndTime = new MutableLiveData<Long>();


    public BSDDViewModel(Context context, Navigator navigator, CommunicateInterface communicateInterface) {
        this.context = context;
        this.navigator = navigator;
        this.communicateInterface = communicateInterface;
        Log.d(TAG, "BSDDViewModel: ");
    }


    /*TODO*/
    public void updateListZones(int position, boolean add) {
        Log.d(TAG, "updateListZones: ");
        if (!add)
            zones.remove((Integer) position);
        else
            zones.add(position);
        communicateInterface.pushDataToAdapterList(position, add);
    }

    public void update() {
        Log.d(TAG, "update: ");
        mldZoneList.setValue(zones);
    }

    public ArrayList<Integer> getListZones() {
        return this.zones;
    }

    public void setOiStartDate(long oiStartDate) {
        Log.d(TAG, "setOiStartDate: " + oiStartDate);
        this.oiStartDate.setValue(oiStartDate);
    }

    public void setOiEndDate(long oiEndDate) {
        Log.d(TAG, "setOiEndDate: "+oiEndDate);
        this.oiEndDate.setValue(oiEndDate);
    }

    public void setOiStartTime(long oiStartTime) {

        Log.d(TAG, "setOiStartTime: "+oiStartTime);
        this.oiStartTime.setValue(oiStartTime);
    }

    public void setOiEndTime(long oiEndTime) {

        Log.d(TAG, "setOiEndTime: "+oiEndTime);
        this.oiEndTime.setValue(oiEndTime);
    }

    private long getStartDateTime() {
        if (oiStartDate != null){
            if (oiStartTime != null){
                if (oiStartTime.getValue() != 0)
                    return oiStartTime.getValue();
            }
            return oiStartDate.getValue();
        } else
            return 0;



    }

    private long getEndDateTime() {

        if (oiEndDate != null){
            if (oiEndTime != null){
                if (oiEndTime.getValue() != 0)
                    return oiEndTime.getValue();
            }
            else return oiEndDate.getValue();
        }
        return 0;


    }

    public void moveDataToHomePage() {

        if (getStartDateTime() > getEndDateTime()) {
            Toast.makeText(context, "Time invalid", Toast.LENGTH_SHORT).show();
        } else if (getEndDateTime() == 0) {
            Toast.makeText(context, "Select End Time", Toast.LENGTH_SHORT).show();
        } else if (getStartDateTime() == 0) {
            Toast.makeText(context, "Select Start Time", Toast.LENGTH_SHORT).show();
        } else if (getListZones().size() == 0){
            Toast.makeText(context, "Select Zones", Toast.LENGTH_SHORT).show();
        }else {

            saveToPref();

            Bundle bundle = new Bundle();
            bundle.putLong("start_time", getStartDateTime());
            bundle.putLong("end_time", getEndDateTime());
            bundle.putIntegerArrayList("zones", getListZones());
            navigator.moveToHomeFragment(bundle);
        }
    }

    private void saveToPref() {

        TinyDB tinyDB = new TinyDB(context);
        tinyDB.putLong(Constant.START_DATE, oiStartDate.getValue());
        tinyDB.putLong(Constant.END_DATE, oiEndTime.getValue());

    }
}
