package com.pw.eiti.wedt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

class XmlGenerator {
    private static final Logger logger = Logger.getLogger(XmlGenerator.class.getName());
    private Document doc;
    private TransformerFactory transformerFactory = TransformerFactory.newInstance();


    void generateXml(Collection<String> sections) {
        logger.info("Generate xml");
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
        logger.info("Create document");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        this.doc = docBuilder.newDocument();
    }

    private Element createParagraph(String text) {
        Element elem = doc.createElement("p");
        elem.appendChild(doc.createTextNode(text));
        return elem;
    }

    Optional<Document> getDocument() {
        return Optional.of(doc);
    }

    void saveDocumentToFile(File outputFile) throws Exception {
        logger.info("Save document to file: " + outputFile.getName());
        Transformer transformer = getXmlTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(outputFile);

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
    }

    Optional<String> getDocumentAsString() throws TransformerException {
        logger.info("Get document as string");
        DOMSource source = new DOMSource(doc);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(outputStream);
        Transformer transformer = getXmlTransformer();
        transformer.transform(source, result);
        return Optional.of(outputStream)
                .filter(out -> out.size() > 0)
                .map(ByteArrayOutputStream::toByteArray)
                .map(String::new);
    }

    private Transformer getXmlTransformer() throws TransformerConfigurationException {
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return transformer;
    }
}
