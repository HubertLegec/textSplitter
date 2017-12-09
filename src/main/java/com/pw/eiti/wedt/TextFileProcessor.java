package com.pw.eiti.wedt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

class TextFileProcessor {
    private static final Logger logger = Logger.getLogger(TextFileProcessor.class.getName());
    private File inputFile;
    private File outputFile;

    TextFileProcessor(File inputFile, File outputFile) {
        this.inputFile = inputFile;
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

    private Collection<String> splitDocumentIntoParagraphs() throws IOException {
        logger.info("Split document into paragraphs");
        List<String> lines = Files.readAllLines(inputFile.toPath());

        // TODO
        return Arrays.asList("Conent of paragraph 1", "Content of paragraph 2");
    }
}
