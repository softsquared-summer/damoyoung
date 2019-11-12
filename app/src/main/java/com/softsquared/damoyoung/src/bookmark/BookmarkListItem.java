package com.softsquared.damoyoung.src.bookmark;

import android.widget.ImageView;

public class BookmarkListItem {

    private String title;
    private Boolean isFirst;
    private Boolean isEditMode;

    public BookmarkListItem(String title) {
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
}
