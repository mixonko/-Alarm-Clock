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
    private List<AlarmClock> exampleItems;

    public ExampleAdapter(List<AlarmClock> exampleItems) {
        this.exampleItems = exampleItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView monday;
        public TextView tuesday;
        public TextView wednesday;
        public TextView thursday;
        public TextView friday;
        public TextView saturday;
        public TextView sunday;
        public Switch mySwitch;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            monday = itemView.findViewById(R.id.monday);
            tuesday = itemView.findViewById(R.id.tuesday);
            wednesday = itemView.findViewById(R.id.wednesday);
            thursday = itemView.findViewById(R.id.thursday);
            friday = itemView.findViewById(R.id.friday);
            saturday = itemView.findViewById(R.id.saturday);
            sunday = itemView.findViewById(R.id.sunday);
            mySwitch = itemView.findViewById(R.id.mySwitch);

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
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_item, viewGroup, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v, onItemClickListener);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        AlarmClock currentItem = exampleItems.get(i);

//        exampleViewHolder.time.set
//        exampleViewHolder.monday.set
//        exampleViewHolder.tuesday.set
//        exampleViewHolder.wednesday.set
//        exampleViewHolder.thursday.set
//        exampleViewHolder.friday.set
//        exampleViewHolder.saturday.set
//        exampleViewHolder.sunday.set
//        exampleViewHolder.mySwitch.set

    }

    @Override
    public int getItemCount() {
        return exampleItems.size();
    }

}
