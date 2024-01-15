package com.example.mediaplayerproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewController {

    @FXML
    private AnchorPane dynamicView;
    @FXML
    private void SwitchToView1() {
        loadView("MainLayout.fxml");
    }

    @FXML
    private void SwitchToView2() {
        loadView("viewPlaylists-view.fxml");
    }

    private void loadView(String fxmlFile) {

        try {
            System.out.println("Loading view: " + fxmlFile);
            AnchorPane newView = FXMLLoader.load(getClass().getResource(fxmlFile));
            dynamicView.getChildren().removeAll();
            dynamicView.getChildren().setAll(newView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}