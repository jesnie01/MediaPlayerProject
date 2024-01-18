package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAndEditPlaylistController {
     @FXML
     private Label playlistName;
     @FXML
     private Button refreshButton;
     @FXML
     private Button deleteButton;
     @FXML
     private Button searchButton;
     @FXML
     private Button addItemButton;
     @FXML
     private Button removeItemButton;
     @FXML
     private Button updateButton;
     @FXML
     private Button createButton;
     @FXML
     private TextField searchField;
     @FXML
     private TextField nameOfPlaylist;
     @FXML
     private ListView allPlaylistsView;
     @FXML
     private ListView allMediaView;
     @FXML
     private ListView currentPlaylistView;

     public void onButtonClickRefresh() throws SQLException {
          allPlaylistsView.getItems().clear();
          allMediaView.getItems().clear();
          ResultSet resultSet = SearchDB.searchPlaylists();
          while (resultSet.next()) {
               allPlaylistsView.getItems().add(resultSet.getString(2));
          }
          for (int i = 0; i < Global.allMedia.size(); i++) {
               allMediaView.getItems().add(Global.allMedia.get(i).getMediaTitle());
          }
     }
     @FXML
     public void plOnMouseClick(MouseEvent mouseEvent) throws SQLException {
          currentPlaylistView.getItems().clear();
          if (allPlaylistsView.getSelectionModel().getSelectedItem() != null) {
               nameOfPlaylist.setText(allPlaylistsView.getSelectionModel().selectedItemProperty().getValue().toString());
               ResultSet resultSet = SearchDB.searchMediaInPlaylist(allPlaylistsView.getSelectionModel().selectedItemProperty().getValue().toString());
               Global.playlistMedia.clear();
               while (resultSet.next()) {
                    Global.playlistMedia.add(resultSet.getString(5));

               }
               for (String s : Global.playlistMedia) {
                    currentPlaylistView.getItems().add(s);
               }
          }
     }
     public void onButtonClickSearch() {

     }
     public void onButtonClickDelete() {

     }
     public void onButtonClickUpdate() {

     }
     public void onButtonClickAdd() {
          if (allMediaView.getSelectionModel().getSelectedItem() != null) {
               currentPlaylistView.getItems().add(allMediaView.getSelectionModel().selectedItemProperty().getValue().toString());
          }
     }
     public void onButtonClickRemove() {
          if (currentPlaylistView.getSelectionModel().getSelectedItem() != null) {
               currentPlaylistView.getItems().remove(currentPlaylistView.getSelectionModel().getSelectedItem());
          }
     }
     public void onButtonClickCreate() {

     }

}
