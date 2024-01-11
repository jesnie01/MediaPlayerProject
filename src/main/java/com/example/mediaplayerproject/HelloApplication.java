package com.example.mediaplayerproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewPlaylists-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 300);
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(400.0);
        stage.setMinHeight(350.0);
    }

    public static void main(String[] args) {

        launch();
    }
}