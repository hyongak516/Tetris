package com.example.tetris.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class GameOverView extends View {
    private Canvas      mCanvas;
    private Bitmap      mBitmap;
    private int         mScreenWidth;
    private int         mScreenHeight;
    private Paint       mGrayPaintD;
    private Paint       mRedPaint;

    public GameOverView(Context context) {
        super(context);
    }

    public GameOverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        mScreenWidth   = metrics.widthPixels;
        mScreenHeight  = metrics.heightPixels;
        mBitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mGrayPaintD = new Paint();
        mGrayPaintD.setColor(Color.rgb(43,43,43));
        mGrayPaintD.setStyle(Paint.Style.FILL_AND_STROKE);
        mRedPaint = new Paint();
        mRedPaint.setColor(Color.rgb(255, 0, 0));
        mRedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mRedPaint.setTextSize(150);
        mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mGrayPaintD);
        mCanvas.drawText("Game Over", mScreenWidth*1/10, mScreenHeight/3, mRedPaint);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
