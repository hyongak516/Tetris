package com.example.tetris.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class GameOverActivity extends AppCompatActivity {
    private static final int HANDLER_MESSAGE_TYPE_GAME_OVER = 0;

    private     EditText        mEditText;
    private     Button          mButton;
    private     GameActivity    mGameActivity;
    private     Handler         mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case HANDLER_MESSAGE_TYPE_GAME_OVER:
                    finish();
                    mGameActivity.finish();
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);



        mEditText   = findViewById(R.id.editTextTextPersonName);
        mButton     = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> stringObjectMap = new HashMap<>();
                CharSequence cs = mEditText.getText();
                stringObjectMap.put("userName", cs.toString());
                stringObjectMap.put("score", SingleTonManager.getInstance().Score);

                Log.d("hosung.kim", "username : " + cs.toString() + "score : " + SingleTonManager.getInstance().Score);

                Date date = new Date();
                String strDate = String.format("%d%02d%02d", date.getYear() + 1900, date.getMonth() + 1, date.getDate());

                FirebaseDatabase.getInstance().getReference().child("records").child(strDate).child("2").setValue(stringObjectMap);

                FirebaseDatabase.getInstance().getReference().child("records").child(strDate).child("2").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        UserScoreModel userScoreModel = dataSnapshot.getValue(UserScoreModel.class);
                        if(userScoreModel !=  null) {
                            int highScore = userScoreModel.score;
                            Toast.makeText(getBaseContext(), "OK" + highScore, Toast.LENGTH_LONG).show();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mHandler.sendMessage(mHandler.obtainMessage(HANDLER_MESSAGE_TYPE_GAME_OVER));
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {
                        Toast.makeText(getBaseContext(), "저장 실패", Toast.LENGTH_LONG).show();
                        mHandler.sendMessage(mHandler.obtainMessage(HANDLER_MESSAGE_TYPE_GAME_OVER));
                    }
                });
                /*CharSequence cs = mEditText.getText();
                SingleTonManager.getInstance().mName = cs.toString();

                mNum = 0;
                for (int i=0; i<5; i++) {
                    if (mNum == 0) {
                        if (SingleTonManager.getInstance().mScore >= SingleTonManager.getInstance().Score[i]) {*/
                        /*for (int j=0; j<4-i; j++) {
                            SingleTonManager.getInstance().Score[i+j+1] = SingleTonManager.getInstance().Score[i+j];

                            SingleTonManager.getInstance().Name[i+j+1] = SingleTonManager.getInstance().Name[i+j];
                        }
                        SingleTonManager.getInstance().Score[i]=SingleTonManager.getInstance().mScore;
                        SingleTonManager.getInstance().Name[i]=SingleTonManager.getInstance().mName;*/
                            /*mNum = mNum + 1;
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
                editor.commit();*/

            }
        });


    }
}