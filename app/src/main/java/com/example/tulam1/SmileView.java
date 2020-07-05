package com.example.tulam1;

import android.content.Context;
import android.graphics.Canvas;
//import android.graphics.Color;
import android.graphics.Color;
import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.RectF;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class SmileView extends View {
    Paint pacmanPaint;
    Paint eyeOfPacmanPaint;

    Path path = new Path();
    Paint paint = new Paint();

    public SmileView(Context context) {
        this(context,null);
        init();
    }
    public SmileView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }
    public SmileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    public SmileView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init() {
        pacmanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pacmanPaint.setColor(Color.RED);
        eyeOfPacmanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        eyeOfPacmanPaint.setColor(getResources().getColor(R.color.colorAccent));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int square = 300;
//        float top = 100;
//        float left = 100;
//        float right = left + square;
//        float bottom = top + square;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawArc(left, top, right, bottom, 30, 300, true, pacmanPaint);
//
//        }
//
//        float cx = left + 180;
//        float cy = top + 70;
//        float radius = 25;
//        canvas.drawCircle(cx, cy, radius, eyeOfPacmanPaint);

        canvas.drawArc(50,50,500,500,50,300,true,pacmanPaint);
        canvas.drawCircle(360,150,70,eyeOfPacmanPaint);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                path.addCircle(eventX, eventY, 20, Path.Direction.CW);
                break;
            case MotionEvent.ACTION_CANCEL: {
                path.addCircle(eventX, eventY, 20, Path.Direction.CW);
                break;
            }
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
