module gestion.salaires {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Ouvre les packages à JavaFX
    opens application to javafx.fxml;
    opens controller to javafx.fxml;
    opens models to javafx.base; // Pour le TableView

    exports application;
}