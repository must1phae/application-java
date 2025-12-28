package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_ocp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion bien rétablie !!"); // [cite: 531]
            } catch (SQLException ex) {
                System.out.println("Erreur : " + ex.getMessage());
            }
        }
        return con;
    }
}