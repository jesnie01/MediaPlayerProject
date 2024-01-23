package com.example.mediaplayerproject;
import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.MediaInfo;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
        stage.setTitle("Media Player");
        stage.setMinWidth(664);
        stage.setMinHeight(488);
        stage.setScene(scene);
        stage.show();

        /*
        scene.addEventHandler(MediaViewReadyEvent.MEDIA_VIEW_READY, event -> {
            // Obtain a reference to the MediaView
            MediaView mediaView = ((MediaViewReadyEvent) event).getMediaView();
            // Now you can bind properties or perform other operations with the MediaView
            mediaView.fitHeightProperty().bind(scene.heightProperty());
            mediaView.fitWidthProperty().bind(scene.widthProperty());
        });
        */

        try {
            ResultSet resultSet = SearchDB.searchMedia();
            while (resultSet.next()) {
                MediaInfo tempMedia = new MediaInfo();
                tempMedia.setMediaId(resultSet.getInt(1));
                tempMedia.setMediaTitle(resultSet.getString(2));
                //TBD Add creatorNames as arraylist here!
                tempMedia.setMediaPath(resultSet.getString(4));
                Global.allMedia.add(tempMedia);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Perform some initial setup in a separate thread
        new Thread(() -> {
            // Simulate a time-consuming setup task
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Use Platform.runLater for UI-related setup
            Platform.runLater(() -> {
                System.out.println("" + scene.getHeight());
                System.out.println("" + scene.getWidth());
            });
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}