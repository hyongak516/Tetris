package com.example.tetris.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tetris.R;
import com.example.tetris.common.SingleTonManager;
import com.example.tetris.common.UserScoreModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//  ================================================================================================
//  Hosung Tetris
//  ================================================================================================
public class MainActivity extends AppCompatActivity {
    private Button      mGameStartBtn;  //  게임 시작 버튼
    private Button      mRankingBtn;    //  랭킹 버튼
    private Button      mInfoBtn;       //  정보 버튼
    private Button      mGameShutBtn;   //  게임 종료 버튼

    private int         temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("hosung.kim", "VERSION ==> 2021.03.30.22.12");

        mGameStartBtn  = findViewById(R.id.main_button1);
        mRankingBtn    = findViewById(R.id.main_button2);
        mInfoBtn       = findViewById(R.id.main_button3);
        mGameShutBtn   = findViewById(R.id.main_button4);

        mGameStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              게임 시작  GameActivity로 이동=======================================================
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
//              ===================================================================================]
            }
        });

        mRankingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              랭킹화면 RankingActivity로 이동======================================================
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
//              ===================================================================================]
            }
        });

        mInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = temp + 1;
                if (temp > 9) {
//                  정보화면 RankingActivity로 이동==================================================
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(intent);
//                  ===============================================================================]
                    temp = 0;
                }
            }
        });

        mGameShutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              MainActivity 종료===================================================================
                GameShutDlg();
//              ===================================================================================]
            }
        });



    }

//  MainActivity 종료 dialog========================================================================
    void GameShutDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임 종료");
        builder.setMessage("Tetris 게임을 종료하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }
//  ===============================================================================================]
}
