package com.example.mediaplayerproject;
import com.example.mediaplayerproject.model.GlobalInfo;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainLayoutView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
        stage.setTitle("Media Meme Player");
        stage.setMinWidth(664);
        stage.setMinHeight(488);
        stage.setScene(scene);
        stage.show();
        try { /*Loads the info from the media of the database into an arraylist,
               to avoid communication via the database as much as possible
               */
            ResultSet resultSet = SearchDB.searchMedia();
            while (resultSet.next()) {
                MediaInfo tempMedia = new MediaInfo();
                tempMedia.setMediaId(resultSet.getInt(1));
                tempMedia.setMediaTitle(resultSet.getString(2));
                tempMedia.setMediaPath(resultSet.getString(4));
                GlobalInfo.allMedia.add(tempMedia);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}