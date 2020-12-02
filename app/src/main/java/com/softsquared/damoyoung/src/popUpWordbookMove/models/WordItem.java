package com.softsquared.damoyoung.src.popUpWordbookMove.models;

import com.google.gson.annotations.SerializedName;

public class WordItem {



    @SerializedName("wordNo")
    int wordNo;

    public WordItem(int wordNo) {
        this.wordNo = wordNo;
    }

    public int getWordNo() {
        return wordNo;
    }


}
