package com.softsquared.damoyoung.src.quickBookmark;

import android.widget.CheckBox;

public class PopupBookmarkListViewItem {

    private String keyword;
    private CheckBox chkFolder;
    private boolean isNew;

    public void setNew(boolean aNew) {
        isNew = aNew;
    }


    public PopupBookmarkListViewItem(String keyword, Boolean isNew) {
        this.keyword = keyword;
        this.isNew = isNew;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public CheckBox getChkFolder() {
        return chkFolder;
    }

    public boolean isNew() {
        return isNew;
    }
}
