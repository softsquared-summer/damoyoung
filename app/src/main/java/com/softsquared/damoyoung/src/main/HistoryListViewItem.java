package com.softsquared.damoyoung.src.main;

public class HistoryListViewItem {

    private String keyword;
    private Boolean isChkShow;
    private Boolean isSelected;

    public HistoryListViewItem(String keyword) {
        this.keyword = keyword;
        this.isChkShow = false;
        this.isSelected = false;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public Boolean getChkShow() {
        return this.isChkShow;
    }

    public void setChkShow(Boolean chkShow) {
        isChkShow = chkShow;
    }

    public Boolean isSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
