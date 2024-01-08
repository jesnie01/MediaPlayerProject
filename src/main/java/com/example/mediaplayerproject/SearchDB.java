package com.example.mediaplayerproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchDB {
    public ArrayList<String> searchComplete(String searchText, boolean toggle) throws SQLException {
        ArrayList<String> tempArrayList = new ArrayList<>();
        ArrayList<String> sortArrayList = new ArrayList<>();
        ArrayList<String> sortedMediaArrayList = new ArrayList<>();


        if (!toggle) {
            Connection connection = DBConnection.getDbConnection().makeConnection();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblMediaInfo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString(2));
            }

            for (String s : tempArrayList) {
                if (s.equals(searchText)) {
                    sortArrayList.add(s);
                }
            }
            return sortArrayList;
        }else {
            Connection connection = DBConnection.getDbConnection().makeConnection();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblCreator");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString(2));
            }

            for (String s : tempArrayList) {
                if (s.equals(searchText)) {
                    sortArrayList.add(s);
                }
            }

            for (String s : sortArrayList) {
                String sqlQuery = "Select * from tblCreatorMedia inner join tblMediaInfo on tblCreatorMedia.fldMediaId = tblMediaInfo.fldMediaId where fldCreatorId = (SELECT fldCreatorId from tblCreator where fldCreatorName = " + s;
                preparedStatement = connection.prepareCall(sqlQuery);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    sortedMediaArrayList.add(resultSet.getString(5));
                }
            }
            return sortedMediaArrayList;
        }
    }
    public ArrayList<String> searchPartial(String searchText, boolean toggle) throws SQLException {
        ArrayList<String> tempArrayList = new ArrayList<>();
        ArrayList<String> sortArrayList = new ArrayList<>();

        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblMediaInfo");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            tempArrayList.add(resultSet.getString(2));
        }

        for (String s : tempArrayList) {
            if (s.contains(searchText)) {
                sortArrayList.add(s);
            }
        }
        return sortArrayList;
    }
}
