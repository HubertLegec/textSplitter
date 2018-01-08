package com.pw.eiti.wedt;

import java.util.ArrayList;
import java.util.List;

public class DocumentDescription {
    private int sentencesCount;
    private int paragraphsCount;
    private List<Integer> paragraphsStarts = new ArrayList<>();

    public int getSentencesCount() {
        return sentencesCount;
    }

    public void setSentencesCount(int sentencesCount) {
        this.sentencesCount = sentencesCount;
    }

    public int getParagraphsCount() {
        return paragraphsCount;
    }

    public void setParagraphsCount(int paragraphsCount) {
        this.paragraphsCount = paragraphsCount;
    }

    public List<Integer> getParagraphsStarts() {
        return paragraphsStarts;
    }

    public void setParagraphsStarts(List<Integer> paragraphsStarts) {
        this.paragraphsStarts = paragraphsStarts;
    }
}
