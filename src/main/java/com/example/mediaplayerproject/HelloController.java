package com.example.mediaplayerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button buttonPlay;
    @FXML
    private Button buttonPause;
    @FXML
    private Button buttonStop;
    @FXML
    private Button buttonPrev;
    @FXML
    private Button buttonNext;
    @FXML
    private MediaView mediaView;
    @FXML
    private Label durationLabel; //Prompt text = Duration

    @FXML
    private Label titleLabel; //Prompt text = Title

    @FXML
    private ListView mediaList;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    /**
     * This method is invoked automatically in the beginning. Used for initializing, loading data etc.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){ //Need to change the link below to drag info from database
        file = new File("c:\\Users\\Jesper\\IdeaProjects\\MediaPlayerProject\\src\\media\\eyy_gtfo_outta_here_dog_meme_a440-jN8drc.mp4"); //filepath
        media = new Media(file.toURI().toString()); //changes filepath to readable media
        mediaPlayer = new MediaPlayer(media); //add media to mediaplayer
        mediaView.setMediaPlayer(mediaPlayer); //add videocontent to the mediaview (without this line, it will only play sounds)
        mediaPlayer.setAutoPlay(false); //disable autoplay, so we can control the media using buttons
    }

    @FXML
    protected void onButtonHelloClick() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT fldMediaTitle FROM tblMediaInfo");
        ResultSet resultSet = preparedStatement.executeQuery();

        mediaList.getItems().clear();
        while(resultSet.next()) {
            String tempString = resultSet.getString(1);
            mediaList.getItems().add(tempString);
        }
        connection.close();
    }

    public void onButtonPlayClick() {
        mediaPlayer.play();
    }

    public void onButtonPauseClick() {
        mediaPlayer.pause();
    }

    public void onButtonStopClick() {
        mediaPlayer.stop();
    }

    public void onButtonPrevClick(ActionEvent actionEvent) {
    }

    public void onButtonNextClick(ActionEvent actionEvent) {
    }
}