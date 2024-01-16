package com.example.mediaplayerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class ViewController {
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
    private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane dynamicView;

    @FXML
    private void SwitchToView1() {
        loadView("viewMediaPlayer-view.fxml");
    }

    @FXML
    private void SwitchToView2() {
        loadView("viewPlaylists-view.fxml");
    }
    @FXML
    private void SwitchToView3() {
        loadView("viewAllMedia.fxml");
    }
/*
    public void onButtonPlayClick() {
        mediaPlayer.play();
    }

    public void onButtonPauseClick() {
        mediaPlayer.pause();
    }

    public void onButtonStopClick() {
        mediaPlayer.stop();
    }

    public void onButtonPrevClick(ActionEvent actionEvent){}

    public void onButtonNextClick() {

        mediaPlayer.dispose();
        file = new File("src\\media\\laughMeme.mp4").getAbsoluteFile();
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        //mediaPlayer.setAutoPlay(true);
        }
        */

    private void loadView(String fxmlFile) {

        try {
            System.out.println("Loading view: " + fxmlFile);
            AnchorPane newView = FXMLLoader.load(getClass().getResource(fxmlFile));
            dynamicView.getChildren().removeAll();
            dynamicView.getChildren().setAll(newView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}