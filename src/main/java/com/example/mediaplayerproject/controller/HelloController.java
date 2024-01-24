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
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
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
    private Label totalTime; //Prompt text = Duration
    @FXML
    private Label testTime; //Dette skal laves om til current duration i sangen?
    @FXML
    private Slider volumeSlider = new Slider();
    @FXML
    private ListView playlistView;
    @FXML
    private StackPane playListToggle;
    @FXML
    private ProgressBar VideoProgressBar = new ProgressBar();

    private File file = new File("src\\media\\getTheFuckOuttaHere.mp4").getAbsoluteFile(); //filepath

    private Media media = new Media(file.toURI().toString()); //changes filepath to readable media
    private MediaPlayer mediaPlayer = Global.mediaPlayer;

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
     * Plays displayed media
     */
    public void btnPlay() {
        mediaPlayer.play();
        int tHours = (int) (mediaPlayer.getTotalDuration().toSeconds() / 3600);
        int tMinutes = (int) ((mediaPlayer.getTotalDuration().toSeconds() % 3600) / 60);
        int tSeconds = (int) (mediaPlayer.getTotalDuration().toSeconds() % 60);
        totalTime.setText(String.format("%02d:%02d:%02d",tHours, tMinutes, tSeconds));

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            VideoProgressBar.setProgress(newTime.toMillis() / mediaPlayer.getTotalDuration().toMillis());
            int hours = (int) (newTime.toSeconds() / 3600);
            int minutes = (int) ((newTime.toSeconds() % 3600) / 60);
            int seconds = (int) (newTime.toSeconds() % 60);
            testTime.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
        });
    }

    /**
     * Pauses displayed media
     */
    public void btnPause() {
        mediaPlayer.pause();
    }

    /**
     * Stops displayed media and removes the media from the mediaplayer
     */
    public void btnStop() {
        mediaPlayer.dispose();
        mediaView.setMediaPlayer(null);
        playlistView.getItems().clear();
        Global.playlistMedia.clear();
    }

    /**
     * Displays the media of the previous index of the playlist in the mediaplayer, ready to play
     */
    public void btnPrev() {
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
    public void btnNext() {
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

    @FXML
    public void onTogglePlaylistClick(ActionEvent actionEvent) {

        if(playListToggle.isVisible())
        {
            playListToggle.setVisible(false);
            playListToggle.setDisable(true);
        }
        else
        {
            playListToggle.setVisible(true);
            playListToggle.setDisable(false);
            playlistView.getItems().clear();
            for(int i = 0; i < Global.playlistMedia.size(); i++)
            {
                playlistView.getItems().add(Global.playlistMedia.get(i).getMediaTitle());
            }
        }
    }

    public void dragDone(DragEvent dragEvent) {
        System.out.println(VideoProgressBar.getProgress());
    }

    public void dragDetect(MouseEvent mouseEvent) {
        System.out.println("drag detected!");
    }

    public void dragDropped(DragEvent dragEvent) {
        System.out.println("drag dropped!");
    }

    public void dragEntered(DragEvent dragEvent) {
        VideoProgressBar.setProgress(dragEvent.getX());
        System.out.println("drag entered!");
    }

    public void dragMouseReleased(MouseDragEvent mouseDragEvent) {
        System.out.println("mouse drag released!");
    }

    public void dragExit(DragEvent dragEvent) {
        System.out.println("FUCK YOU!");
    }

    public void dragOver(DragEvent dragEvent) {
        System.out.println("drag over!");
    }

    /*
    @FXML
    protected void onButtonClickPlaylistHanndler() {
        String tempString = "Added ";
        tempString += mediaList.getSelectionModel().getSelectedItem().toString();
        toAddToPlaylist.add(tempString);
    }
    @FXML
    protected void onButtonClickSPlist() {
        mediaList.getItems().clear();
        for (String i : toAddToPlaylist) {
            mediaList.getItems().add(i);
        }
    }
    @FXML
    protected void onButtonClickClearPlaylist() {
        toAddToPlaylist.clear();
        mediaList.getItems().clear();
    }
  */
}