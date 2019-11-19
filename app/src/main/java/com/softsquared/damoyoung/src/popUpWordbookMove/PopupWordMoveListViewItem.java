package com.softsquared.damoyoung.src.popUpWordbookMove;

import com.google.gson.annotations.SerializedName;

public class PopupWordMoveListViewItem {

    @SerializedName("no")
    private int bookmarkNo;
    @SerializedName("title")
    private String keyword;

    private Boolean isSelected;



    public PopupWordMoveListViewItem(int bookmarkNo, String keyword) {
        this.bookmarkNo = bookmarkNo;
        this.keyword = keyword;
        isSelected=false;
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
