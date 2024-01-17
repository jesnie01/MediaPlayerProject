package com.example.mediaplayerproject.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public class Global {

    public static ArrayList<MediaInfo> allMedia = new ArrayList<>();

    public static ArrayList<String> playlistMedia = new ArrayList<>();

    public static int currentIndexOfMediaInPlaylist = 0;

}
