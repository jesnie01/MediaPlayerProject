package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.DBConnection;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class viewPlaylistsController  {
    @FXML
    private TextField playlistSearchField;
    @FXML
    private ListView playlistListView;
    @FXML
    protected void onButtonClickSearchPlaylists() throws SQLException {
        ResultSet resultSet = SearchDB.searchPlaylists();

        playlistListView.getItems().clear();

        if (playlistSearchField.getText().isEmpty()) {
            while (resultSet.next()) {
                playlistListView.getItems().add(resultSet.getString(2));
            }
        }else {
            while (resultSet.next()) {
                if (resultSet.getString(2).toLowerCase().contains(playlistSearchField.getText().toLowerCase())) {
                    playlistListView.getItems().add(resultSet.getString(2));
                }
                if (playlistListView.getItems().isEmpty()) {
                    playlistListView.getItems().add("Nothing found");
                }
            }
        }
    }
}
