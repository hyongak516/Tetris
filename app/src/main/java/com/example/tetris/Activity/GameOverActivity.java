package com.example.tetris.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tetris.R;
import com.example.tetris.common.SingleTonManager;

public class GameOverActivity extends AppCompatActivity {
    private     EditText        mEditText;
    private     Button          mButton;
    private     int             mNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);



        mEditText   = findViewById(R.id.editTextTextPersonName);
        mButton     = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence cs = mEditText.getText();
                SingleTonManager.getInstance().mName = cs.toString();

                mNum = 0;
                for (int i=0; i<5; i++) {
                    if (mNum == 0) {
                        if (SingleTonManager.getInstance().mScore >= SingleTonManager.getInstance().Score[i]) {
                        /*for (int j=0; j<4-i; j++) {
                            SingleTonManager.getInstance().Score[i+j+1] = SingleTonManager.getInstance().Score[i+j];

                            SingleTonManager.getInstance().Name[i+j+1] = SingleTonManager.getInstance().Name[i+j];
                        }
                        SingleTonManager.getInstance().Score[i]=SingleTonManager.getInstance().mScore;
                        SingleTonManager.getInstance().Name[i]=SingleTonManager.getInstance().mName;*/
                            mNum = mNum + 1;
                            for (int j = 5 - i; j > 0; j--) {
                                SingleTonManager.getInstance().Score[i + j] = SingleTonManager.getInstance().Score[i + j - 1];

                                SingleTonManager.getInstance().Name[i + j] = SingleTonManager.getInstance().Name[i + j - 1];
                            }
                            SingleTonManager.getInstance().Score[i] = SingleTonManager.getInstance().mScore;
                            SingleTonManager.getInstance().Name[i] = SingleTonManager.getInstance().mName;

                        }
                    }
                }
                SingleTonManager.getInstance().mRanking = "1. " + SingleTonManager.getInstance().Name[0] + "  -->  " + SingleTonManager.getInstance().Score[0]/25*9 + "초\n"
                        + "2. " + SingleTonManager.getInstance().Name[1] + "  -->  " + SingleTonManager.getInstance().Score[1]/25*9 + "초\n"
                        + "3. " + SingleTonManager.getInstance().Name[2] + "  -->  " + SingleTonManager.getInstance().Score[2]/25*9 + "초\n"
                        + "4. " + SingleTonManager.getInstance().Name[3] + "  -->  " + SingleTonManager.getInstance().Score[3]/25*9 + "초\n"
                        + "5. " + SingleTonManager.getInstance().Name[4] + "  -->  " + SingleTonManager.getInstance().Score[4]/25*9 + "초\n";
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = pref.edit();


                editor.putString("Ranking", SingleTonManager.getInstance().mRanking);
                editor.commit();

                finish();
            }
        });


    }
}