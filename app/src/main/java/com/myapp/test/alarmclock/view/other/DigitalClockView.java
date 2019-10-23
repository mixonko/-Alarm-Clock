package com.myapp.test.alarmclock.view.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.myapp.test.alarmclock.myAppContext.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.myapp.test.alarmclock.view.MainActivity.UPDATE;

public class DigitalClockView extends View {

    private int height = 0, width = 0;
    private int min;
    private int positionX;
    private int positionY;
    private Paint paint;
    private boolean isInit;

    public DigitalClockView(Context context) {
        super(context);
    }

    public DigitalClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DigitalClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        min = Math.min(width, height);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(min / 5);
        paint.setStrokeWidth(5);
        paint.setTextAlign(Paint.Align.CENTER);
        positionX = (getWidth() / 2);
        positionY = (int) ((getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        isInit = true;
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (!isInit) {
            initClock();
        }
        drawClock(canvas);
        postInvalidateDelayed(100);
    }

    private void drawClock(Canvas canvas){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
        String datetime = dateformat.format(calendar.getTime());
        canvas.drawText(datetime, positionX, positionY, paint);

        if (calendar.get(Calendar.SECOND) == 0){
            Intent myIntent = new Intent();
            myIntent.setAction(UPDATE);
            MyApplication.getAppContext().sendBroadcast(myIntent);
        }
    }

}
