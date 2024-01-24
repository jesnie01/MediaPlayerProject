package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.GlobalInfo;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.net.URL;
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
    private ListView playlistView;
    @FXML
    private StackPane playListToggle;
    @FXML
    private Label durationLabel; //Prompt text = Duration
    @FXML
    private Label titleLabel; //Dette skal laves om til current duration i sangen?
    @FXML
    private Slider volumeSlider = new Slider();
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
        /*
        file = new File(Global.playlistMedia.get(Global.currentIndexOfMediaInPlaylist).getMediaPath()).getAbsoluteFile();
        */
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media); //Adds sound of the mediafile to the mediaplayer
        mediaView.setMediaPlayer(mediaPlayer); //add videocontent to the mediaview (without this line, it will only play sounds)
        mediaPlayer.setAutoPlay(false); //disable autoplay, so we can control the media using buttons
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        AnchorPane.setTopAnchor(mediaView, 0.0);
        AnchorPane.setBottomAnchor(mediaView, 0.0);
        AnchorPane.setLeftAnchor(mediaView, 0.0);
        AnchorPane.setRightAnchor(mediaView, 0.0);
        mediaView.fitWidthProperty().bind(mediaGridpane.widthProperty());
        mediaView.fitHeightProperty().bind(mediaGridpane.heightProperty());
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
        GlobalInfo.playlistMedia.clear();
    }
    /**
     * Displays the media of the previous index of the playlist in the mediaplayer, ready to play
     */
    public void btnPrev() {
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
    }
    /**
     * Displays the media of the next index of the playlist in the mediaplayer, ready to play
     */
    public void btnNext() {
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
    }
    @FXML
    public void btnList(ActionEvent actionEvent) {
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
            for(int i = 0; i < GlobalInfo.playlistMedia.size(); i++)
            {
                playlistView.getItems().add(GlobalInfo.playlistMedia.get(i).getMediaTitle());
            }
        }
    }
}