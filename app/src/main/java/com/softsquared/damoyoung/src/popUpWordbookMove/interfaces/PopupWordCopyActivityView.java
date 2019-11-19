package com.softsquared.damoyoung.src.popUpWordbookMove.interfaces;

import com.softsquared.damoyoung.src.popUpWordbookMove.PopupWordMoveListViewItem;

import java.util.ArrayList;

public interface PopupWordCopyActivityView {

    void validateGetSuccess(String text, ArrayList<PopupWordMoveListViewItem> bookmarkListItems);
    void validateGetFailure(String message);
    void validateMoveSuccess(String text);
    void validateMoveFailure(String message);
}
