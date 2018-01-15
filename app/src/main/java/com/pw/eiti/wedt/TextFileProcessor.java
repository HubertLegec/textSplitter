package com.pw.eiti.wedt;

import com.pw.eiti.wedt.detector.ParagraphDetector;
import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;
import edu.stanford.nlp.util.Pair;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * This class can be used to split txt document into paragraphs.
 * It requires {@code ParagraphDetector} instance to detect paragraphs.
 */
class TextFileProcessor {
    private static final Logger logger = Logger.getLogger(TextFileProcessor.class.getName());
    private File inputFile;
    private ParagraphDetector detector;

    /**
     * Main constructor
     * @param inputFile file to split into paragraphs
     * @param detector {@code ParagraphDetector implementation}
     */
    TextFileProcessor(File inputFile, ParagraphDetector detector) {
        this.inputFile = inputFile;
        this.detector = detector;
    }

    /**
     * Splits file passed in constructor into paragraphs
     * @return list of paragraphs found in file
     */
    List<String> splitDocumentIntoParagraphs() {
        logger.info("Split document into paragraphs");
        Document document = new Document(inputFile.toPath());
        AtomicInteger paragraphIdx = new AtomicInteger(-1);
        Map<Integer, List<DocSentence>> parasGroups = document.getSentences().stream()
                .map(s -> new Pair<>(detector.startsNewParagraph(s) ? paragraphIdx.incrementAndGet() : paragraphIdx.get(), s))
                .collect(groupingBy(Pair::first, Collectors.mapping(Pair::second, toList())));
        return parasGroups.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(this::sentencesToParagraph)
                .collect(toList());
    }

    private String sentencesToParagraph(List<DocSentence> sentences) {
        return sentences.stream()
                .map(s -> s.getPredecessor() + s.getText())
                .collect(joining());
    }
}
