package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class viewPlaylistsController  {
    public AnchorPane allPlaylistView;
    public Label playlistLabel;
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

    /**
     * Shows a list of media on the selected playlist
     * @param mouseEvent
     * @throws SQLException
     */
    @FXML
    public void clickGetList(MouseEvent mouseEvent) throws SQLException {
        listViewOfMediaInCurrentPlaylist.getItems().clear();
        String currentPlaylist = playlistListView.getSelectionModel().getSelectedItem().toString();
        playlistLabel.setText(currentPlaylist);
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

    /**
     * Changes view to the mediaplayer and loads the selected playlist with the selected media as current index, ready to play,
     * if no media is selected, it will load the first media on the playlist as current index
     * @param actionEvent onMouseClick
     */
    public void onPlayCurrentSelectedPlaylistButtonClick(ActionEvent actionEvent) {
        String selectedItem = playlistListView.getSelectionModel().getSelectedItem().toString(); //Fetch the title of selected media
        for (int i = 0; i < Global.playlistMedia.size(); i++) {
            if (selectedItem.equals(Global.playlistMedia.get(i).getMediaTitle())) { //Check the array allMedia for a match
                Global.playlistMedia.clear(); //Clears the playlist
                Global.playlistMedia.add(Global.allMedia.get(i)); //Add matching media to playlist
            }
        }
        try { //Loads the view of the mediaplayer with the matching index of the selected media, ready to play
            System.out.println("Loading view: " + Global.fxmlFile);
            AnchorPane newView = FXMLLoader.load(new File(Global.relativePath).toURI().toURL());
            allPlaylistView.getChildren().removeAll();
            allPlaylistView.getChildren().setAll(newView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes starting index of the playlist to currently selected media
     * @param mouseEvent
     */
    public void onMediaSelectedClick(MouseEvent mouseEvent) {
        if(listViewOfMediaInCurrentPlaylist.getSelectionModel().getSelectedItem()!=null){
            Global.currentIndexOfMediaInPlaylist = listViewOfMediaInCurrentPlaylist.getSelectionModel().getSelectedIndex();
        }else{Global.currentIndexOfMediaInPlaylist = 0;}
    }
}

