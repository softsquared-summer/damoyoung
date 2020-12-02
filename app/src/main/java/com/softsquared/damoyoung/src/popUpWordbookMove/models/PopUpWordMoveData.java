package com.softsquared.damoyoung.src.popUpWordbookMove.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PopUpWordMoveData {

    @SerializedName("newbookmarkNo")
    int newbookmarkNo;

    @SerializedName("bookmarkNo")
    int bookmarkNo;

    @SerializedName("wordList")
    ArrayList<WordItem> wordItems;

    public PopUpWordMoveData(int newbookmarkNo, int bookmarkNo, ArrayList<WordItem> wordItems) {
        this.newbookmarkNo = newbookmarkNo;
        this.bookmarkNo = bookmarkNo;
        this.wordItems = wordItems;
    }

    public int getNewbookmarkNo() {
        return newbookmarkNo;
    }

    public int getBookmarkNo() {
        return bookmarkNo;
    }

    public ArrayList<WordItem> getWordItems() {
        return wordItems;
    }

}
