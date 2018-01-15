package com.pw.eiti.wedt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Collection;
import java.util.Optional;

/**
 * Utils class. Generates XML document from given list of paragraphs.
 */
class XmlGenerator {
    private static final Logger log = LoggerFactory.getLogger(XmlGenerator.class);

    /**
     * Generates XML document from given list of paragraphs.
     * @param sections list of paragraphs, where each paragraph is represented by its content
     * @return XML document
     */
    Document generateXml(Collection<String> sections) {
        log.info("Generate xml");
        Document doc = createDocument()
                .orElseThrow(() -> new RuntimeException("Can't create new XML document"));
        Element rootElement = doc.createElement("document");
        doc.appendChild(rootElement);
        sections.stream()
                .map(String::trim)
                .map(s -> createParagraph(s, doc))
                .forEach(rootElement::appendChild);
        return doc;
    }

    /**
     * Saves XML document to file.
     * @param doc document to save
     * @param outputFile file where document should be saved
     * @throws TransformerException if can't transform document representation to file
     */
    static void saveDocumentToFile(Document doc, File outputFile) throws TransformerException {
        log.info("Save document to file: " + outputFile.getName());
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(outputFile);
        Transformer transformer = getXmlTransformer()
                .orElseThrow(() -> new RuntimeException("Can't create XMLTransformer"));
        transformer.transform(source, result);
    }

    private Optional<Document> createDocument() {
        log.info("Create document");
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return Optional.of(docBuilder.newDocument());
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private Element createParagraph(String text, Document doc) {
        Element elem = doc.createElement("p");
        elem.appendChild(doc.createTextNode(text));
        return elem;
    }

    private static Optional<Transformer> getXmlTransformer() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            return Optional.of(transformer);
        } catch (TransformerConfigurationException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
