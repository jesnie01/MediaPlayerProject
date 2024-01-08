package com.example.mediaplayerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onButtonHelloClick() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT fldMediaTitle FROM tblMediaInfo WHERE fldMediaId = 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        String tempString = resultSet.getString(1);

        welcomeText.setText(tempString);

        connection.close();
    }
}