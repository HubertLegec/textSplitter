package com.pw.eiti.wedt;

import com.pw.eiti.wedt.conditions.SentenceConditionsMapper;
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

    private File inputFile;
    private File outputFile;
    private File modelFile;

    private List<String> paragraphs;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("textSplitter.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Text Splitter");
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.setMinWidth(500);
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
        BasicNetwork network = NetworkProvider.restoreSavedNetwork(modelFile.getAbsolutePath());
        ParagraphDetector detector = new PerceptronParagraphDetector(network, new SentenceConditionsMapper());
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
