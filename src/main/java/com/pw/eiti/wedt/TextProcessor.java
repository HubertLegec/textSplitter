package com.pw.eiti.wedt;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

class TextProcessor {
    private static final Logger logger = Logger.getLogger(TextProcessor.class.getName());
    private String inputContent;
    private File outputFile;

    TextProcessor(String inputContent, File outputFile) {
        this.inputContent = inputContent;
        this.outputFile = outputFile;
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
        // TODO
        return Arrays.asList("Conent of paragraph 1", "Content of paragraph 2");
    }
}
