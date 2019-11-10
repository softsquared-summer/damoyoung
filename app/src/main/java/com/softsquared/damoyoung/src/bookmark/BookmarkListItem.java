package com.softsquared.damoyoung.src.bookmark;

import android.widget.ImageView;

public class BookmarkListItem {

    private String title;
    private Boolean isFirst;

    public BookmarkListItem(String title) {
        this.title = title;
        isFirst=false;
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
}
