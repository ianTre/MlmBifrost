package com.example.bishoppc.pdfaddpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by BishopPC on 22/2/2018.
 */

public class TouchEventView extends View {

    private int width , height;
    public Bitmap mBitmap;
    public Canvas mCanvas;
    public Paint mPaint = new Paint();
    public Path mPath = new Path();
    public boolean painted=false;
    private float mX, mY;
    private static final  float TOLERANCE = 5;
    Context context;

    public TouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        int naranja = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        mPaint.setAntiAlias(true);
        mPaint.setColor(naranja);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4f);

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    private void startTouch(float x, float y ) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

    }

    private void moveTouch(float x , float y )
    {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if ( dx >= TOLERANCE || dy >= TOLERANCE)
        {
            mPath.quadTo(mX , mY , (x + mX) / 2 , ( y + mY) /2 );
            mX = x;
            mY = y;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath , mPaint);
    }

    public void clearCanvas()
    {
        painted=false;
        mPath.reset();
        invalidate();
    }

    private void upTouch()
    {
        mPath.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        TouchEventView touchEventView;
        touchEventView = (TouchEventView)findViewById(R.id.canvas);
        touchEventView.requestFocus();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                painted=true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}

