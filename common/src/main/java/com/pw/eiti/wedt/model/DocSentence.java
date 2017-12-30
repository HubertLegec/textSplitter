package com.pw.eiti.wedt.model;

import edu.stanford.nlp.simple.Sentence;
import org.apache.commons.lang3.StringUtils;

public class DocSentence {
    private int id;
    private String text;
    private int startRow;
    private int endRow;
    private String predecessor;
    private Document document;

    public DocSentence() {
    }

    public DocSentence(Sentence sentence, Document document, int startRow) {
        this.id = sentence.sentenceIndex();
        this.text = sentence.text();
        this.startRow = startRow;
        this.endRow = calculateEndRow(startRow, text);
        this.predecessor = sentence.before(0);
        this.document = document;
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

    public String getPredecessor() {
        return predecessor;
    }

    public Document getDocument() {
        return document;
    }

    private static int calculateEndRow(int startRow, String text) {
        return startRow + StringUtils.countMatches(text, "\n");
    }
}
