package com.softsquared.damoyoung.src.bookmark.models;

import com.google.gson.annotations.SerializedName;
import com.softsquared.damoyoung.src.bookmark.BookmarkListItem;

import java.util.ArrayList;

public class BookmarkGetResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("result")
    private ArrayList<BookmarkListItem> bookmarkListItems;

    public ArrayList<BookmarkListItem> getBookmarkListItems() {
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