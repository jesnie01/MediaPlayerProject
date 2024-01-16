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
    private ListView mediaList;
    @FXML
    private MediaView mediaView = new MediaView();
    @FXML
    private TextField searchBox;
    @FXML
    private Label durationLabel; //Prompt text = Duration
    @FXML
    private RadioButton searchToggleArtist;
    @FXML
    private Label titleLabel; //Dette skal laves om til current duration i sangen?
    @FXML
    private Slider volumeSlider;
    @FXML
    private RadioButton searchToggleFilename;

    private File file = new File("src\\media\\getTheFuckOuttaHere.mp4").getAbsoluteFile(); //filepath

    private Media media = new Media(file.toURI().toString()); //changes filepath to readable media
    private MediaPlayer mediaPlayer = new MediaPlayer(media);
    //private mediaPlayer = new MediaPlayer(media); //add media to mediaplayer



    private ArrayList<String> toAddToPlaylist = new ArrayList<>();

    SearchDB searchDB = new SearchDB();
    /**
     * This method is invoked automatically in the beginning. Used for initializing, loading data etc.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){ //Need to change the link below to drag info from database

        try {
            ResultSet resultSet = SearchDB.searchMedia();
            while (resultSet.next()) {
                MediaInfo tempMedia = new MediaInfo();
                tempMedia.setMediaId(resultSet.getInt(1));
                tempMedia.setMediaTitle(resultSet.getString(2));
                //TBD Add creatorNames as arraylist here!
                tempMedia.setMediaPath(resultSet.getString(4));
                Global.allMedia.add(tempMedia);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        file = new File(Global.allMedia.get(Global.currentIndexOfMediaInPlaylist).getMediaPath() + ".mp4").getAbsoluteFile();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer); //add videocontent to the mediaview (without this line, it will only play sounds)
        mediaPlayer.setAutoPlay(false); //disable autoplay, so we can control the media using buttons
        volumeSlider.setValue(mediaPlayer.getVolume()*100);

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });
    }


    @FXML
    protected void onButtonHelloClick() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT fldMediaTitle FROM tblMediaInfo");
        ResultSet resultSet = preparedStatement.executeQuery();

        mediaList.getItems().clear(); // Stops the list from duplicating itself every click
        while(resultSet.next()) {
            String tempString = resultSet.getString(1); // gets everything from fldMediaTitle as String values
            mediaList.getItems().add(tempString); // gets elements from ListView and replaces them with fldMediaTitle
        }

        connection.close();
    }

    public void onButtonPlayClick()
    {
        mediaPlayer.play();
    }

    public void onButtonPauseClick() {
        mediaPlayer.pause();
    }
    @FXML
    protected void onButtonPartialSearchClick() throws SQLException {
        boolean searchToggleBool = searchToggleArtist.isSelected();
        //Checks the searchbox on button click for a partial match in artist name
        ArrayList<String> searchedList = searchDB.searchPartial(searchBox.getText(), searchToggleBool);
        mediaList.getItems().clear();
        //if found, adds them to an ArrayList and displays it in a ListView
        for (String s : searchedList) {
            mediaList.getItems().add(s);
        }
    }

    public void onButtonStopClick() {
        mediaPlayer.dispose();
        mediaView.setMediaPlayer(null);
    }

    public void onButtonPrevClick(ActionEvent actionEvent) {
    }

    public void onButtonNextClick() {
        mediaPlayer.dispose();
        if (Global.currentIndexOfMediaInPlaylist < Global.allMedia.size()-1) {
            Global.currentIndexOfMediaInPlaylist++;
        }else {
            Global.currentIndexOfMediaInPlaylist = 0;
        }

        file = new File(Global.allMedia.get(Global.currentIndexOfMediaInPlaylist).getMediaPath() + ".mp4").getAbsoluteFile();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        //mediaPlayer.setAutoPlay(true);

    }
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
}