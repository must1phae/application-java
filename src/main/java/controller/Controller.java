package controller;

import implementation.GestionEmployeDB;
import models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleObjectProperty;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Champs du formulaire
    @FXML private TextField txtNom;
    @FXML private TextField txtAge;
    @FXML private TextField txtDate;     // Vérifiez que l'id est bien txtDate dans SceneBuilder
    @FXML private TextField txtValue;    // Vérifiez que l'id est bien txtValue dans SceneBuilder
    @FXML private ComboBox<String> comboProducteur; // Vérifiez l'id comboProducteur
    @FXML private Label lblSalaireMoyen;

    // TableView et colonnes
    @FXML private TableView<Employe> table;
    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colName;
    @FXML private TableColumn<Employe, String> colDate;
    @FXML private TableColumn<Employe, Double> colSalary;

    private ObservableList<Employe> employeeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation du ComboBox
        comboProducteur.getItems().addAll("VENDEUR", "REPRESENTANT", "PRODUCTEUR", "MANUTENTIONNAIRE");

        // Initialisation des colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
        // Calcul dynamique du salaire pour l'affichage
        colSalary.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().calculerSalaire()));

        // Lier la liste et charger les données
        table.setItems(employeeList);
        afficherAction();

        // Bonus : Remplir les champs quand on clique sur une ligne (Facultatif mais pratique)
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtNom.setText(newVal.getNom().replace("L'employé ", "")); // On enlève le préfixe pour l'édition
                txtAge.setText(String.valueOf(newVal.getAge()));
                txtDate.setText(newVal.getDateEntree());
                // Note : On ne peut pas facilement récupérer la "valeur" (CA/Heures) car elle n'est pas stockée
                // brute dans la classe mère, il faudrait des casts. On laisse vide ou à 0.
                txtValue.setText("0");
            }
        });
    }

    // --- 1. Méthode AJOUTER ---
    @FXML
    private void ajouterAction() {
        try {
            String nom = txtNom.getText();
            int age = Integer.parseInt(txtAge.getText());
            String date = txtDate.getText();
            double val = Double.parseDouble(txtValue.getText());
            String type = comboProducteur.getValue();

            if (type == null) throw new Exception("Veuillez sélectionner un type d'employé.");

            // cette partie sert a creer des employé selon les types
            Employe emp;
            switch (type) {
                case "VENDEUR" -> emp = new Vendeur(nom, age, date, val);
                case "REPRESENTANT" -> emp = new Representant(nom, age, date, val);
                case "PRODUCTEUR" -> emp = new Producteur(nom, age, date, (int)val);
                case "MANUTENTIONNAIRE" -> emp = new Manutentionnaire(nom, age, date, (int)val);
                default -> throw new IllegalArgumentException("Type inconnu");
            }

            // Insertion en Base de Données
            GestionEmployeDB.addEmployee(emp, type, val, false); // false = pas de risque par défaut

            // Mise à jour de l'affichage
            afficherAction();
            clearFields();

        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    //  Méthode MODIFIER  ---
    @FXML
    private void modifierAction() {
        // pour recuperer l'employe selectioné
        Employe selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un employé dans le tableau pour le modifier.");
            return;
        }

        try {

            // Logique plus simple (basée sur la sélection du tableau) :
            showAlert("Info", "Fonctionnalité Modifier à implémenter dans GestionEmployeDB (UPDATE SQL). \nID sélectionné : " + selected.getId());

        } catch (Exception e) {
            showAlert("Erreur", e.getMessage());
        }
    }

    // --- 3. Méthode SUPPRIMER ---
    @FXML
    private void supprimerAction() {
        Employe selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Appel à la base de données
            GestionEmployeDB.deleteEmployee(selected.getId());
            // Rafraîchir le tableau
            afficherAction();
        } else {
            showAlert("Attention", "Veuillez sélectionner une ligne à supprimer.");
        }
    }

    //  Méthode AFFICHER ---
    @FXML
    private void afficherAction() {
        employeeList.setAll(GestionEmployeDB.getAllEmployees());
    }

    // Méthode SALAIRE MOYEN ---
    @FXML
    private void salaireMoyenAction() {
        double moy = GestionEmployeDB.getAverageSalary();
        lblSalaireMoyen.setText(String.format("Salaire moyen: %.2f Dhs", moy));
    }

    // Méthodes utilitaires
    private void clearFields() {
        txtNom.clear();
        txtAge.clear();
        txtDate.clear();
        txtValue.clear();
        comboProducteur.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}