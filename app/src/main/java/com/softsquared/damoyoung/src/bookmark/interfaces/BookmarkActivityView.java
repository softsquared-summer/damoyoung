package com.softsquared.damoyoung.src.bookmark.interfaces;

import com.softsquared.damoyoung.src.bookmark.BookmarkListItem;

import java.util.ArrayList;

public interface BookmarkActivityView {

    void validateMakeSuccess(String text);
    void validateMakeFailure(String message);
    void validateDeleteSuccess(String text);
    void validateDeleteFailure(String message);
    void validateGetSuccess(String text, ArrayList<BookmarkListItem> bookmarkListItems);
    void validateGetFailure(String message);
}
