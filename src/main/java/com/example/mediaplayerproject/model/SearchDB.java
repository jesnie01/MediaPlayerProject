package com.example.mediaplayerproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchDB {
    //ArrayList method for partial search button for data storage
    public ArrayList<String> searchPartial(String searchText, boolean toggle) throws SQLException {
        ArrayList<String> tempArrayList = new ArrayList<>();
        ArrayList<String> sortArrayList = new ArrayList<>();
        ArrayList<String> sortedMediaArrayList = new ArrayList<>();

        if (!toggle) { // if toggle media is selected connects to DB and executes sql query:
            Connection connection = DBConnection.getDbConnection().makeConnection();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblMediaInfo");
            ResultSet resultSet = preparedStatement.executeQuery();
            //selects everything from tblMediaInfo and stores it in a temporary ArrayList
            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString(2));
            }
            //sorts through the temporary ArrayList and compares it with what the user types in the searchbox
                // in order to find a matching media file containing anything the user types in.
            for (String s : tempArrayList) {
                if (s.toLowerCase().contains(searchText.toLowerCase())) {
                    sortArrayList.add(s);
                }
            }
            if (sortArrayList.isEmpty()) {
                sortArrayList.add("Nothing found");
            }
            return sortArrayList;
        }else { // same thing with creator
            Connection connection = DBConnection.getDbConnection().makeConnection();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblCreator");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString(2));
            }

            for (String s : tempArrayList) {
                if (s.toLowerCase().contains(searchText.toLowerCase())) {
                    sortArrayList.add(s);
                }
            }
            for (String s : sortArrayList) {
                String sqlQuery = "Select * from tblCreatorMedia inner join tblMediaInfo on tblCreatorMedia.fldMediaId = tblMediaInfo.fldMediaId where fldCreatorId = (SELECT fldCreatorId from tblCreator where fldCreatorName = '" + s + "')";
                preparedStatement = connection.prepareCall(sqlQuery);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (!sortedMediaArrayList.contains(resultSet.getString(5))) {
                        sortedMediaArrayList.add(resultSet.getString(5));
                    }
                }
            }
            if (sortedMediaArrayList.isEmpty()) {
                sortedMediaArrayList.add("Nothing found");
            }
            return sortedMediaArrayList;
        }
    }

    public static ResultSet searchMedia() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblMediaInfo");
        return preparedStatement.executeQuery();
    }
    public static ResultSet searchCreators() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblCreator");
        return preparedStatement.executeQuery();
    }
    public static ResultSet searchPlaylists() throws SQLException {
        Connection connection = DBConnection.getDbConnection().makeConnection();
        PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblPlaylist");
        return preparedStatement.executeQuery();
    }
}
