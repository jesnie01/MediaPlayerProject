package com.example.mediaplayerproject.controller;

import com.example.mediaplayerproject.model.Global;
import com.example.mediaplayerproject.model.SearchDB;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class CreatAndEditPlaylistController {

    @FXML
    private ListView allMediaView;

    @FXML
    private TextField nameOfPlaylist;



    @FXML
    private void onCreateButtonClick() throws SQLException { // button that creates a record in tblPlaylists
        Connection connection = com.example.mediaplayerproject.model.DBConnection.makeConnection(); // Db connection
        String InsertSQL = "INSERT INTO tblPlaylist(fldPlaylistTitle, fldPlaylistOwner) values (?, 'Unnamed Owner2')"; // SQL insert statement
        PreparedStatement insertStatement = connection.prepareStatement(InsertSQL);
        insertStatement.setString(1, nameOfPlaylist.getText()); //sets the placeholder '?' to a String from the textbox
        insertStatement.executeUpdate();

        ResultSet resultSet = SearchDB.searchPlaylists(); // select * from tblPlaylists

        while (resultSet.next()) {
                allMediaView.getItems().add(resultSet.getString(2)); //adds columnIndex:2 to the listview as String
                                                                                //columnindex:2 = fldPlaylistTitle
        }
    }
}
