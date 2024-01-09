package com.example.mediaplayerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class  HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView mediaList;
    @FXML
    private TextField searchBox;
    @FXML
    private RadioButton searchToggleArtist;
    @FXML
    private RadioButton searchToggleFilename;

    SearchDB searchDB = new SearchDB();

    @FXML
    protected void onButtonHelloClick() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT fldMediaTitle FROM tblMediaInfo");
        ResultSet resultSet = preparedStatement.executeQuery();

        mediaList.getItems().clear(); // Stops the list from duplicating itself every click
        while(resultSet.next()) {
            String tempString = resultSet.getString(1); // gets everything from fldMediaTitle as String values
            mediaList.getItems().add(tempString); // gets elements from ListView and replaces them with fldMediaTitle
        }

        connection.close();
    }
    @FXML
    protected void onButtonSearchClick() throws SQLException {
        boolean searchToggleBool = searchToggleArtist.isSelected();
        //Checks the searchbox on button click for a complete artist name
        ArrayList<String> searchedList = searchDB.searchComplete(searchBox.getText(),searchToggleBool);
        mediaList.getItems().clear(); // makes sure the ListView is empty, before displaying elements
        //if found, adds them to an Arraylist and displays it in a ListView
        for (String s : searchedList) {
            mediaList.getItems().add(s);
        }
    }
    @FXML
    protected void onButtonPartialSearchClick() throws SQLException {
        boolean searchToggleBool = searchToggleArtist.isSelected();
        //Checks the searchbox on button click for a partial match in artist name
        ArrayList<String> searchedList = searchDB.searchPartial(searchBox.getText(),searchToggleBool);
        mediaList.getItems().clear();
        //if found, adds them to an ArrayList and displays it in a ListView
        for (String s : searchedList) {
            mediaList.getItems().add(s);
        }
    }
}