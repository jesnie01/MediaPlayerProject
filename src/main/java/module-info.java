module com.example.mediaplayerproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.mediaplayerproject to javafx.fxml;
    exports com.example.mediaplayerproject;
    exports com.example.mediaplayerproject.model;
    opens com.example.mediaplayerproject.model to javafx.fxml;
    exports com.example.mediaplayerproject.controller;
    opens com.example.mediaplayerproject.controller to javafx.fxml;
}