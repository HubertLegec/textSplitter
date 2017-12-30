package com.pw.eiti.wedt.model;

import com.pw.eiti.wedt.utils.FileUtils;
import edu.stanford.nlp.simple.Sentence;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Document {
    private List<DocSentence> sentences;
    private List<String> lines;

    public Document() {}

    public Document(final Path inputFile) {
        this.lines = FileUtils.readFileAsLines(inputFile)
                .orElseThrow(() -> new RuntimeException("Can't read file: " + inputFile.getFileName()));
        String fileContent = FileUtils.readFileAsString(inputFile)
                .orElseThrow(() -> new RuntimeException("Can't read file: " + inputFile.getFileName()));
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

    List<DocSentence> generateSentencesFromString(String fileContent) {
        edu.stanford.nlp.simple.Document doc = new edu.stanford.nlp.simple.Document(fileContent);
        List<Sentence>  sentences = doc.sentences();
        return sentences.stream()
                .map(s -> mapSentenceToDocSentence(s, fileContent))
                .collect(toList());
    }

    static int calculateRowFromSentenceOffset(int offset, String documentContent) {
        String subString = StringUtils.substring(documentContent, 0, offset);
        return StringUtils.countMatches(subString, "\n");
    }

    private DocSentence mapSentenceToDocSentence(Sentence sentence, String fileContent) {
        return new DocSentence(
                sentence,
                this,
                calculateRowFromSentenceOffset(sentence.characterOffsetBegin(0), fileContent)
        );
    }
}
