package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException; // Import obligatoire pour l'erreur

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // --- C'est ICI que doit se trouver le code de chargement ---

        // Assurez-vous que interface.fxml est bien à la racine du dossier 'resources'
        Parent root = FXMLLoader.load(getClass().getResource("/interface.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Gestion des Employés");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}