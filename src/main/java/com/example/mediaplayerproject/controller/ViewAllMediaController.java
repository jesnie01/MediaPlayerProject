package com.example.mediaplayerproject.controller;
import com.example.mediaplayerproject.HelloApplication;
import com.example.mediaplayerproject.ViewController;
import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.MediaInfo;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewAllMediaController {
    @FXML
    private AnchorPane allMediaView;
    @FXML
    private ListView mediaList;
    @FXML
    private TextField searchBox;
    @FXML
    private RadioButton searchToggleArtist;
    @FXML
    private RadioButton searchToggleFilename;
    SearchDB searchDB = new SearchDB();

    @FXML
    protected void onButtonPartialSearchClick() throws SQLException {
        mediaList.getItems().clear();
        boolean searchToggleBool = searchToggleArtist.isSelected();
        //Checks the searchbox on button click for a partial match in artist name
        ArrayList<String> searchedList = searchDB.searchPartial(searchBox.getText(), searchToggleBool);
        //if found, adds them to an ArrayList and displays it in a ListView
        for (String s : searchedList) {
            mediaList.getItems().add(s);
        }
    }

    public void onPlaySelectedMediaClick() {
        String selectedItem = mediaList.getSelectionModel().getSelectedItem().toString(); //Fetch the title of selected media
        for (int i = 0; i < Global.allMedia.size(); i++) {
            if (selectedItem.equals(Global.allMedia.get(i).getMediaTitle())) { //Check the array allMedia for a match
                Global.playlistMedia.clear();
                Global.playlistMedia.add(Global.allMedia.get(i)); //Adds the match of MediaTitle to the playlist
                Global.currentIndexOfMediaInPlaylist = 0; //Reset the index
            }
        }
        try { //Loads the view of the mediaplayer with the matching index of the selected media, ready to play
            System.out.println("Loading view: " + Global.fxmlFile);
            AnchorPane newView = FXMLLoader.load(new File(Global.relativePath).toURI().toURL());
            allMediaView.getChildren().removeAll();
            allMediaView.getChildren().setAll(newView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}