package com.pw.eiti.wedt.model;

public class Sentence {
    private String text;
    private int startRow;
    private int endRow;
    private SentencePredecessor predecessor;

    public String getText() {
        return text;
    }
}
