module com.example.mediaplayerproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;


    opens com.example.mediaplayerproject to javafx.fxml;
    exports com.example.mediaplayerproject;
}