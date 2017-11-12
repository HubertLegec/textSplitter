package com.pw.eiti.wedt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Collection;
import java.util.Optional;

class XmlGenerator {
    private Document doc;


    void generateXml(Collection<String> sections) {
        try {
            createDocument();

            // root elements
            Element rootElement = doc.createElement("document");
            doc.appendChild(rootElement);
            sections.stream()
                    .map(this::createParagraph)
                    .forEach(rootElement::appendChild);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        this.doc = docBuilder.newDocument();
    }

    private Element createParagraph(String text) {
        Element elem = doc.createElement("paragraph");
        elem.appendChild(doc.createTextNode(text));
        return elem;
    }

    Optional<Document> getDocument() {
        return Optional.of(doc);
    }

    void saveDocumentToFile(File outputFile) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(outputFile);

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
    }
}
