package com.edencoding;

import com.edencoding.controllers.DragFileIntoJavaFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/EdenCodingIcon.png")));
        stage.setTitle("CryptoANALizator");

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/CryptoAnalizator.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((DragFileIntoJavaFX) fxmlLoader.getController()).init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}