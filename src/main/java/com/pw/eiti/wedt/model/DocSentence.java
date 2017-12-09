package com.pw.eiti.wedt.model;

import edu.stanford.nlp.simple.Sentence;
import org.apache.commons.lang3.StringUtils;

public class DocSentence {
    private int id;
    private String text;
    private int startRow;
    private int endRow;
    private SentencePredecessor predecessor;

    public DocSentence() {
    }

    public DocSentence(int id, Sentence sentence) {
        this.id = id;
        this.text = sentence.text();
        this.startRow = 0;
        this.endRow = calculateEndRow(startRow, text);
        this.predecessor = new SentencePredecessor(sentence.before(0));
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public SentencePredecessor getPredecessor() {
        return predecessor;
    }

    private static int calculateEndRow(int startRow, String text) {
        return startRow + StringUtils.countMatches(text, "\n");
    }
}
