package com.softsquared.damoyoung.src.wordbook.models;

import com.google.gson.annotations.SerializedName;
import com.softsquared.damoyoung.src.wordbook.WordbookExListItem;

import java.util.ArrayList;

public class WordbookResponse {

    @SerializedName("result")
    private ArrayList<WordbookExListItem> wordBookExListItems;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;



    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public ArrayList<WordbookExListItem> getWordBookExListItems() {
        return wordBookExListItems;
    }
}
