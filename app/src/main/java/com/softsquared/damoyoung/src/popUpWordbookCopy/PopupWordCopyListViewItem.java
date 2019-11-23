package com.softsquared.damoyoung.src.popUpWordbookCopy;

import com.google.gson.annotations.SerializedName;

public class PopupWordCopyListViewItem {

    @SerializedName("no")
    private int bookmarkNo;
    @SerializedName("title")
    private String keyword;
    private Boolean isSelected;


    public PopupWordCopyListViewItem(int bookmarkNo, String keyword) {
        this.bookmarkNo = bookmarkNo;
        this.keyword = keyword;
        isSelected = false;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }


    public Boolean isSelected() {
        return isSelected;
    }

    public void setChecked(Boolean checked) {
        isSelected = checked;
    }

    public int getBookmarkNo() {
        return bookmarkNo;
    }
}
