package dev.alan20111.monitorpresionarterial.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class c_dbConnection {
    private static c_dbConnection instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/monitor_presion_db";
    private final String USER = "root";
    private final String PASSWORD = "root";

    private c_dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static c_dbConnection getInstance() {
        if (instance == null) {
            instance = new c_dbConnection();
        } else {
            try {
                if (instance.getConnection().isClosed()) {
                    instance = new c_dbConnection();
                }
            } catch (SQLException e) {
                System.err.println("Error al verificar el estado de la conexión: " + e.getMessage());
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}