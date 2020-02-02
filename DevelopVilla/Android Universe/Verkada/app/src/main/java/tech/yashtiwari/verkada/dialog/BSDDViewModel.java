package tech.yashtiwari.verkada.dialog;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BSDDViewModel extends ViewModel {

    public MutableLiveData<List<Integer>> mldZones = new MutableLiveData<>();

    public void updateMLDZone(int position){

        if(mldZones.getValue() == null)
            mldZones.setValue(new ArrayList<Integer>());

        List<Integer> list = mldZones.getValue();
        if(list.contains(position)){
            list.remove((Integer) position);
        } else{
            list.add((Integer) position);
        }
        mldZones.setValue(list);
    }


}
