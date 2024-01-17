package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.HelloApplication;
import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class viewPlaylistsController  {
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
    public void onButtonClickDeletePlaylist() throws SQLException {
        Connection connection = com.example.mediaplayerproject.model.DBConnection.makeConnection();
        String deleteSQL = "DELETE FROM tblPlayList WHERE fldPlaylistTitle = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
        deleteStatement.setString(1, (String) playlistListView.getSelectionModel().getSelectedItem());
        deleteStatement.executeUpdate();
    }
    public void onButtonClickCreatePlaylist() throws SQLException {


    }

    @FXML
    public void clickGetList(MouseEvent mouseEvent) throws SQLException {
        listViewOfMediaInCurrentPlaylist.getItems().clear();
        ResultSet resultSet = SearchDB.searchMediaInPlaylist(playlistListView.getSelectionModel().selectedItemProperty().getValue().toString());
        Global.playlistMedia.clear();
        while (resultSet.next()) {
            Global.playlistMedia.add(resultSet.getString(5));

        }
        for (String s : Global.playlistMedia) {
            listViewOfMediaInCurrentPlaylist.getItems().add(s);
        }
        if(listViewOfMediaInCurrentPlaylist.getItems().isEmpty())
        {
            listViewOfMediaInCurrentPlaylist.getItems().add("Playlist is empty.");
        }

    }
}

