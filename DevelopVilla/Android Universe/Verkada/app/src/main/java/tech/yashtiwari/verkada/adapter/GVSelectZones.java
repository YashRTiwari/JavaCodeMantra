package tech.yashtiwari.verkada.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.databinding.RowSelectZonesBinding;

public class GVSelectZones extends BaseAdapter {

    private static final String TAG = GVSelectZones.class.getName();
    private final Context context;
    RowSelectZonesBinding binding;
    private double height;

    public GVSelectZones(Context context, float height){
        this.context = context;
        this.height = height/10.0;
    }


    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_select_zones, parent,false);
        binding.tb.setTextOn("Y");
        binding.tb.setTextOff("N");
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) height);
        binding.tb.setLayoutParams(lp);
        return binding.getRoot();
    }
}
