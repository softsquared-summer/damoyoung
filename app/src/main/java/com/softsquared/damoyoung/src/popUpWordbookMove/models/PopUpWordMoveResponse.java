package com.softsquared.damoyoung.src.popUpWordbookMove.models;

import com.google.gson.annotations.SerializedName;
import com.softsquared.damoyoung.src.popUpWordbookMove.PopupWordMoveListViewItem;

import java.util.ArrayList;

public class PopUpWordMoveResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("result")
    private ArrayList<PopupWordMoveListViewItem> bookmarkListItems;

    public ArrayList<PopupWordMoveListViewItem> getBookmarkListItems() {
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