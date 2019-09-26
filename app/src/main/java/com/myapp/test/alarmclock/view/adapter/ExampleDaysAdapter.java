package com.myapp.test.alarmclock.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.myAppContext.MyApplication;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExampleDaysAdapter extends RecyclerView.Adapter<ExampleDaysAdapter.ExampleViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<String> exampleItems;
    private List<Integer> checkedDays;
    private static int monday = 111;
    private static int tuesday = 111;
    private static int wednesday = 111;
    private static int thursday = 111;
    private static int friday = 111;
    private static int saturday = 111;
    private static int sunday = 111;

    public ExampleDaysAdapter(List<String> exampleItems, List<Integer> checkedDays) {
        this.exampleItems = exampleItems;
        this.checkedDays = checkedDays;

    }

    public interface OnItemClickListener {
        void onItemClick(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView day;
        public CheckBox check;


        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener ) {
            super(itemView);
            day = itemView.findViewById(R.id.dayOfWeek);
            check = itemView.findViewById(R.id.check);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (position == 0){
                                setChecked();
                                if (check.isChecked()){
                                    monday = 2;
                                }else {
                                    monday = 0;
                                }
                            }
                            if (position == 1){
                                setChecked();
                                if (check.isChecked()){
                                    tuesday = 3;
                                }else{
                                    tuesday = 0;
                                }
                            }
                            if (position == 2){
                                setChecked();
                                if (check.isChecked()){
                                    wednesday = 4;
                                }else {
                                    wednesday = 0;
                                }
                            }
                            if (position == 3){
                                setChecked();
                                if (check.isChecked()){
                                    thursday = 5;
                                }else {
                                    thursday = 0;
                                }
                            }
                            if (position == 4){
                                setChecked();
                                if (check.isChecked()){
                                    friday = 6;
                                }else {
                                    friday = 0;
                                }
                            }
                            if (position == 5){
                                setChecked();
                                if (check.isChecked()){
                                    saturday = 7;
                                }else {
                                    saturday = 0;
                                }
                            }
                            if (position == 6){
                                setChecked();
                                if (check.isChecked()){
                                    sunday = 1;
                                }else {
                                    sunday = 0;
                                }
                            }
                            listener.onItemClick( monday, tuesday, wednesday, thursday,
                                    friday, saturday, sunday);

                        }
                    }
                }
            });

        }

        private void setChecked(){
            check.setChecked(!check.isChecked());
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
        Boolean isChecked = checkedDays.get(i) !=0 ? true : false;
        exampleViewHolder.day.setText(currentItem);
        exampleViewHolder.check.setChecked(isChecked);
    }

    @Override
    public int getItemCount() {
        return exampleItems.size();
    }

}
