package com.pw.eiti.wedt;

import com.pw.eiti.wedt.conditions.SentenceConditionsMapper;
import com.pw.eiti.wedt.detector.CustomParagraphDetector;
import com.pw.eiti.wedt.detector.ParagraphDetector;
import com.pw.eiti.wedt.detector.PerceptronParagraphDetector;
import com.pw.eiti.wedt.network.NetworkProvider;
import com.pw.eiti.wedt.utils.FileUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.encog.neural.networks.BasicNetwork;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.List;


public class TextSplitter extends Application {
    private Window window;
    @FXML
    private TextField inputFileTF;
    @FXML
    private Button processBT;
    @FXML
    private Button saveBT;
    @FXML
    private TextArea inputPrevTA;
    @FXML
    private TreeView<String> outputPrevTV;
    @FXML
    private Label statusL;
    @FXML
    private ToggleButton modeBT;

    private File inputFile;
    private File outputFile;
    private File modelFile;
    private List<String> paragraphs;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("textSplitter.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Text Splitter");
        primaryStage.setScene(new Scene(root, 900, 800));
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(600);
        primaryStage.show();
        this.window = primaryStage.getScene().getWindow();
    }

    @FXML
    void onInputFileButtonClick(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        inputFile = chooser.showOpenDialog(window);
        outputPrevTV.setRoot(null);
        if (inputFile != null) {
            inputFileTF.setText(inputFile.getAbsolutePath());
            String inputContent = FileUtils.readFileAsString(inputFile.toPath())
                    .orElse("Can't load file preview");
            inputPrevTA.setText(inputContent);
            processBT.setDisable(false);
        } else {
            inputPrevTA.clear();
            processBT.setDisable(true);
        }
    }

    @FXML
    void onOutputFileButtonClick(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        outputFile = chooser.showSaveDialog(window);
        if (outputFile != null) {
            XmlGenerator generator = new XmlGenerator();
            Document doc = generator.generateXml(paragraphs);
            try {
                XmlGenerator.saveDocumentToFile(doc, outputFile);
            } catch (TransformerException ex) {
                showDialog(ex.getMessage(), "Can't save xml to file");
            }
        }
    }

    @FXML
    void onProcessButtonClick(ActionEvent e) {
        outputPrevTV.setRoot(null);
        if (inputFile == null || !inputFile.isFile()) {
            showDialog("Chose input file!", "Input file error");
            return;
        }
        if (modelFile == null) {
            showDialog("Load neural network model first!", "Model file error");
        }
        ParagraphDetector detector = getParagraphDetector();
        TextFileProcessor textFileProcessor = new TextFileProcessor(inputFile, detector);
        paragraphs = textFileProcessor.splitDocumentIntoParagraphs();
        TreeItem<String> xmlTree = XMLTreeViewGenerator.generateTree(paragraphs);
        outputPrevTV.setRoot(xmlTree);
        saveBT.setDisable(false);
    }

    @FXML
    void onLoadModelClick(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        modelFile = chooser.showOpenDialog(window);
        if (modelFile != null) {
            statusL.setText("Model loaded from file: " + modelFile.getName());
        } else {
            statusL.setText("Model not loaded");
        }
    }

    @FXML
    void onModeButtonClick(ActionEvent e) {
        String newText = modeBT.getText().equals("Neural network") ? "Custom" : "Neural network";
        modeBT.setText(newText);
    }

    private ParagraphDetector getParagraphDetector() {
        if (modeBT.getText().equals("Custom")) {
            return new CustomParagraphDetector(new SentenceConditionsMapper());
        }
        BasicNetwork network = NetworkProvider.restoreSavedNetwork(modelFile.getAbsolutePath());
        return new PerceptronParagraphDetector(network, new SentenceConditionsMapper());
    }

    private void showDialog(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
