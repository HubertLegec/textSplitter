package com.pw.eiti.wedt.model;

import edu.stanford.nlp.simple.Sentence;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Document {
    private List<DocSentence> sentences;
    private List<String> lines;

    public Document() {}

    public Document(File inputFile) throws IOException {
        this.lines = Files.readAllLines(inputFile.toPath());
        String fileContent = new String(Files.readAllBytes(inputFile.toPath()));
        this.sentences = generateSentencesFromString(fileContent);

    }

    public List<DocSentence> getSentences() {
        return sentences;
    }

    public List<String> getLines() {
        return lines;
    }

    public Optional<Integer> getMinSentenceId() {
        return sentences.stream()
                .map(DocSentence::getId)
                .min(Integer::compareTo);
    }

    public String getLine(int idx) {
        return lines.get(idx);
    }

    static List<DocSentence> generateSentencesFromString(String fileContent) {
        edu.stanford.nlp.simple.Document doc = new edu.stanford.nlp.simple.Document(fileContent);
        List<Sentence>  sentences = doc.sentences();
        return sentences.stream()
                .map(s -> new DocSentence(s, calculateRowFromSentenceOffset(s.characterOffsetBegin(0), fileContent)))
                .collect(toList());
    }

    static int calculateRowFromSentenceOffset(int offset, String documentContent) {
        String subString = StringUtils.substring(documentContent, 0, offset);
        return StringUtils.countMatches(subString, "\n");
    }
}
