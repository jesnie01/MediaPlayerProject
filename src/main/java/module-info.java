module com.example.mediaplayerproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.mediaplayerproject to javafx.fxml;
    exports com.example.mediaplayerproject;
}