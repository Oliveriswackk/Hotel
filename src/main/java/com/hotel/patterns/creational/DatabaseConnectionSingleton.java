package com.hotel.patterns.creational;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Patrón Singleton para la conexión a la base de datos
 * Garantiza una única instancia de conexión en toda la aplicación
 */
public class DatabaseConnectionSingleton {
    
    private static DatabaseConnectionSingleton instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/hotel_db";
    private String username = "root";
    private String password = "root";
    
    // Constructor privado para prevenir instanciación
    private DatabaseConnectionSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al crear la conexión: " + e.getMessage());
        }
    }
    
    // Método estático para obtener la instancia única
    public static DatabaseConnectionSingleton getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionSingleton.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionSingleton();
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

