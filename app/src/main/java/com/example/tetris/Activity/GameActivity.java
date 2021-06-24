package com.example.tetris.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tetris.R;
import com.example.tetris.View.GameView;
import com.example.tetris.View.GameListener;
import com.example.tetris.common.SingleTonManager;

public class GameActivity extends AppCompatActivity implements GameListener {

    private Button mLeftBtn;
    private Button mRotateRBtn;
    private Button mDownBtn;
    private Button mRotateBtn;
    private Button mRightBtn;

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mLeftBtn      = findViewById(R.id.game_button1);
        mRotateRBtn   = findViewById(R.id.game_button2);
        mDownBtn      = findViewById(R.id.game_button3);
        mRotateBtn    = findViewById(R.id.game_button4);
        mRightBtn     = findViewById(R.id.game_button5);

        mGameView = findViewById(R.id.gameview);

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.moveLeft();
            }
        });

        mRotateRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.rotateCounterClockWise();
            }
        });

        mDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.moveDown();
            }
        });

        mRotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.rotateClockWise();
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.moveRight();
            }
        });

        mGameView.setGameListener(this);

        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGameView.destroy();
    }

    @Override
    public void openActivity() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
        finish();
    }
}