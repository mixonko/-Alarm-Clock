package com.myapp.test.alarmclock.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.entity.DaysOfWeek;
import com.myapp.test.alarmclock.myAppContext.MyApplication;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExampleDaysAdapter extends RecyclerView.Adapter<ExampleDaysAdapter.ExampleViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<String> exampleItems;
    private List<Integer> checkedDays;
    private static int monday = 0;
    private static int tuesday = 0;
    private static int wednesday = 0;
    private static int thursday = 0;
    private static int friday = 0;
    private static int saturday = 0;
    private static int sunday = 0;
    private static String mn = "";
    private static String ts = "";
    private static String wd = "";
    private static String th = "";
    private static String fr = "";
    private static String st = "";
    private static String sn = "";

    public ExampleDaysAdapter(List<String> exampleItems, List<Integer> checkedDays) {
        this.exampleItems = exampleItems;
        this.checkedDays = checkedDays;

    }

    public interface OnItemClickListener {
        void onItemClick(DaysOfWeek daysOfWeek, String pickedDaysText);
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
                                    mn = " пн";
                                }else {
                                    monday = 0;
                                    mn = "";
                                }
                            }
                            if (position == 1){
                                setChecked();
                                if (check.isChecked()){
                                    tuesday = 3;
                                    ts = " вт";
                                }else{
                                    tuesday = 0;
                                    ts = "";
                                }
                            }
                            if (position == 2){
                                setChecked();
                                if (check.isChecked()){
                                    wednesday = 4;
                                    wd = " ср";

                                }else {
                                    wednesday = 0;
                                    wd = "";
                                }
                            }
                            if (position == 3){
                                setChecked();
                                if (check.isChecked()){
                                    thursday = 5;
                                    th = " чт";
                                }else {
                                    thursday = 0;
                                    th = "";
                                }
                            }
                            if (position == 4){
                                setChecked();
                                if (check.isChecked()){
                                    friday = 6;
                                    fr = " пт";
                                }else {
                                    friday = 0;
                                    fr = "";
                                }
                            }
                            if (position == 5){
                                setChecked();
                                if (check.isChecked()){
                                    saturday = 7;
                                    st = " сб";
                                }else {
                                    saturday = 0;
                                    st = "";
                                }
                            }
                            if (position == 6){
                                setChecked();
                                if (check.isChecked()){
                                    sunday = 1;
                                    sn = " вс";

                                }else {
                                    sunday = 0;
                                    sn = "";
                                }
                            }
                            String pickedDaysText;
                            StringBuffer stringBuffer = new StringBuffer();
                            if (monday != 0 && tuesday != 0 && wednesday != 0 && thursday != 0 &&
                                    friday != 0 && saturday != 0 && sunday != 0){
                                pickedDaysText = MyApplication.getAppContext().getString(R.string.everyday);
                            }else {
                                stringBuffer.append(mn);
                                stringBuffer.append(ts);
                                stringBuffer.append(wd);
                                stringBuffer.append(th);
                                stringBuffer.append(fr);
                                stringBuffer.append(st);
                                stringBuffer.append(sn);
                                pickedDaysText = stringBuffer.toString();
                            }
                             
                            if (pickedDaysText.length()==0){
                                pickedDaysText = MyApplication.getAppContext().getString(R.string.without_replay);
                            }

                            DaysOfWeek daysOfWeek = new DaysOfWeek(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                            Toast.makeText(MyApplication.getAppContext(), String.valueOf(daysOfWeek.getMonday()+ daysOfWeek.getTuesday()+ daysOfWeek.getWednesday()+ daysOfWeek.getThursday()+ daysOfWeek
                            .getFriday()+ daysOfWeek.getSaturday() + daysOfWeek.getSunday()), Toast.LENGTH_LONG).show();
                            listener.onItemClick(daysOfWeek, pickedDaysText);

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

    public void clearData(){
        monday = 0;
        tuesday = 0;
        wednesday = 0;
        thursday = 0;
        friday = 0;
        saturday = 0;
        sunday = 0;
        mn = "";
        ts = "";
        wd = "";
        th = "";
        fr = "";
        st = "";
        sn = "";
    }

}
