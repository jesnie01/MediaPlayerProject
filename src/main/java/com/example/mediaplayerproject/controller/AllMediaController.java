package com.example.mediaplayerproject.controller;
import com.example.mediaplayerproject.model.GlobalInfo;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllMediaController {
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

    /**
     * Searches for the partial matches depending on Radial Button selected (Title, Creator)
     * @throws SQLException
     */
    @FXML
    protected void btnPartialSearch() throws SQLException {
        mediaList.getItems().clear();
        boolean searchToggleBool = searchToggleArtist.isSelected();
        //Checks the searchbox on button click for a partial match in artist name
        ArrayList<String> searchedList = searchDB.searchPartial(searchBox.getText(), searchToggleBool);
        //if found, adds them to an ArrayList and displays it in a ListView
        for (String s : searchedList) {
            mediaList.getItems().add(s);
        }
    }

    /**
     * Changes the view to mediaplayer, and loads the selected item into said mediaplayer, ready to play.
     */
    public void btnPlayMedia() {
        String selectedItem = mediaList.getSelectionModel().getSelectedItem().toString(); //Fetch the title of selected media
        for (int i = 0; i < GlobalInfo.allMedia.size(); i++) {
            if (selectedItem.equals(GlobalInfo.allMedia.get(i).getMediaTitle())) { //Check the array allMedia for a match
                GlobalInfo.playlistMedia.clear();
                GlobalInfo.playlistMedia.add(GlobalInfo.allMedia.get(i)); //Adds the match of MediaTitle to the playlist
                GlobalInfo.currentIndexOfMediaInPlaylist = 0; //Reset the index
            }
        }
        try { //Loads the view of the mediaplayer with the matching index of the selected media, ready to play
            System.out.println("Loading view: " + GlobalInfo.fxmlFile);
            AnchorPane newView = FXMLLoader.load(getClass().getClassLoader().getResource(GlobalInfo.fxmlFile));
            allMediaView.getChildren().removeAll();
            allMediaView.getChildren().setAll(newView);

            for (Node node : allMediaView.getChildren()) {
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