package com.pw.eiti.wedt;

import com.pw.eiti.wedt.detector.ParagraphDetector;
import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;
import edu.stanford.nlp.util.Pair;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

class TextFileProcessor {
    private static final Logger logger = Logger.getLogger(TextFileProcessor.class.getName());
    private File inputFile;
    private File outputFile;
    private ParagraphDetector detector;

    TextFileProcessor(File inputFile, File outputFile, ParagraphDetector detector) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.detector = detector;
    }

    /**
     * Splits text from given text file into paragraphs and saves it as XML file
     *
     * @return created XML
     */
    Optional<String> process() throws Exception {
        Collection<String> paragraphs = splitDocumentIntoParagraphs();
        XmlGenerator xmlGenerator = new XmlGenerator();
        xmlGenerator.generateXml(paragraphs);
        xmlGenerator.saveDocumentToFile(outputFile);
        return xmlGenerator.getDocumentAsString();
    }

    private Collection<String> splitDocumentIntoParagraphs() {
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
