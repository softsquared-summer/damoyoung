package com.softsquared.damoyoung.src.wordbook.models;

public class Word {

    public Word(int wordNo, String word, boolean edit) {
        this.wordNo = wordNo;
        this.word = word;
        this.isEditMode = edit;
        this.isChecked = false;
        this.isMemorized = false;
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
    private boolean isMemorized;

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

    public boolean isMemorized() {
        return isMemorized;
    }

    public void setMemorized(boolean memorized) {
        isMemorized = memorized;
    }
}
