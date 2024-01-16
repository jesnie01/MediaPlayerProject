package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class viewPlaylistsController {

    @FXML
    private TextField playlistSearchField;
    @FXML
    private ListView playlistListView;
    @FXML
    private ListView listViewOfMediaInCurrentPlaylist;

    @FXML
    protected void onButtonClickSearchPlaylists() throws SQLException {
        playlistListView.getItems().clear();  //clears the list so results wont duplicate on click
        ResultSet resultSet = SearchDB.searchPlaylists();

        while (resultSet.next()) {
            //partial search, if searchfield contains something in fldPlayListTitle, prints it out.
            if (resultSet.getString(2).toLowerCase().contains(playlistSearchField.getText().toLowerCase())) {
                    playlistListView.getItems().add(resultSet.getString(2));
            }
        }
        if (playlistListView.getItems().isEmpty()) {
                playlistListView.getItems().add("Nothing found");
                listViewOfMediaInCurrentPlaylist.getItems().clear();
        }

    }
    @FXML
    public void clickGetList(MouseEvent mouseEvent) throws SQLException {
        ArrayList<String> tempArrayList = new ArrayList<>();
        listViewOfMediaInCurrentPlaylist.getItems().clear();
        ResultSet resultSet = SearchDB.searchMediaInPlaylist(playlistListView.getSelectionModel().selectedItemProperty().getValue().toString());
        while (resultSet.next()) {
            tempArrayList.add(resultSet.getString(5));
        }
        for (String s : tempArrayList) {
            listViewOfMediaInCurrentPlaylist.getItems().add(s);
        }
        if(listViewOfMediaInCurrentPlaylist.getItems().isEmpty())
        {
            listViewOfMediaInCurrentPlaylist.getItems().add("Playlist is empty.");
        }

    }
}

