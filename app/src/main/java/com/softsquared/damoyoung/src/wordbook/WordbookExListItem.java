package com.softsquared.damoyoung.src.wordbook;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WordbookExListItem {



    @SerializedName("word_no")
    private int wordNo;
    @SerializedName("word")
    private String word;

    @SerializedName("sentenceList")
    private ArrayList<Sentence> sentenceList;

    public ArrayList<Sentence> getSentenceList() {
        return sentenceList;
    }

    public WordbookExListItem(ArrayList<Sentence> sentenceList){
        this.sentenceList = sentenceList;
    }


    public int getWordNo() {
        return wordNo;
    }

    public String getWord() {
        return word;
    }


    public class Sentence{

        private boolean isSelected;

        @SerializedName("sentence_no")
        private int sentenceNo;

        @SerializedName("sentence")
        private String sentence;

        public boolean isSelected() {
            return isSelected;
        }

        public String getSetense() {
            return sentence;
        }

        public int getSentenceNo() {
            return sentenceNo;
        }
    }


}
