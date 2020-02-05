package tech.yashtiwari.verkada.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.databinding.RowSelectZonesBinding;

public class GVSelectZones extends RecyclerView.Adapter<GVSelectZones.ViewHolder> {

    private static final String TAG = GVSelectZones.class.getName();
    private Context context;

    private double height;
    private TbListener listener;
    private List<Integer> zoneList = new ArrayList<>();

    public interface TbListener {
        void tbCheckListener(boolean isChecked, int position);
    }

    public GVSelectZones(Context context, float height, TbListener listener ) {
        this.context = context;
        this.listener = listener;
        this.height = height / 10.0;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowSelectZonesBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_select_zones, parent, false);
        return new GVSelectZones.ViewHolder(binding);
    }

    public void addRemoveZones(int zone, boolean add) {
        boolean contains =  zoneList.contains(zone);
        Log.d(TAG, "addRemoveZones: ");
        if (!contains && add) {
            this.zoneList.add(zone);
            notifyItemChanged(zone);
        } else if (contains && !add){
            this.zoneList.remove((Integer) zone);
            notifyItemChanged(zone);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) height);
        holder.binding.tb.setLayoutParams(lp);

        if (zoneList != null) {
            if (zoneList.size() > 0) {

                if (zoneList.contains(position)) {
                    Log.d(TAG, "onBindViewHolder: contains");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.binding.tb.setBackgroundDrawable(context.getDrawable(R.drawable.selected_border));
                        holder.binding.tb.setChecked(true);
                    }
                } else {
                    Log.d(TAG, "onBindViewHolder: doesnt contains");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.binding.tb.setBackgroundDrawable(context.getDrawable(R.drawable.border));
                        holder.binding.tb.setChecked(false);
                    }
                }

            }
        }
    }


    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        RowSelectZonesBinding binding;

        public ViewHolder(RowSelectZonesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.tb.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            listener.tbCheckListener(isChecked, getAdapterPosition());
        }
    }


}
