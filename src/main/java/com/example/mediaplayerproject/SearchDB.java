package com.example.mediaplayerproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchDB {
    //ArrayList method for complete search button, in order to store data
    public ArrayList<String> searchComplete(String searchText, boolean toggle) throws SQLException {
        ArrayList<String> tempArrayList = new ArrayList<>();
        ArrayList<String> sortArrayList = new ArrayList<>();
        ArrayList<String> sortedMediaArrayList = new ArrayList<>();


        if (!toggle) { // if toggle media is selected, makes connection to DB and executes the following sql query
            Connection connection = DBConnection.getDbConnection().makeConnection();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblMediaInfo");
            ResultSet resultSet = preparedStatement.executeQuery();
            //selects everything from tblMediaInfo and stores it in a temporary ArrayList
            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString(2));
            }
            //sorts through the temporary ArrayList and compares it with what the user types in the searchbox
                // in order to find a match
            for (String s : tempArrayList) {
                if (s.equalsIgnoreCase(searchText)) {
                    sortArrayList.add(s);
                }
            }
            return sortArrayList; // returns any matches found
        }else {
            // else, if toggle creator is selected, does the same thing mentioned above
            Connection connection = DBConnection.getDbConnection().makeConnection();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM tblCreator");
            ResultSet resultSet = preparedStatement.executeQuery();
            //adds to temporary arraylist
            while (resultSet.next()) {
                tempArrayList.add(resultSet.getString(2));
            }
            //sorts and compares
            for (String s : tempArrayList) {
                if (s.equalsIgnoreCase(searchText)) {
                    sortArrayList.add(s);
                }
            }
            //for every artists found in the sorted Arraylist, adds media associated with matched artist to a sortedMediaArrayList
              //with an SQL innerjoin and subquery below:
            for (String s : sortArrayList) {
                String sqlQuery = "Select * from tblCreatorMedia inner join tblMediaInfo on tblCreatorMedia.fldMediaId = tblMediaInfo.fldMediaId where fldCreatorId = (SELECT fldCreatorId from tblCreator where fldCreatorName = '" + s + "')";
                preparedStatement = connection.prepareCall(sqlQuery);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    sortedMediaArrayList.add(resultSet.getString(5));
                }
            }
            return sortedMediaArrayList; // returns media associated with artist that was typed in
        }
    }
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
            return sortedMediaArrayList;
        }
    }
}
