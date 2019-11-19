package com.softsquared.damoyoung.src.popUpBookmark.models;

import com.google.gson.annotations.SerializedName;
import com.softsquared.damoyoung.src.bookmark.BookmarkListItem;
import com.softsquared.damoyoung.src.popUpBookmark.PopupBookmarkListViewItem;

import java.util.ArrayList;

public class PopUpBookmarkGetResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("result")
    private ArrayList<PopupBookmarkListViewItem> bookmarkListItems;

    public ArrayList<PopupBookmarkListViewItem> getBookmarkListItems() {
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