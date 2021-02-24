package com.example.tetris.common;

public class SingleTonManager {

    private static SingleTonManager INSTANCE = null;

    public int mScore;
    public String mName="";
    public int[] Score = {0, 0, 0, 0, 0, 0};
    public String[] Name = {"Not Saved", "Not Saved", "Not Saved", "Not Saved", "Not Saved", "Not Saved"};
    public String mRanking="기록이 없습니다.";
    // other instance variables can be here

    private SingleTonManager() {
        this.mScore = 0;
    };

    public static SingleTonManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingleTonManager();
        }
        return(INSTANCE);
    }

    // other instance methods can follow

}
