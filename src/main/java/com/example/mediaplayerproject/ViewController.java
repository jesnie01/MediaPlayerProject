package com.example.mediaplayerproject;

import com.example.mediaplayerproject.model.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.IllegalFormatWidthException;

import static com.example.mediaplayerproject.model.Global.mediaPlayer;

public class ViewController {
    @FXML
    private AnchorPane dynamicView;
    @FXML
    private GridPane MainGridpane;
    MediaPlayer player = mediaPlayer;
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
        loadView("CreateAndEditPlaylist.fxml");
        Global.stopMedia(player, view);
    }
    @FXML
    private void SwitchToCreatePlaylist() {
        loadView("CreateAndEditPlaylist.fxml");
        Global.stopMedia(player, view);
    }
    @FXML
    private void SwitchToHowTo() {
        loadView("");
        Global.stopMedia(player, view);
    }
    @FXML
    private void SwitchToAboutUs() {
        loadView("viewAllMedia.fxml");
        Global.stopMedia(player, view);
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

                for (Node node : dynamicView.getChildren()) {
                    AnchorPane.setTopAnchor(node, 0.0);
                    AnchorPane.setRightAnchor(node, 0.0);
                    AnchorPane.setBottomAnchor(node, 0.0);
                    AnchorPane.setLeftAnchor(node, 0.0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}