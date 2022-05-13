package com.edencoding.controllers;

import com.edencoding.utils.Cryptoanaliz;
import com.jfoenix.controls.JFXSlider;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class DragFileIntoJavaFX {

    @FXML
    public TextArea textArea;
    @FXML
    public Pane dropInstructions;
    @FXML
    private Pane titlePane;
    @FXML
    private ImageView btnMinimize, btnClose;
    @FXML
    private Button encrypt, decipher, keyButton;
    @FXML
    private JFXSlider keySlider;

    private double x;
    private double y;


    public void initialize() {
        makeTextAreaDragTarget(textArea);
        keyButton.setOnMouseClicked(event -> keySlider.setVisible(true));
    }

    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
    }


    public void makeEncrypt(String textFromFile) {

        Cryptoanaliz cryptoanaliz = new Cryptoanaliz(textFromFile.length());
        encrypt.setOnMouseClicked(mouseEvent -> textArea.setText(String.valueOf(cryptoanaliz.encrypt((int) keySlider.getValue(), textFromFile.toCharArray()))));
    }

    public void makeDecipher(String textFromFile) {
        Cryptoanaliz cryptoanaliz = new Cryptoanaliz(textFromFile.length());
        decipher.setOnMouseClicked(mouseEvent -> textArea.setText(String.valueOf(cryptoanaliz.decrypt((int) keySlider.getValue(), textFromFile.toCharArray()))));
    }

    private void makeTextAreaDragTarget(Node node) {
        node.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY));

        node.setOnDragExited(event -> dropInstructions.setVisible(false));

        node.setOnDragEntered(event -> dropInstructions.setVisible(true));

        node.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            if (event.getDragboard().hasFiles()) {
                fileLoaderTask(db.getFiles().get(0)).run();
            }

            dropInstructions.setVisible(false);
        });

    }

    private Task<String> fileLoaderTask(File fileToLoad) {
        //Create a task to load the file asynchronously
        Task<String> loadFileTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));

                //Use Files.lines() to calculate total lines - used for progress
                long lineCount;
                try (Stream<String> stream = Files.lines(fileToLoad.toPath())) {
                    lineCount = stream.count();
                }

                //Load in all lines one by one into a StringBuilder separated by "\n" - compatible with TextArea
                String line;
                StringBuilder totalFile = new StringBuilder();
                long linesLoaded = 0;
                while ((line = reader.readLine()) != null) {
                    totalFile.append(line);
                    totalFile.append("\n");
                    updateProgress(++linesLoaded, lineCount);
                }

                return totalFile.toString();
            }
        };

        //If successful, update the text area, display a success message and store the loaded file reference
        loadFileTask.setOnSucceeded(workerStateEvent -> {
            try {
                textArea.setText(loadFileTask.get());
                makeEncrypt(textArea.getText());
                makeDecipher(textArea.getText());
            } catch (InterruptedException | ExecutionException e) {
                textArea.setText("Не могу загрузить файл:\n " + fileToLoad.getAbsolutePath());
            }
        });

        //If unsuccessful, set text area with error message and status message to failed
        loadFileTask.setOnFailed(workerStateEvent -> textArea.setText("Не могу загрузить файл:\n " + fileToLoad.getAbsolutePath()));

        return loadFileTask;
    }

}
