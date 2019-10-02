package com.myapp.test.alarmclock.view.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.myAppContext.MyApplication;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class ClockView extends View {

    private int height = 0, width = 0;
    private int padding = 0;
    private int fontSize = 0;
    private int numeralSpacing = 0;
    private int handTruncation = 0, hourHandTruncation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private int[] hours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Rect rect = new Rect();


    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing + 90;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        int min = Math.min(width, height);
        radius = min / 2 - padding;
        handTruncation = min / 20;
        hourHandTruncation = min / 17;
        paint = new Paint();
        isInit = true;
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (!isInit) {
            initClock();
        }
        drawOuterCircle(canvas);
        drawNumerals(canvas);
        drawCenterCircle(canvas);
        drawHands(canvas);

        postInvalidateDelayed(500);
    }

    private void drawOuterCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10, paint);

        paint.setColor(Color.WHITE);
        for (int i = 0; i < 60; i++) {
            canvas.drawLine(width / 2, 0 , width / 2, 15, paint);
            canvas.rotate(6, width/2, height/2);
        }
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(width / 2, 0 , width / 2, 30, paint);
            canvas.rotate(30, width/2, height/2);
        }
    }

    private void drawCenterCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(width / 2, height / 2, 12, paint);
    }

    private void drawNumerals(Canvas canvas) {
        paint.setStrokeWidth(6);
        paint.setColor(Color.WHITE);
        paint.setTextSize(fontSize);

        for (int hour : hours) {
            String nrString = String.valueOf(hour);
            paint.getTextBounds(nrString, 0, nrString.length(), rect);

            double angle = Math.PI / 6 * (hour - 3);
            int xNum = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int yNum = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            canvas.drawText(nrString, xNum, yNum, paint);
        }
    }

    private void drawHands(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        float hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;

        float minuteOfDay = calendar.get(Calendar.MINUTE) + 60 * calendar.get(Calendar.HOUR_OF_DAY);
        minuteOfDay = calendar.get(Calendar.HOUR_OF_DAY) > 12 ? minuteOfDay - 12 * 60 : minuteOfDay;
        double angleOfDay = minuteOfDay * 360 / (12 * 60) - 90;
        double radianOfDay = angleOfDay * ((2 * Math.PI / 360));

        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);
        drawHourHand(canvas, radianOfDay);;

        paint.setStrokeWidth(6);
        drawHand(canvas, calendar.get(Calendar.MINUTE), false);
        paint.setStrokeWidth(1);
        paint.setColor(MyApplication.getAppContext().getColor(R.color.colorAccent));
        drawHand(canvas, calendar.get(Calendar.SECOND), false);
    }

    private void drawHourHand(Canvas canvas, double loc) {
        int handRadius = radius - handTruncation - hourHandTruncation;
        canvas.drawLine(width / 2, height / 2,
                (float) (width / 2 + Math.cos(loc) * handRadius),
                (float) (height / 2 + Math.sin(loc) * handRadius),
                paint);

    }

    private void drawHand(Canvas canvas, double loc, boolean isHour) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handRadius = isHour ? radius - handTruncation - hourHandTruncation : radius - handTruncation;
        canvas.drawLine(width / 2, height / 2,
                (float) (width / 2 + Math.cos(angle) * handRadius),
                (float) (height / 2 + Math.sin(angle) * handRadius), paint);

    }
}
