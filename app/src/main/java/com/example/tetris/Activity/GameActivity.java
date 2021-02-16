package com.example.tetris.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tetris.R;
import com.example.tetris.View.GameView;

public class GameActivity extends AppCompatActivity {

    private Button mLeftBtn;
    private Button mRotateRBtn;
    private Button mRotateBtn;
    private Button mRightBtn;

    private GameView mGameView;

    public int getScore;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGameView.destroy();
    }

}