package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.*;
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
import java.sql.*;
import java.util.ArrayList;

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
     public void onButtonClickAdd() throws SQLException {
          if (allMediaView.getSelectionModel().getSelectedItem() != null) {
               String tempSelectedItem = allMediaView.getSelectionModel().selectedItemProperty().getValue().toString();
               int tempSelectedPlaylist = -1;
               currentPlaylistView.getItems().add(tempSelectedItem);
               ResultSet resultSet = SearchDB.searchPlaylists();
               while (resultSet.next()) {
                    if (resultSet.getString(2).equals(nameOfPlaylist.getText())) {
                         tempSelectedPlaylist = resultSet.getInt(1);
                         addToPlaylist(tempSelectedItem, tempSelectedPlaylist);
                         break;
                    }
               }
          }
     }
     public void onButtonClickRemove() throws SQLException {
          if (currentPlaylistView.getSelectionModel().getSelectedItem() != null) {
               String tempSelectedItem = currentPlaylistView.getSelectionModel().selectedItemProperty().getValue().toString();
               int tempIndexOfSelectedMedia = currentPlaylistView.getItems().indexOf(tempSelectedItem);
               ArrayList<Integer> tempResults = new ArrayList<>();
               currentPlaylistView.getItems().remove(currentPlaylistView.getSelectionModel().getSelectedItem());
               System.out.println(tempIndexOfSelectedMedia);
               ResultSet resultSet = SearchDB.searchMediaInPlaylist(nameOfPlaylist.getText());
               while (resultSet.next()) {
                    tempResults.add(resultSet.getInt(1));
               }
               removeFromPlaylist(tempResults.get(tempIndexOfSelectedMedia));
          }
     }
     @FXML
     private void onButtonClickCreate() throws SQLException { // button that creates a record in tblPlaylists
          ResultSet resultSet = SearchDB.searchPlaylists();

          boolean isInResultSet = false;

          while (resultSet.next()) {
               if (resultSet.getString(2).equals(nameOfPlaylist.getText()) && resultSet.getString(3).equals(Global.User)) {
                    isInResultSet = true;
                    break;
               }
          }
          if (!isInResultSet) {
               Connection connection = DBConnection.makeConnection(); // Db connection
               String InsertSQL = "INSERT INTO tblPlaylist(fldPlaylistTitle, fldPlaylistOwner) values (?, ?)"; // SQL insert statement
               PreparedStatement insertStatement = connection.prepareStatement(InsertSQL);
               insertStatement.setString(1, nameOfPlaylist.getText()); //sets the placeholder '?' to a String from the textbox
               insertStatement.setString(2, Global.User);
               insertStatement.executeUpdate();
          }




//          ResultSet resultSet = SearchDB.searchPlaylists(); // select * from tblPlaylists
//
//          while (resultSet.next()) { // while column has anything at all
//               allMediaView.getItems().add(resultSet.getString(2)); //adds columnIndex:2 to the listview as String
//               //columnindex:2 = fldPlaylistTitle
//          }
     }
     private void addToPlaylist(String nameOfMedia, int IdOfPlaylist) throws SQLException {
          for (int i = 0; i < Global.allMedia.size(); i++) {
               if (Global.allMedia.get(i).getMediaTitle().equals(nameOfMedia)) {
                    Connection connection = DBConnection.makeConnection();
                    String insertSQL = "INSERT INTO tblMediaPlaylist(fldMediaId, fldPlaylistId) values (?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                    preparedStatement.setInt(1, Global.allMedia.get(i).getMediaId());
                    preparedStatement.setInt(2, IdOfPlaylist);
                    preparedStatement.executeUpdate();
               }
               //return (Global.allMedia.get(i).getMediaTitle().equals(nameOfMedia) ? -1 : Global.allMedia.get(i).getMediaId());
          }
     }
     private void removeFromPlaylist(int MediaPlaylistId) throws SQLException {
          Connection connection = DBConnection.makeConnection();
          String deleteSQL = "DELETE FROM tblMediaPlaylist WHERE (fldMediaPlaylistId = ?)";
          PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
          preparedStatement.setInt(1,MediaPlaylistId);
          preparedStatement.executeUpdate();
     }

}
