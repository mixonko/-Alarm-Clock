package com.myapp.test.alarmclock.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.CreateContract;
import com.myapp.test.alarmclock.presenter.CreatePresenter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends Activity implements CreateContract.view {

    private CreateContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(R.string.create_title);
//        actionBar.setHomeButtonEnabled(true);

//        presenter = new CreatePresenter(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.done:
//                presenter.onDoneWasClicked();
//                break;
//            case android.R.id.home:
//                presenter.onCancelWasClicked();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void cancel() {
        finish();
    }

    @Override
    public void done() {

    }
}
