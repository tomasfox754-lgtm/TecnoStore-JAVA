package com.tecnostore.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    public static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tecnostore_db";
        String usuario = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (usuario == null || password == null) {
            throw new SQLException(
                "Debes configurar DB_USER y DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(url, usuario, password);
    }

    public static void main(String[] args) {
        try (Connection conexion = conectar()) {
            if (conexion.isValid(5)) {
                System.out.println("Conexión con MySQL exitosa.");
            } else {
                System.out.println("No se pudo validar la conexión.");
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}