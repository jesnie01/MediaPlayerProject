package com.example.mediaplayerproject;
import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.MediaInfo;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createAndEditPlaylist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
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
    }
    public static void main(String[] args) {
        launch();

    }
}