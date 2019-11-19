package com.softsquared.damoyoung.src.bookmark;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class BookmarkListItem {

    @SerializedName("no")
    private int bookmarkNo;
    @SerializedName("title")
    private String title;

    private Boolean isFirst;
    private Boolean isEditMode;

    public BookmarkListItem(int bookmarkNo,String title) {
        this.bookmarkNo = bookmarkNo;
        this.title = title;
        isFirst=false;
        isEditMode=false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public void setEditMode(Boolean editMode) {
        isEditMode = editMode;
    }

    public Boolean getEditMode() {
        return isEditMode;
    }

    public int getBookmarkNo() {
        return bookmarkNo;
    }
}
