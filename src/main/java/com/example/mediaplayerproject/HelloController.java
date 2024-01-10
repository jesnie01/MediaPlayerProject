package com.example.mediaplayerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
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
    private Button buttonPlay;
    @FXML
    private Label welcomeText;
    @FXML
    private Button buttonPause;
    @FXML
    private Button buttonStop;
    @FXML
    private ListView mediaList;
    @FXML
    private Button buttonPrev;
    @FXML
    private Button buttonNext;
    @FXML
    private MediaView mediaView = new MediaView();
    @FXML
    private TextField searchBox;
    @FXML
    private Label durationLabel; //Prompt text = Duration
    @FXML
    private RadioButton searchToggleArtist;
    @FXML
    private Label titleLabel; //Prompt text = Title

    @FXML
    private RadioButton searchToggleFilename;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

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

        file = new File("src\\media\\eyy_gtfo_outta_here_dog_meme_a440-jN8drc.mp4").getAbsoluteFile(); //filepath
        media = new Media(file.toURI().toString()); //changes filepath to readable media
        mediaPlayer = new MediaPlayer(media); //add media to mediaplayer
        mediaView.setMediaPlayer(mediaPlayer); //add videocontent to the mediaview (without this line, it will only play sounds)
        mediaPlayer.setAutoPlay(false); //disable autoplay, so we can control the media using buttons
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
        mediaPlayer.stop();
    }

    public void onButtonPrevClick(ActionEvent actionEvent) {
    }

    public void onButtonNextClick(ActionEvent actionEvent) {
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