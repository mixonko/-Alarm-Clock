package com.myapp.test.alarmclock.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnChangeListener onChangedListener;

    private List<AlarmClock> exampleItems;

    public ExampleAdapter(List<AlarmClock> exampleItems) {
        this.exampleItems = exampleItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public interface OnChangeListener{
        void onChangedListener(int position, boolean b);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        onItemLongClickListener = listener;
    }

    public void setOnCheckedChangeListener(OnChangeListener listener){
        onChangedListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView daysOfWeek;
        public androidx.appcompat.widget.SwitchCompat mySwitch;
        public TextView description;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener,
                                 final OnItemLongClickListener longClickListener,
                                 final OnChangeListener changedListener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            daysOfWeek = itemView.findViewById(R.id.days_of_week);
            mySwitch = itemView.findViewById(R.id.mySwitch);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(longClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            longClickListener.onItemLongClick(position);
                        }
                    }
                    return true;
                }
            });

            mySwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (changedListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            changedListener.onChangedListener(position, mySwitch.isChecked());
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_alarms_item, viewGroup, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v, onItemClickListener, onItemLongClickListener, onChangedListener);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        AlarmClock currentItem = exampleItems.get(i);
        exampleViewHolder.time.setText(currentItem.getHour() + ":" + currentItem.getMinute());
        exampleViewHolder.description.setText(currentItem.getDescription() + ", ");
        exampleViewHolder.daysOfWeek.setText(currentItem.getPickedDays());
        exampleViewHolder.mySwitch.setChecked(currentItem.getAlarmClockOn());

    }

    @Override
    public int getItemCount() {
        return exampleItems.size();
    }

    public void updateData(List<AlarmClock> list) {
        exampleItems.clear();
        exampleItems.addAll(list);
        notifyDataSetChanged();
    }
}
