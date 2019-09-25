package com.myapp.test.alarmclock.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExampleDaysAdapter extends RecyclerView.Adapter<ExampleDaysAdapter.ExampleViewHolder> {
    private OnItemClickListener onItemClickListener;

    private List<String> exampleItems;

    public ExampleDaysAdapter(List<String> exampleItems) {
        this.exampleItems = exampleItems;
    }

    public interface OnItemClickListener {
        void onItemClick(String day);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ToggleButton dayOfWeek;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener ) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        String day = dayOfWeek.getText().toString();
                        if (position != RecyclerView.NO_POSITION) {
                            if (dayOfWeek.isChecked())
                            listener.onItemClick(day);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_days_item, viewGroup, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v, onItemClickListener);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        String currentItem = exampleItems.get(i);
        exampleViewHolder.dayOfWeek.setText(currentItem);

    }

    @Override
    public int getItemCount() {
        return exampleItems.size();
    }

}
