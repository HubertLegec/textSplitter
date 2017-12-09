package com.pw.eiti.wedt.model;

import org.apache.commons.lang3.StringUtils;

public class SentencePredecessor {
    private String content;

    public SentencePredecessor() {
    }

    public SentencePredecessor(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean containsEmptyRow() {
        return StringUtils.contains(content, "\n\n");
    }
}
