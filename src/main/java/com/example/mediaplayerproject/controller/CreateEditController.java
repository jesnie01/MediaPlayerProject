package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.*;
import com.example.mediaplayerproject.model.GlobalInfo;
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

public class CreateEditController {
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

     public void btnRefresh() throws SQLException {
          refreshAllPlaylists();
     }
     @FXML
     public void selectPlaylist(MouseEvent mouseEvent) throws SQLException {
          currentPlaylistView.getItems().clear(); // clears selected playlist view to avoid duplicating results.
          if (allPlaylistsView.getSelectionModel().getSelectedItem() != null) {
               //sets name to the selected playlist
               nameOfPlaylist.setText(allPlaylistsView.getSelectionModel().selectedItemProperty().getValue().toString());
               //Executes an SQL query based on the selected item in list, and stores it in resultSet
               ResultSet resultSet = SearchDB.searchMediaInPlaylist(allPlaylistsView.getSelectionModel().selectedItemProperty().getValue().toString());
               GlobalInfo.playlistMedia.clear(); //clears arraylist containing the medias in selected playlist
               while (resultSet.next()) {
                    for (int i = 0; i < GlobalInfo.allMedia.size(); i++) {//repeats body of loops based on the size of the allMedia list
                         //if the mediatitle found in all available media match the medias found in the sql query, adds them to playlistMedia
                         if(resultSet.getString(5).equals(GlobalInfo.allMedia.get(i).getMediaTitle().toString())){
                              GlobalInfo.playlistMedia.add(GlobalInfo.allMedia.get(i));
                         }
                    }

               }
               //displays playlistMedia in currentPlaylistView
               for (int i = 0; i < GlobalInfo.playlistMedia.size(); i++) {
                    currentPlaylistView.getItems().add(GlobalInfo.playlistMedia.get(i).getMediaTitle());
               }
          }
     }


     public void btnSearch() throws SQLException { // button that searches for media
          ResultSet resultSet = SearchDB.searchMedia();
          allMediaView.getItems().clear();
          while (resultSet.next()) {
               //Partial search, medias in all available medias contain anything in the searchbox, prints it out
               if (resultSet.getString(2).toLowerCase().contains(searchField.getText())) {
                    allMediaView.getItems().add(resultSet.getString(2));
               }
          }

     }
     public void btnAdd() throws SQLException { // button that adds whats selected in mediaview, into currentplaylistview
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
     public void btnRemove() throws SQLException { // button that removes whats selected in playlist view
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
     private void btnCreate() throws SQLException { // button that creates a record in tblPlaylists
          ResultSet resultSet = SearchDB.searchPlaylists();

          boolean isInResultSet = false;

          while (resultSet.next()) {
               if (resultSet.getString(2).equals(nameOfPlaylist.getText()) && resultSet.getString(3).equals(GlobalInfo.User)) {
                    isInResultSet = true;
                    break;
               }
          }
          if (!isInResultSet) {
               createPlaylist();
               addMediaToNewPlaylist();
               refreshAllPlaylists();
          }
     }
     @FXML
     private void btnDelete() throws SQLException { // button that deletes record from playlists
          deletePlaylist();
          refreshAllPlaylists();
          currentPlaylistView.getItems().clear();
          nameOfPlaylist.setText(null);
     }

     /**
      *
      * @param nameOfMedia - a media title
      * @param IdOfPlaylist - an ID of a playlist from DB
      * @throws SQLException
      */
     private void addToPlaylist(String nameOfMedia, int IdOfPlaylist) throws SQLException {
          for (int i = 0; i < GlobalInfo.allMedia.size(); i++) {
               if (GlobalInfo.allMedia.get(i).getMediaTitle().equals(nameOfMedia)) {
                    Connection connection = DBConnection.makeConnection();
                    String insertSQL = "INSERT INTO tblMediaPlaylist(fldMediaId, fldPlaylistId) values (?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                    preparedStatement.setInt(1, GlobalInfo.allMedia.get(i).getMediaId());
                    preparedStatement.setInt(2, IdOfPlaylist);
                    preparedStatement.executeUpdate();
               }
          }
     }

     /**
      *
      * @param MediaPlaylistId - an ID of a selected playlist
      * @throws SQLException
      */
     private void removeFromPlaylist(int MediaPlaylistId) throws SQLException {
          Connection connection = DBConnection.makeConnection();
          String deleteSQL = "DELETE FROM tblMediaPlaylist WHERE (fldMediaPlaylistId = ?)";
          PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
          preparedStatement.setInt(1,MediaPlaylistId);
          preparedStatement.executeUpdate();
     }

     /**
      * creates a record for playlist
      * @throws SQLException
      */
     private void createPlaylist() throws SQLException {
          Connection connection = DBConnection.makeConnection(); // Db connection
          String InsertSQL = "INSERT INTO tblPlaylist(fldPlaylistTitle, fldPlaylistOwner) values (?, ?)"; // SQL insert statement
          PreparedStatement insertStatement = connection.prepareStatement(InsertSQL);
          insertStatement.setString(1, nameOfPlaylist.getText()); //sets the placeholder '?' to a String from the textbox
          insertStatement.setString(2, GlobalInfo.User);
          insertStatement.executeUpdate();
     }

     /**
      * adds media to a newly created playlist
      * @throws SQLException
      */
     private void addMediaToNewPlaylist() throws SQLException {
          ResultSet resultSet = SearchDB.searchPlaylists();
          int tempPlaylistId = -1;
          while (resultSet.next()) {
               if (resultSet.getString(2).equals(nameOfPlaylist.getText())) {
                    tempPlaylistId = resultSet.getInt(1);
               }
          }
          for (int i = 0; i < currentPlaylistView.getItems().size(); i++) {
               addToPlaylist(currentPlaylistView.getItems().get(i).toString(), tempPlaylistId);
          }
     }

     /**
      * refreshes the list of playlists
      * @throws SQLException
      */
     private void refreshAllPlaylists() throws SQLException {
          allPlaylistsView.getItems().clear();
          allMediaView.getItems().clear();
          ResultSet resultSet = SearchDB.searchPlaylists();
          while (resultSet.next()) {
               allPlaylistsView.getItems().add(resultSet.getString(2));
          }
          for (int i = 0; i < GlobalInfo.allMedia.size(); i++) {
               allMediaView.getItems().add(GlobalInfo.allMedia.get(i).getMediaTitle());
          }
     }

     /**
      * Deletes record for playlist
      * @throws SQLException
      */
     private void deletePlaylist() throws SQLException {
          Connection connection = com.example.mediaplayerproject.model.DBConnection.makeConnection();
          String deleteSQL = "DELETE FROM tblPlayList WHERE fldPlaylistTitle = ?";
          PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
          deleteStatement.setString(1, (String) allPlaylistsView.getSelectionModel().getSelectedItem());
          deleteStatement.executeUpdate();
     }
}
