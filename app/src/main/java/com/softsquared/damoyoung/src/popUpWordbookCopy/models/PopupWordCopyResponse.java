package com.softsquared.damoyoung.src.popUpWordbookCopy.models;

import com.google.gson.annotations.SerializedName;
import com.softsquared.damoyoung.src.popUpWordbookCopy.PopupWordCopyListViewItem;
import com.softsquared.damoyoung.src.popUpWordbookMove.PopupWordMoveListViewItem;

import java.util.ArrayList;

public class PopupWordCopyResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("result")
    private ArrayList<PopupWordCopyListViewItem> bookmarkListItems;

    public ArrayList<PopupWordCopyListViewItem> getBookmarkListItems() {
        return bookmarkListItems;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }
}