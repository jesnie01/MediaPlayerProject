package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.GlobalInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;

import static com.example.mediaplayerproject.model.GlobalInfo.mediaPlayer;

public class MainLayoutController {
    @FXML
    private AnchorPane dynamicView;
    @FXML
    MediaPlayer player = mediaPlayer;
    MediaView view = GlobalInfo.mediaView;

    /**
     * Changes the view to ViewMedia
     */
    @FXML
    private void SwitchToAllMedia() {
        loadView("AllMediaView.fxml");
        GlobalInfo.stopMedia(player, view);
    }

    /**
     * Changes the view to ViewPlaylists
     */
    @FXML
    private void SwitchToAllPlaylists() {
        loadView("PlaylistView.fxml");
        GlobalInfo.stopMedia(player, view);
    }

    /**
     * Changes view to MediaPlayer (this option is not visible by default)
     */
    @FXML
    private void SwitchToMediaPlayer() {
        loadView("MediaPlayerView.fxml");
        GlobalInfo.stopMedia(player, view);
    }

    /**
     * Changes view to EditPlaylist
     */
    @FXML
    private void SwitchToEditPlaylist() {
        loadView("CreateEditPlaylistView.fxml");
        GlobalInfo.stopMedia(player, view);
    }

    /**
     * Changes view to CreatePlaylist
     */
    @FXML
    private void SwitchToCreatePlaylist() {
        loadView("CreateEditPlaylistView.fxml");
        GlobalInfo.stopMedia(player, view);
    }

    /**
     * Changes view to HowTo
     */
    @FXML
    private void SwitchToHowTo() {
        loadView("");
        GlobalInfo.stopMedia(player, view);
    }

    /**
     * Changes view to AboutUs
     */
    @FXML
    private void SwitchToAboutUs() {
        loadView("AllMediaView.fxml");
        GlobalInfo.stopMedia(player, view);
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