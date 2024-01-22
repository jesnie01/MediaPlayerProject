package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.DBConnection;
import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.MediaInfo;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
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
    private MediaView mediaView =  Global.mediaView;
    @FXML
    private Label durationLabel; //Prompt text = Duration
    @FXML
    private Label titleLabel; //Dette skal laves om til current duration i sangen?
    @FXML
    private Slider volumeSlider = new Slider();

    private File file = new File("src\\media\\getTheFuckOuttaHere.mp4").getAbsoluteFile(); //filepath

    private Media media = new Media(file.toURI().toString()); //changes filepath to readable media
    private MediaPlayer mediaPlayer = Global.mediaPlayer;


    //private mediaPlayer = new MediaPlayer(media); //add media to mediaplayer


    private ArrayList<String> toAddToPlaylist = new ArrayList<>();

    /**
     * This method is invoked automatically in the beginning. Used for initializing, loading data etc.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { //Need to change the link below to drag info from database

        file = new File(Global.playlistMedia.get(Global.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media); //Adds sound of the mediafile to the mediaplayer

        mediaView.setMediaPlayer(mediaPlayer); //add videocontent to the mediaview (without this line, it will only play sounds)
        mediaPlayer.setAutoPlay(false); //disable autoplay, so we can control the media using buttons
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });
    }

    /**
     * Plays selected media
     */
    public void onButtonPlayClick() {
        mediaPlayer.play();
    }

    /**
     * Pauses selected media
     */
    public void onButtonPauseClick() {
        mediaPlayer.pause();
    }

    /**
     * Stops selected media and removes the display from the mediaplayer
     */
    public void onButtonStopClick() {
        mediaPlayer.dispose();
        mediaView.setMediaPlayer(null);
    }

    /**
     * Displays the media of the previous index of the playlist in the mediaplayer, ready to play
     */
    public void onButtonPrevClick() {
        mediaPlayer.dispose(); //Flushes the mediaplayer
        if (Global.currentIndexOfMediaInPlaylist > 0) {
            Global.currentIndexOfMediaInPlaylist--;
        } else {
            Global.currentIndexOfMediaInPlaylist = Global.playlistMedia.size() - 1;
        }

        file = new File(Global.playlistMedia.get(Global.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    /**
     * Displays the media of the next index of the playlist in the mediaplayer, ready to play
     */
    public void onButtonNextClick() {
        mediaPlayer.dispose();
        if (Global.currentIndexOfMediaInPlaylist < Global.playlistMedia.size() - 1) {
            Global.currentIndexOfMediaInPlaylist++;
        } else {
            Global.currentIndexOfMediaInPlaylist = 0;
        }

        file = new File(Global.playlistMedia.get(Global.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        //mediaPlayer.setAutoPlay(true);

    }
}