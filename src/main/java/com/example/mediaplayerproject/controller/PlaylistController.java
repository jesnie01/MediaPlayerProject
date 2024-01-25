package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.GlobalInfo;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistController {
    public AnchorPane allPlaylistView;
    public Label playlistLabel;
    @FXML
    private TextField playlistSearchField;
    @FXML
    private ListView playlistListView;
    @FXML
    private ListView listViewOfMediaInCurrentPlaylist;

    /**
     * searches for playlists on button click
     * @throws SQLException
     */
    @FXML
    protected void btnSearchPlaylists() throws SQLException {
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

    /**
     * Shows a list of media on the selected playlist
     * @param mouseEvent
     * @throws SQLException
     */
    @FXML
    public void selectPlaylist(MouseEvent mouseEvent) throws SQLException {
        GlobalInfo.mediaSelected = true;
        listViewOfMediaInCurrentPlaylist.getItems().clear();
        String currentPlaylist = playlistListView.getSelectionModel().getSelectedItem().toString();
        playlistLabel.setText(currentPlaylist);
        ResultSet resultSet = SearchDB.searchMediaInPlaylist(playlistListView.getSelectionModel().selectedItemProperty().getValue().toString());
        GlobalInfo.playlistMedia.clear();
        while (resultSet.next()) {
            for (int i = 0; i < GlobalInfo.allMedia.size(); i++) {
                if(resultSet.getString(5).equals(GlobalInfo.allMedia.get(i).getMediaTitle())){
                    GlobalInfo.playlistMedia.add(GlobalInfo.allMedia.get(i));
                }
            }

        }
        for (int i = 0; i < GlobalInfo.playlistMedia.size(); i++) {
            listViewOfMediaInCurrentPlaylist.getItems().add(GlobalInfo.playlistMedia.get(i).getMediaTitle());
        }
        GlobalInfo.currentIndexOfMediaInPlaylist = 0;
    }

    /**
     * Changes starting index of the playlist to currently selected media
     * @param mouseEvent
     */
    @FXML
    public void selectMedia(MouseEvent mouseEvent) {
        if(listViewOfMediaInCurrentPlaylist.getSelectionModel().getSelectedItem()!=null){
            GlobalInfo.currentIndexOfMediaInPlaylist = listViewOfMediaInCurrentPlaylist.getSelectionModel().getSelectedIndex();
        }else{
            GlobalInfo.currentIndexOfMediaInPlaylist = 0;
        }
    }

    /**
     * Changes view to the mediaplayer and loads the selected playlist with the selected media as current index, ready to play,
     * if no media is selected, it will load the first media on the playlist as current index
     * @param actionEvent onMouseClick
     */
    @FXML
    public void btnPlayPlaylist(ActionEvent actionEvent) {
        if(GlobalInfo.mediaSelected) {
            String selectedItem = playlistListView.getSelectionModel().getSelectedItem().toString(); //Fetch the title of selected media
            for (int i = 0; i < GlobalInfo.playlistMedia.size(); i++) {
                if (selectedItem.equals(GlobalInfo.playlistMedia.get(i).getMediaTitle())) { //Check the array allMedia for a match
                    GlobalInfo.playlistMedia.clear(); //Clears the playlist
                    GlobalInfo.playlistMedia.add(GlobalInfo.allMedia.get(i)); //Add matching media to playlist
                }
            }
        }
        try { //Loads the view of the mediaplayer with the matching index of the selected media, ready to play
            System.out.println("Loading view: " + GlobalInfo.fxmlFile);
            AnchorPane newView = FXMLLoader.load(getClass().getClassLoader().getResource(GlobalInfo.fxmlFile));
            allPlaylistView.getChildren().removeAll();
            allPlaylistView.getChildren().setAll(newView);

            for (Node node : allPlaylistView.getChildren()) {
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

