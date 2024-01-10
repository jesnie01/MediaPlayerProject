package com.example.mediaplayerproject;

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
    private Label titleLabel; //Dette skal laves om til current duration i sangen?
    @FXML
    private Slider volumeSlider;
    @FXML
    private RadioButton searchToggleFilename;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int volume = 50;

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
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                volume = (int)volumeSlider.getValue();
                mediaPlayer.setVolume(volume);

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
        mediaPlayer.stop();
    }

    public void onButtonPrevClick(ActionEvent actionEvent) {
    }

    public void onButtonNextClick(ActionEvent actionEvent) {
    }
}