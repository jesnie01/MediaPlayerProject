package com.example.mediaplayerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ListView mediaList;

    @FXML
    protected void onButtonHelloClick() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT fldMediaTitle FROM tblMediaInfo");
        ResultSet resultSet = preparedStatement.executeQuery();

        mediaList.getItems().clear();
        while(resultSet.next()) {
            String tempString = resultSet.getString(1);
            mediaList.getItems().add(tempString);
        }
        connection.close();
    }

    public void onButtonPlayClick(ActionEvent actionEvent) {
    }

    public void onButtonPauseClick(ActionEvent actionEvent) {
    }

    public void onButtonStopClick(ActionEvent actionEvent) {
    }

    public void onButtonPrevClick(ActionEvent actionEvent) {
    }

    public void onButtonNextClick(ActionEvent actionEvent) {
    }
}