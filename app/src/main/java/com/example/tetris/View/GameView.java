 package com.example.tetris.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tetris.R;
import com.example.tetris.common.SingleTonManager;

import java.util.Random;

 /**
  * toBeFixed List
  * -> find the place by Ctrl + f "to be fixed"
  *
  * next shape
  * the number of vertical lines
  *
  *
  */

class Map {
    public int Fill;
    public int PaintNum;

    Map (int Fill, int PaintNum) {
        this.Fill       = Fill;
        this.PaintNum   = PaintNum;
    }
}

public class GameView extends View {
//  7  Tetromino
//   1      2      3      4      5      6      7
//  ....   ....   ....   ....   ....   ....   ....
//  .@@@   .@@@   .@@@   ..@@   @@@@   .@@.   .@@.
//  ...@   ..@.   .@..   .@@.   ....   .@@.   ..@@
//  ....   ....   ....   ....   ....   ....   ....

//  mTetromino[도형종류][y좌표][x좌표]
    private int[][][]   mTetromino = new int[][][] {

            {
                    {0, 0, 0, 0},
                    {0, 1, 1, 1},
                    {0, 0, 0, 1},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {0, 1, 1, 1},
                    {0, 0, 1, 0},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {0, 1, 1, 1},
                    {0, 1, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {0, 0, 1, 1},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {0, 1, 1, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {0, 1, 1, 0},
                    {0, 0, 1, 1},
                    {0, 0, 0, 0}
            }
    };
    private Map[][]     mMap;

    private final int   MAP_EMPTY  = 0;
    private final int   MAP_FILL   = 1;

    private final int   BLOCK_WIDTH_NUM  = 12;
    private final int   BLOCK_HEIGHT_NUM = 19;

    private int         mScreenWidth;
    private int         mScreenHeight;
    private int         mViewHeight;

    public  int         mScore = 0;

    private Random      mRandom;

    private int         mTetrominoNum;
    private int         mNextTetrominoNum;
    private int         mTetrominoX = 0;
    private int         mTetrominoY = 0;

    private int         mTetrominoNull = 0;
    private int         IsFullLine = 0;  //  void checkFullLine
    private int         Timer = 0;       //  openactivity
    private int         openOnce = 0;    //  openactivity

    private Paint       mBlackPaint;
    private Paint       mGrayPaintL;
    private Paint       mGrayPaintD;
    private Paint       mRedPaint;
    private Paint       mYellowPaint;
    private Paint[]     mPaint;
    private Canvas      mCanvas;
    private Bitmap      mBitmap;

    private MediaPlayer mMediaPlayerMainSound;

    private GameListener mGameListener;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mRandom = new Random();

//      Paint=======================================================================================
        mBlackPaint = new Paint();
        mBlackPaint.setColor(Color.rgb(0,0,0));
        mBlackPaint.setStyle(Paint.Style.STROKE);
        mBlackPaint.setStrokeWidth(8);

        mGrayPaintL = new Paint();
        mGrayPaintL.setColor(Color.rgb(60,63,65));
        mGrayPaintL.setStyle(Paint.Style.STROKE);
        mGrayPaintL.setStrokeWidth(8);
        mGrayPaintL.setTextSize(70);

        mGrayPaintD = new Paint();
        mGrayPaintD.setColor(Color.rgb(43,43,43));
        mGrayPaintD.setStyle(Paint.Style.FILL_AND_STROKE);

        mRedPaint = new Paint();
        mRedPaint.setColor(Color.rgb(255, 0, 0));
        mRedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mRedPaint.setTextSize(150);

        mYellowPaint = new Paint();
        mYellowPaint.setColor(Color.rgb(255, 198, 109));
        mYellowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mYellowPaint.setTextSize(50);

        mPaint = new Paint[8];
        for (int i=0; i<mPaint.length; i++) {
            mPaint[i] = new Paint();
            mPaint[i].setStyle(Paint.Style.FILL_AND_STROKE);
        }
        mPaint[0].setColor(Color.rgb(255,255,255)); //흰색
        mPaint[1].setColor(Color.rgb(176,0,0));     //빨간색
        mPaint[2].setColor(Color.rgb(209,204,0));   //노란색
        mPaint[3].setColor(Color.rgb(0,176,0));     //초록색
        mPaint[4].setColor(Color.rgb(0,179,242));   //하늘색
        mPaint[5].setColor(Color.rgb(0,0,214));     //파랑색
        mPaint[6].setColor(Color.rgb(97,0,204));    //보라색
        mPaint[7].setColor(Color.rgb(204,0,160));   //핑크색
        for (int i=0; i<mPaint.length; i++) {
            mPaint[i].setStyle(Paint.Style.FILL_AND_STROKE);
        }
//      ===========================================================================================]

//      화면 너비&높이 구하기========================================================================
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        mScreenWidth   = metrics.widthPixels;
        mScreenHeight  = metrics.heightPixels;

        Log.d("hosung.kim", "mScreenWidth ==> " + mScreenWidth);
//      ===========================================================================================]

//      Bitmap======================================================================================
        mBitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
//      ===========================================================================================]

//      (10,20)의 맵 & 맵을 좌우,아래로 둘러싸고있는 가상의 1개의 층 & 위쪽의 4개의 층==================
        //to be fixed

        mMap = new Map[BLOCK_WIDTH_NUM][BLOCK_HEIGHT_NUM];

//      real map
        for (int i=1; i<BLOCK_WIDTH_NUM-1; i++) {
            for (int j=4; j<BLOCK_HEIGHT_NUM-1; j++) {
                mMap[i][j] = new Map(MAP_EMPTY, 0);
            }
        }
//      virtual map
        for (int i=4; i<BLOCK_HEIGHT_NUM; i++) {
            mMap[0][i] = new Map(MAP_FILL, 0);
        }
        for (int i=4; i<BLOCK_HEIGHT_NUM; i++) {
            mMap[BLOCK_WIDTH_NUM-1][i] = new Map(MAP_FILL, 0);
        }
        for (int i=0; i<BLOCK_WIDTH_NUM; i++) {
            for (int j=0; j<4; j++) {
                mMap[i][j] = new Map(MAP_EMPTY, 0);
            }
        }
        for (int i=1; i<BLOCK_WIDTH_NUM-1; i++) {
            mMap[i][BLOCK_HEIGHT_NUM-1] = new Map(MAP_FILL, 0);
        }
//      ===========================================================================================]

//      음악 재생====================================================================================
        mMediaPlayerMainSound = MediaPlayer.create(context, R.raw.tetris_bgm);
        mMediaPlayerMainSound.setVolume(2.0f, 2.0f);
        mMediaPlayerMainSound.start();
        mMediaPlayerMainSound.setLooping(true);
//      ===========================================================================================]

        mNextTetrominoNum = mRandom.nextInt(7);

        mHandler.sendMessageDelayed(mHandler.obtainMessage(), 360);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = this.getMeasuredHeight()-16;
        Log.d("hosung.kim", "button height ==> " + (mScreenHeight - mViewHeight));
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (isGameOver()) {
                Timer=Timer+1;
                drawBackground();
                mCanvas.drawText("Game Over", mScreenWidth*1/10, mViewHeight/3, mRedPaint);
                mCanvas.drawText(mScore + "점을 획득하셨습니다.", mScreenWidth*1/10, mViewHeight*2/5, mYellowPaint);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(), 360);
                if (Timer > 5) {
                    openGameOverActivity();
                }
            } else {
                drawBackground();
                drawMap();
                if (mTetrominoNull == 0) {
                    mTetrominoNum = mNextTetrominoNum;
                    mNextTetrominoNum = mRandom.nextInt(7);
                    mTetrominoNull = mTetrominoNull +1;
                    mScore = mScore + 1;
                }
                if (canDownTetromino()) {
                    mTetrominoY = mTetrominoY + 1;
                    drawTetromino();
                } else {
                    for (int i=0; i<4; i++) {
                        for (int j=0; j<4; j++) {
                            if (mTetromino[mTetrominoNum][j][i] == MAP_FILL) {
                                mMap[i + 4 + mTetrominoX][j + mTetrominoY].Fill = MAP_FILL;
                                mMap[i + 4 + mTetrominoX][j + mTetrominoY].PaintNum = mTetrominoNum + 1;
                            }
                        }
                    }
                    drawTetromino();
                    mTetrominoX = 0;
                    mTetrominoY = 0;
                    mTetrominoNull = 0;
                }
                drawMenu();
                drawBackgroundnet();
                checkFullLine();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(), 360);
            }
            invalidate();
        }
    };

    public void setGameListener(GameListener gl) {
        mGameListener = gl;
    }

    void drawTetromino() {
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == MAP_FILL) {
                    if (0 < i + 4 + mTetrominoX && i + 4 + mTetrominoX < 11 && 3 < j + mTetrominoY && j + mTetrominoY < 25) {
                        mCanvas.drawRect(mScreenWidth * (i + 3 + mTetrominoX) / (BLOCK_WIDTH_NUM-2), mViewHeight * (j + mTetrominoY+2) / (BLOCK_HEIGHT_NUM+1), mScreenWidth * (i + 4 + mTetrominoX) / (BLOCK_WIDTH_NUM-2), mViewHeight * (j + mTetrominoY+3) / (BLOCK_HEIGHT_NUM+1), mPaint[mTetrominoNum+1]);
                    }
                }
            }
        }
    }

    boolean canDownTetromino() {
        int canDownTetrominoNum = MAP_EMPTY;
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == 1) {
                    canDownTetrominoNum = canDownTetrominoNum + mMap[i+4+mTetrominoX][j+1+mTetrominoY].Fill;
                }
            }
        }
        return (canDownTetrominoNum == 0);
    }

    void drawBackground() {
        mCanvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mGrayPaintD);
    }

    void drawBackgroundnet() {
        for (int i=0; i<BLOCK_WIDTH_NUM-2; i++) {
            for (int j=6; j<BLOCK_HEIGHT_NUM+1; j++) {
                mCanvas.drawRect(mScreenWidth * i / (BLOCK_WIDTH_NUM-2), mViewHeight * j / (BLOCK_HEIGHT_NUM+1), mScreenWidth * (i + 1) / (BLOCK_WIDTH_NUM-2), mViewHeight * (j + 1) / (BLOCK_HEIGHT_NUM+1), mGrayPaintL);
            }
        }
    }

    void drawMap() {
        for (int i=1; i<(BLOCK_WIDTH_NUM-1); i++) {
            for (int j=4; j<BLOCK_HEIGHT_NUM-1; j++) {
                if (mMap[i][j].Fill == MAP_FILL) {
                    mCanvas.drawRect(mScreenWidth * (i - 1) / (BLOCK_WIDTH_NUM-2), mViewHeight * (j+2) / (BLOCK_HEIGHT_NUM+1), mScreenWidth * i / (BLOCK_WIDTH_NUM-2), mViewHeight * (j+3) / (BLOCK_HEIGHT_NUM+1), mPaint[mMap[i][j].PaintNum]);
                }
            }
        }
    }

    void drawMenu() {
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (mTetromino[mNextTetrominoNum][i][j] == 1) {
                    mCanvas.drawText("Next Shape", mScreenWidth / 50, mScreenHeight/15, mYellowPaint);
                    mCanvas.drawRect(mScreenWidth * (i + 1) / 2 /(BLOCK_WIDTH_NUM - 2), mViewHeight * (j + 3) / 2 /(BLOCK_HEIGHT_NUM + 1), mScreenWidth * (i + 2) / 2 / (BLOCK_WIDTH_NUM - 2), mViewHeight * (j + 4) / 2 / (BLOCK_HEIGHT_NUM + 1), mPaint[mNextTetrominoNum + 1]);
                    mCanvas.drawRect(mScreenWidth * (i + 1) / 2 /(BLOCK_WIDTH_NUM - 2), mViewHeight * (j + 3) / 2 /(BLOCK_HEIGHT_NUM + 1), mScreenWidth * (i + 2) / 2 / (BLOCK_WIDTH_NUM - 2), mViewHeight * (j + 4) / 2 / (BLOCK_HEIGHT_NUM + 1), mGrayPaintL);
                    mCanvas.drawText("Score : " + mScore, mScreenWidth*7/10, mScreenHeight/30, mYellowPaint);
                }
            }
        }
    }

    boolean isGameOver() {
        int mGameOver = 0;
        for (int i=1; i<11; i++) {
            mGameOver = mGameOver + mMap[i][3].Fill;
        }
        if (mGameOver == 0) {
            return false;
        } else {
            return true;
        }
    }

    void checkFullLine() {
        for (int j=BLOCK_HEIGHT_NUM-2; j>3; j--) {
            IsFullLine = 0;
            for (int i=1; i<BLOCK_WIDTH_NUM-1; i++) {
                IsFullLine = IsFullLine + mMap[i][j].Fill;
            }
            if (IsFullLine == 10) {
                mScore = mScore + 10;
                for (int k=BLOCK_HEIGHT_NUM-2; k>3; k--) {
                    if (k <= j) {
                        for (int l=1; l<BLOCK_WIDTH_NUM-1; l++) {
                            mMap[l][k].Fill      = mMap[l][k-1].Fill;
                            mMap[l][k].PaintNum  = mMap[l][k-1].PaintNum;
                        }
                    }
                }
            }
        }
    }

    public void moveLeft() {
        if (isGameOver()) {
            return;
        }
        int ShapeFillorNot = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == 1) {
                    ShapeFillorNot = ShapeFillorNot + mMap[i + 4 + mTetrominoX - 1][j + mTetrominoY].Fill;
                }
            }
        }
        if (ShapeFillorNot == 0) {
            drawBackground();
            drawMap();
            drawMenu();
            mTetrominoX = mTetrominoX - 1;
            drawTetromino();
            drawBackgroundnet();
            invalidate();
        }
    }

    public void moveRight() {
        if (isGameOver()) {
            return;
        }
        int ShapeFillorNot = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == 1) {
                    ShapeFillorNot = ShapeFillorNot + mMap[i + 4 + mTetrominoX + 1][j + mTetrominoY].Fill;
                }
            }
        }
        if (ShapeFillorNot == 0) {
            drawBackground();
            drawMap();
            drawMenu();
            mTetrominoX = mTetrominoX + 1;
            drawTetromino();
            drawBackgroundnet();
            invalidate();
        }
    }

    public void moveDown() {
        if (isGameOver()) {
            return;
        }
        int ShapeFillorNot = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == 1) {
                    ShapeFillorNot = ShapeFillorNot + mMap[i + 4 + mTetrominoX][j + mTetrominoY + 1].Fill;
                }
            }
        }
        if (ShapeFillorNot == 0) {
            drawBackground();
            drawMap();
            drawMenu();
            mTetrominoY = mTetrominoY + 1;
            drawTetromino();
            drawBackgroundnet();
            invalidate();
        }

    }

    public void rotateClockWise() {
        if (isGameOver()) {
            return;
        }
        int[][] Temp = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        int ShapeFillorNot = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Temp[i][j] = mTetromino[mTetrominoNum][j][i];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == 1) {
                    if (0 <= 7 - j + mTetrominoX && 7 - j + mTetrominoX < 12 && 0 <= i + mTetrominoY && i + mTetrominoY < 25) {
                        ShapeFillorNot = ShapeFillorNot + mMap[7 - j + mTetrominoX][i + mTetrominoY].Fill;
                    }
                }
            }
        }
        if (ShapeFillorNot == 0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    mTetromino[mTetrominoNum][i][3 - j] = Temp[i][j];
                }
            }
            drawBackground();
            drawMap();
            drawMenu();
            drawTetromino();
            drawBackgroundnet();
            invalidate();
        }
    }
    
    public void rotateCounterClockWise() {
        if (isGameOver()) {
            return;
        }
        int[][] Temp = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        int ShapeFillorNot = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Temp[i][j] = mTetromino[mTetrominoNum][j][i];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTetromino[mTetrominoNum][j][i] == 1) {
                    if (0 <= j + 4 + mTetrominoX && j + 4 + mTetrominoX < 12 && 0 <= 3 - i + mTetrominoY && 3 - i + mTetrominoY < 25) {
                        ShapeFillorNot = ShapeFillorNot + mMap[j + 4 + mTetrominoX][3 - i + mTetrominoY].Fill;
                    }
                }
            }
        }
        if (ShapeFillorNot == 0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    mTetromino[mTetrominoNum][3 - i][j] = Temp[i][j];
                }
            }
            drawBackground();
            drawMap();
            drawMenu();
            drawTetromino();
            drawBackgroundnet();
            invalidate();
        }

    }

    public void destroy() {
        if (mMediaPlayerMainSound != null) {
            mMediaPlayerMainSound.release();
            mMediaPlayerMainSound = null;
        }
        if (mHandler != null) {
            mHandler.removeMessages(0);
        }
    }

    void openGameOverActivity() {
        if (openOnce == 0) {
            SingleTonManager.getInstance().Score = mScore;
            mGameListener.openActivity();
        }
        openOnce = openOnce + 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);

        /*canvas.drawRect(0, 0, mScreenWidth, mScreenHeight, mGrayPaintD);
        for (int i=0; i<10; i++) {
            for (int j = 0; j < 20; j++) {
                canvas.drawRect(mScreenWidth * i / 10, mScreenHeight * j / 20, mScreenWidth * (i + 1) / 10, mScreenHeight * (j + 1) / 20, mGrayPaintL);
            }
        }*/
    }

}

// to be fixed -> draw functions & Score part
//                start & stop
