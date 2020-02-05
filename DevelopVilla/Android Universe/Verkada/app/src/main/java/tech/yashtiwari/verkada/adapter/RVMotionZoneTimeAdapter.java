package tech.yashtiwari.verkada.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.Utils.*;
import tech.yashtiwari.verkada.databinding.RowMotionZoneTimeAdpBinding;

public class RVMotionZoneTimeAdapter extends RecyclerView.Adapter<RVMotionZoneTimeAdapter.ViewHolder> {

    private List<List<Long>> list = null;

    public RVMotionZoneTimeAdapter(){
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
            holder.binding.tvTime.setText(CommonUtility.getDateTimeInString(list.get(position).get(0)));
            holder.binding.tvDuration.setText(list.get(position).get(1).toString());
        }

    }

    public void setList(List<List<Long>> list){
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
