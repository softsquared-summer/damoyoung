package com.softsquared.damoyoung.src.popUpBookmark.interfaces;

import com.softsquared.damoyoung.src.bookmark.BookmarkListItem;
import com.softsquared.damoyoung.src.popUpBookmark.PopupBookmarkListViewItem;

import java.util.ArrayList;

public interface PopUpBookmarkActivityView {

    void validateMakeSuccess(String text);
    void validateMakeFailure(String message);
    void validateGetSuccess(String text, ArrayList<PopupBookmarkListViewItem> bookmarkListItems);
    void validateGetFailure(String message);
    void validateSaveSuccess(String text);
    void validateSaveFailure(String message);
}
