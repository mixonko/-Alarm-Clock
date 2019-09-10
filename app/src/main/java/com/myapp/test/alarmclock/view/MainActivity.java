package com.myapp.test.alarmclock.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.adapter.ExampleAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private static final String BUNDLE_KEY = "bundle_key";
    private static final String ALARM_CLOCK_KEY = "alarm_clock_key";
    private MainContract.Presenter presenter;
    private Button create;
    private TextView info;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(R.string.alarm_clock_title);

//        presenter = new MainPresenter(this);

        create = findViewById(R.id.create);
        info = findViewById(R.id.info);
//        recyclerView = findViewById(R.id.list);
//        linearLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
//        recyclerView.setLayoutManager(linearLayoutManager);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MyApplication.getAppContext(), CreateActivity.class));
                Toast.makeText(MyApplication.getAppContext(), "SAdasd", Toast.LENGTH_LONG).show();
            }
        });

//        presenter.onCreateActivity();

//        exampleAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                presenter.onItemWasClicked(position);
//            }
//        });
    }

    @Override
    public void setInfoText(String infoText) {

    }

    @Override
    public void startCreateActivity() {
        startActivity(new Intent(MyApplication.getAppContext(), CreateActivity.class));
    }

    @Override
    public void startCreateActivity(AlarmClock alarmClock) {
//        Intent intent = new Intent(MyApplication.getAppContext(), CreateActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(BUNDLE_KEY, (Serializable) alarmClock);
//        intent.putExtra(ALARM_CLOCK_KEY, bundle);
//        startActivity(intent);
    }

    @Override
    public void updateList() {
        exampleAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(List<AlarmClock> list) {
        exampleAdapter = new ExampleAdapter(list);
        recyclerView.setAdapter(exampleAdapter);
    }

}
