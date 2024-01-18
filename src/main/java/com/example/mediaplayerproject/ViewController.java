package com.example.mediaplayerproject;

import com.example.mediaplayerproject.model.Global;
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
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;

public class ViewController {
    @FXML
    private AnchorPane dynamicView;

    MediaPlayer player = Global.mediaPlayer;
    MediaView view = Global.mediaView;
    @FXML
    private void SwitchToAllMedia() {
        loadView("viewAllMedia.fxml");
        Global.stopMedia(player, view);
    }

    @FXML
    private void SwitchToAllPlaylists() {
        loadView("viewPlaylists-view.fxml");
        Global.stopMedia(player, view);
    }
    @FXML
    private void SwitchToTestView() {
        loadView("viewMediaPlayer-view.fxml");
        Global.stopMedia(player, view);
    }
    @FXML
    private void SwitchToEditPlaylist() {
        loadView("createAndEditPlaylist.fxml");
    }
    @FXML
    private void SwitchToCreatePlaylist() {
        loadView("createAndEditPlaylist.fxml");
    }
    @FXML
    private void SwitchToHowTo() {
        loadView("");
    }
    @FXML
    private void SwitchToAboutUs() {
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