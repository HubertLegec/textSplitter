package com.pw.eiti.wedt;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class TextSplitter extends Application {
    private Window window;
    @FXML
    private Button inputFileBT;
    @FXML
    private TextField inputFileTF;
    @FXML
    private Button outputFileBT;
    @FXML
    private TextField outputFileTF;
    @FXML
    private Button processBT;
    @FXML
    private TextArea inputPrevTA;
    @FXML
    private TextArea outputPrevTA;

    private File inputFile;
    private File outputFile;
    private String inputContent;

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
        outputPrevTA.clear();
        if (inputFile != null) {
            inputFileTF.setText(inputFile.getAbsolutePath());
            try {
                inputContent = FileUtils.readFileToString(inputFile, "UTF-8");
                inputPrevTA.setText(inputContent);
            } catch (IOException ex) {
                inputPrevTA.setText("Cannot load file preview");
            }
        } else {
            inputPrevTA.clear();
        }
    }

    @FXML
    void onOutputFileButtonClick(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        outputFile = chooser.showSaveDialog(window);
        if (outputFile != null) {
            outputFileTF.setText(outputFile.getAbsolutePath());
        }
    }

    @FXML
    void onProcessButtonClick(ActionEvent e) {
        outputPrevTA.clear();
        if (inputFile == null || !inputFile.isFile()) {
            showDialog("Chose input file!", "Input file error");
            return;
        }
        if (outputFile == null) {
            showDialog("Enter output file path before processing!", "Output path error");
            return;
        }
        try {
            TextFileProcessor textFileProcessor = new TextFileProcessor(inputFile, outputFile);
            String result = textFileProcessor.process()
                    .orElse("Output not available");
            outputPrevTA.setText(result);
        } catch (Exception ex) {
            // TODO - change to some error message
            outputPrevTA.setText(ex.getMessage());
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
