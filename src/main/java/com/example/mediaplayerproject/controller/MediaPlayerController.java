package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.DBConnection;
import com.example.mediaplayerproject.model.GlobalInfo;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MediaPlayerController implements Initializable {
    @FXML
    public AnchorPane mediaAnchorpane;
    @FXML
    public GridPane mediaGridpane;
    @FXML
    private MediaView mediaView =  GlobalInfo.mediaView;
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
    private ListView currentPlaylist;
    @FXML
    private Label totalTime; //Prompt text = Duration
    @FXML
    private Label currentTime; //Dette skal laves om til current duration i sangen?
    @FXML
    private Slider volumeSlider = new Slider();
    @FXML
    private ProgressBar VideoProgressBar = new ProgressBar();

    private File file = new File("src\\media\\NoFile.mp4").getAbsoluteFile(); //filepath
    private Media media = new Media(file.toURI().toString()); //changes filepath to readable media
    private MediaPlayer mediaPlayer = GlobalInfo.mediaPlayer;
    /**
     * This method is invoked automatically in the beginning. Used for initializing the mediaplayer, loading data etc.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { //Initializes the mediaplayer
        if(GlobalInfo.mediaSelected) {
            file = new File(GlobalInfo.playlistMedia.get(GlobalInfo.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
        }
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media); //Adds sound of the mediafile to the mediaplayer
        mediaView.setMediaPlayer(mediaPlayer); //add videocontent to the mediaview (without this line, it will only play sounds)
        mediaPlayer.setAutoPlay(false); //disable autoplay, so we can control the media using buttons
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);

        mediaAnchorpane.setTopAnchor(mediaView, 0.0);
        mediaAnchorpane.setBottomAnchor(mediaView, 0.0);
        mediaAnchorpane.setLeftAnchor(mediaView, 0.0);
        mediaAnchorpane.setRightAnchor(mediaView, 0.0);
        mediaView.fitWidthProperty().bind(mediaGridpane.widthProperty());
        mediaView.fitHeightProperty().bind(mediaGridpane.heightProperty());
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });

//        VideoProgressBar.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            System.out.println(e.getX());
//            System.out.println(e.getY());
//        });

        VideoProgressBar.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            mediaPlayer.pause();
            VideoProgressBar.setProgress(e.getX()/VideoProgressBar.getWidth());
        });

        VideoProgressBar.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            mediaPlayer.seek(Duration.seconds(mediaPlayer.getTotalDuration().toSeconds()*(e.getX()/VideoProgressBar.getWidth())));
            mediaPlayer.play();
        });
    }
    /**
     * Plays displayed media
     */
    public void btnPlay() {
        if (GlobalInfo.mediaSelected) {
            mediaPlayer.play();
            System.out.println("Media Selected: " + GlobalInfo.mediaSelected);
        }
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
            currentTime.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
        });
    }
    /**
     * Pauses displayed media
     */
    public void btnPause() {
        if (GlobalInfo.mediaSelected) {
            mediaPlayer.pause();
            System.out.println("Media Selected: " + GlobalInfo.mediaSelected);
        }
    }
    /**
     * Stops displayed media and removes the media from the mediaplayer
     */
    public void btnStop() {
        if (GlobalInfo.mediaSelected) {
            mediaPlayer.dispose();
            mediaView.setMediaPlayer(null);
            currentPlaylist.getItems().clear();
            GlobalInfo.playlistMedia.clear();
            System.out.println("Media Selected: " + GlobalInfo.mediaSelected);
        }
    }
    /**
     * Displays the media of the previous index of the playlist in the mediaplayer, ready to play
     */
    public void btnPrev() {
        System.out.println("Media Selected: " + GlobalInfo.mediaSelected);
        if (GlobalInfo.mediaSelected) {
            mediaPlayer.dispose(); //Flushes the mediaplayer
            if (GlobalInfo.currentIndexOfMediaInPlaylist > 0) {
                GlobalInfo.currentIndexOfMediaInPlaylist--;
            } else {
                GlobalInfo.currentIndexOfMediaInPlaylist = GlobalInfo.playlistMedia.size() - 1;
            }
            file = new File(GlobalInfo.playlistMedia.get(GlobalInfo.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            currentPlaylist.getSelectionModel().select(GlobalInfo.currentIndexOfMediaInPlaylist);
        }
    }
    /**
     * Displays the media of the next index of the playlist in the mediaplayer, ready to play
     */
    public void btnNext() {
        if (GlobalInfo.mediaSelected) {
            System.out.println("Media Selected: " + GlobalInfo.mediaSelected);
            mediaPlayer.dispose();
            if (GlobalInfo.currentIndexOfMediaInPlaylist < GlobalInfo.playlistMedia.size() - 1) {
                GlobalInfo.currentIndexOfMediaInPlaylist++;
            } else {
                GlobalInfo.currentIndexOfMediaInPlaylist = 0;
            }
            file = new File(GlobalInfo.playlistMedia.get(GlobalInfo.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            currentPlaylist.getSelectionModel().select(GlobalInfo.currentIndexOfMediaInPlaylist);
        }
    }
    @FXML
    public void btnList(ActionEvent actionEvent) {
        if(currentPlaylist.isVisible())
        {
            currentPlaylist.setVisible(false);
            currentPlaylist.setDisable(true);
        }
        else
        {
            currentPlaylist.setVisible(true);
            currentPlaylist.setDisable(false);
            currentPlaylist.getItems().clear();
            for(int i = 0; i < GlobalInfo.playlistMedia.size(); i++)
            {
                currentPlaylist.getItems().add(GlobalInfo.playlistMedia.get(i).getMediaTitle());
            }
            currentPlaylist.getSelectionModel().select(GlobalInfo.currentIndexOfMediaInPlaylist);
        }
    }

    public void selectFromPlaylist(MouseEvent mouseEvent) {
        mediaPlayer.dispose();
        mediaView.setMediaPlayer(null);
        GlobalInfo.currentIndexOfMediaInPlaylist = currentPlaylist.getSelectionModel().getSelectedIndex();
        file = new File(GlobalInfo.playlistMedia.get(GlobalInfo.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }
}