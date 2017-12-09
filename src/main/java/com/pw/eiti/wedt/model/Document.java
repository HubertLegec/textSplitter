package com.pw.eiti.wedt.model;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Document {
    private List<DocSentence> sentences;
    private List<String> lines;

    public Document() {}

    public Document(List<String> lines, List<DocSentence> sentences) throws IOException {
        this.lines = lines;
        this.sentences = sentences;

    }

    public Optional<Integer> getMinSentenceId() {
        return sentences.stream()
                .map(DocSentence::getId)
                .min(Integer::compareTo);
    }

    public String getLine(int idx) {
        return lines.get(idx);
    }
}
