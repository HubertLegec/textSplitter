package com.pw.eiti.wedt;

import org.w3c.dom.Document;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

public class TextProcessor {
    private File inputFile;
    private File outputFile;

    public TextProcessor(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Splits text from given text file into paragraphs and saves it as XML file
     *
     * @return created XML
     */
    public Optional<Document> process() throws Exception {
        XmlGenerator xmlGenerator = new XmlGenerator();
        xmlGenerator.generateXml(Collections.emptyList());
        xmlGenerator.saveDocumentToFile(outputFile);
        return xmlGenerator.getDocument();
    }
}
