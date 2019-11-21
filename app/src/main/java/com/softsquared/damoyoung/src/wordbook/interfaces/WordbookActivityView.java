package com.softsquared.damoyoung.src.wordbook.interfaces;

import com.softsquared.damoyoung.src.wordbook.WordbookExListItem;

import java.util.ArrayList;

public interface WordbookActivityView {
    void validateGetSuccess(String text, ArrayList<WordbookExListItem> data);
    void validateGetFailure(String message);
    void validateMoveSuccess(String text);
    void validateMoveFailure(String message);
    void validateCopySuccess(String text);
    void validateCopyFailure(String message);
    void vaildateMemorizedSuccess(String text);
    void vaildateMemorizedFailure(String text);
    void vaildateSentenceDeleteSuccess(String text);
    void vaildateSentenceDeleteFailure(String text);
    void validateDeleteSuccess(String text);
    void validateDeleteFailure(String message);


}
