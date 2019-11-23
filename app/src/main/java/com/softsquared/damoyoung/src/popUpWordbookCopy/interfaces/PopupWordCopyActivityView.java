package com.softsquared.damoyoung.src.popUpWordbookCopy.interfaces;

import com.softsquared.damoyoung.src.popUpWordbookCopy.PopupWordCopyListViewItem;

import java.util.ArrayList;

public interface PopupWordCopyActivityView {

    void validateGetSuccess(String text, ArrayList<PopupWordCopyListViewItem> bookmarkListItems);

    void validateGetFailure(String message);

    void validateCopySuccess(String text);

    void validateCopyFailure(String message);
}
