package com.softsquared.damoyoung.src.wordbook.itemClass;

public class Word {

    public Word(int wordNo, String word,boolean edit) {
        this.wordNo = wordNo;
        this.word = word;
        this.isEditMode =edit;
        this.isChecked = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    private int wordNo;
    private String word;
    private boolean isEditMode;
    private boolean isChecked;

    public boolean isSelected() {
        return isChecked;
    }

    public void setChecked(boolean selected) {
        isChecked = selected;
    }

    public int getWordNo() {
        return wordNo;
    }

    public void setWordNo(int wordNo) {
        this.wordNo = wordNo;
    }
}
