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

    /**
     * Changes the view to ViewMedia
     */
    @FXML
    private void SwitchToAllMedia() {
        loadView("viewAllMedia.fxml");
        Global.stopMedia(player, view);
    }

    /**
     * Changes the view to ViewPlaylists
     */
    @FXML
    private void SwitchToAllPlaylists() {
        loadView("viewPlaylists-view.fxml");
        Global.stopMedia(player, view);
    }

    /**
     * Changes view to MediaPlayer (this option is not visible by default)
     */
    @FXML
    private void SwitchToTestView() {
        loadView("viewMediaPlayer-view.fxml");
        Global.stopMedia(player, view);
    }

    /**
     * Changes view to EditPlaylist
     */
    @FXML
    private void SwitchToEditPlaylist() {
        loadView("createAndEditPlaylist.fxml");
    }

    /**
     * Changes view to CreatePlaylist
     */
    @FXML
    private void SwitchToCreatePlaylist() {
        loadView("createAndEditPlaylist.fxml");
    }

    /**
     * Changes view to HowTo
     */
    @FXML
    private void SwitchToHowTo() {
        loadView("");
    }

    /**
     * Changes view to AboutUs
     */
    @FXML
    private void SwitchToAboutUs() {
        loadView("viewAllMedia.fxml");
    }

    /**
     * Changes the current view (excluding the Top Menu) depending on the chosen menu option.
     * Loads a new fxml file depending on the chosen menu option
     * @param fxmlFile
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