package com.example.mediaplayerproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
public class CreatAndEditPlaylistController {
    @FXML private static ListView allMediaView;
    @FXML private static ListView allPlaylistsView;
    @FXML private static ListView allPlaylistsView1;
    @FXML private static AnchorPane Anchorpane;


    public void initialize(URL location, ResourceBundle resources) {
        allMediaView.prefWidthProperty().bind(Anchorpane.widthProperty().divide(3));
        allPlaylistsView.prefWidthProperty().bind(Anchorpane.widthProperty().divide(3));
        allPlaylistsView1.prefWidthProperty().bind(Anchorpane.widthProperty().divide(3));
        allMediaView.prefHeightProperty().bind(Anchorpane.heightProperty());
        allPlaylistsView.prefHeightProperty().bind(Anchorpane.heightProperty());
        allPlaylistsView1.prefHeightProperty().bind(Anchorpane.heightProperty());
    }

}
