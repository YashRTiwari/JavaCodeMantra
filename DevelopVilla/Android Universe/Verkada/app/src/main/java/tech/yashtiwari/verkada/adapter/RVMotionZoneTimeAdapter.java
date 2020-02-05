package tech.yashtiwari.verkada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.Utils.*;
import tech.yashtiwari.verkada.databinding.RowMotionZoneTimeAdpBinding;
import tech.yashtiwari.verkada.retrofit.entity.DateAndDuration;

public class RVMotionZoneTimeAdapter extends RecyclerView.Adapter<RVMotionZoneTimeAdapter.ViewHolder> {

    private List<DateAndDuration> list = null;
    private Context context;

    public RVMotionZoneTimeAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RVMotionZoneTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowMotionZoneTimeAdpBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_motion_zone_time_adp, parent, false);
        return new RVMotionZoneTimeAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RVMotionZoneTimeAdapter.ViewHolder holder, int position) {


        if(list != null){
            DateAndDuration entity = list.get(position);
            int duration = entity.getDuration();
            int oDuration = entity.getoDuration();
            holder.binding.ivDuration.setImageDrawable(CommonUtility.getSignalDrawable(context, duration));
            holder.binding.tvOriginalDuration.setText(oDuration+" secs");
            holder.binding.tvDay.setText(entity.getMonth()+" "+entity.getDay());
            holder.binding.tvTime.setText(entity.getHour()+" : "+entity.getMinute());
        }

    }

    public void setList(List<DateAndDuration> list){
        this.list  = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowMotionZoneTimeAdpBinding binding;

        public ViewHolder( RowMotionZoneTimeAdpBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
