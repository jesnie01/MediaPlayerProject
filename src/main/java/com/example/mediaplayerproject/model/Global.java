package com.example.mediaplayerproject.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.util.ArrayList;

public class Global {

    public static String User = "bob1";
    public static MediaPlayer mediaPlayer;
    public static MediaView mediaView;

    public static void stopMedia(MediaPlayer mediaPlayer, MediaView mediaView){
        if (mediaView != null && mediaPlayer != null){
            mediaPlayer.dispose();
            mediaView.setMediaPlayer(null);
        }
    }
    public static ArrayList<MediaInfo> allMedia = new ArrayList<>();

    public static ArrayList<MediaInfo> playlistMedia = new ArrayList<>();

    public static int currentIndexOfMediaInPlaylist = 0;

    public static String fxmlFile = "viewMediaPlayer-view.fxml";

    public static String relativePath = "src\\main\\resources\\com\\example\\mediaplayerproject\\viewMediaPlayer-view.fxml";

}
