package com.example.tetris.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.tetris.R;
import com.example.tetris.View.GameView;
import com.example.tetris.View.GameListener;

public class GameActivity extends AppCompatActivity implements GameListener {

    private Button mLeftBtn;
    private Button mRotateRBtn;
    private Button mRotateBtn;
    private Button mRightBtn;

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mLeftBtn      = findViewById(R.id.game_button1);
        mRotateRBtn   = findViewById(R.id.game_button2);
        mRotateBtn    = findViewById(R.id.game_button3);
        mRightBtn     = findViewById(R.id.game_button4);

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGameView.destroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    public void openActivity() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }
}