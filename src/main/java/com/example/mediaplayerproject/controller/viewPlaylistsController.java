package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    @FXML
    public void clickGetList(MouseEvent mouseEvent) throws SQLException {
        listViewOfMediaInCurrentPlaylist.getItems().clear();
        ResultSet resultSet = SearchDB.searchMediaInPlaylist(playlistListView.getSelectionModel().selectedItemProperty().getValue().toString());
        Global.playlistMedia.clear();
        while (resultSet.next()) {
            for (int i = 0; i < Global.allMedia.size(); i++) {
                if(resultSet.getString(5).equals(Global.allMedia.get(i).getMediaTitle())){
                    Global.playlistMedia.add(Global.allMedia.get(i));
                }
            }

        }
        for (int i = 0; i < Global.playlistMedia.size(); i++) {
            listViewOfMediaInCurrentPlaylist.getItems().add(Global.playlistMedia.get(i).getMediaTitle());
        }
    }
}

