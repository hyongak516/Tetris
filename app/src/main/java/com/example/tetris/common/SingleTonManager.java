package com.example.tetris.common;

public class SingleTonManager {

    private static SingleTonManager INSTANCE = null;

    public int Score;

    // other instance variables can be here

    private SingleTonManager() {
        this.Score = 0;
    };

    public static SingleTonManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingleTonManager();
        }
        return(INSTANCE);
    }
    // other instance methods can follow
}