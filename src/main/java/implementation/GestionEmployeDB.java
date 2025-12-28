package implementation;

import connection.DBConnection;
import models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionEmployeDB {

    // [cite: 573] Méthode Ajouter
    public static void addEmployee(Employe e, String type, double value, boolean risk) {
        String sql = "INSERT INTO Employe (nom, age, date_entree, type_employe, valeur, salaire, a_risque) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, e.getNom());
            stmt.setInt(2, e.getAge());
            stmt.setString(3, e.getDateEntree());
            stmt.setString(4, type);
            stmt.setDouble(5, value);
            stmt.setDouble(6, e.calculerSalaire());
            stmt.setBoolean(7, risk);
            stmt.executeUpdate();
            System.out.println("Employee added successfully: " + e.getNom());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // [cite: 611] Méthode Récupérer Tout
    public static List<Employe> getAllEmployees() {
        List<Employe> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employe";
        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int age = rs.getInt("age");
                String date = rs.getString("date_entree");
                String type = rs.getString("type_employe");
                double val = rs.getDouble("valeur");
                boolean risk = rs.getBoolean("a_risque");

                Employe e = createEmployeFromType(id, nom, age, date, type, val, risk);
                if(e != null) employees.add(e);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return employees;
    }

    // [cite: 721] Méthode Supprimer
    public static void deleteEmployee(int id) {
        String sql = "DELETE FROM Employe WHERE id=?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Employee deleted successfully (ID: " + id + ")");
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    // [cite: 757] Méthode Salaire Moyen
    public static double getAverageSalary() {
        String sql = "SELECT AVG(salaire) AS moyenne FROM Employe";
        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble("moyenne");
        } catch (SQLException ex) { ex.printStackTrace(); }
        return 0.0;
    }

    // [cite: 797] Méthode Utilitaire pour créer l'objet
    private static Employe createEmployeFromType(int id, String nom, int age, String date, String type, double value, boolean risk) {
        switch (type.toUpperCase()) {
            case "VENDEUR": return new Vendeur(id, nom, age, date, value);
            case "REPRESENTANT": return new Representant(id, nom, age, date, value);
            case "PRODUCTEUR": return risk ? new ProdARisque(id, nom, age, date, (int)value) : new Producteur(id, nom, age, date, (int)value);
            case "MANUTENTIONNAIRE": return risk ? new ManutARisque(id, nom, age, date, (int)value) : new Manutentionnaire(id, nom, age, date, (int)value);
            default: return null;
        }
    }
}