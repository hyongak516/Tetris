package com.example.tetris.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tetris.R;
import com.example.tetris.common.SingleTonManager;

public class RankingActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button   mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        /*SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SingleTonManager.getInstance().mRanking = pref.getString("Ranking", "기록이 없습니다.");
        mTextView = findViewById(R.id.textView);
        mTextView.setText(SingleTonManager.getInstance().mRanking);*/

        mButton = findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}