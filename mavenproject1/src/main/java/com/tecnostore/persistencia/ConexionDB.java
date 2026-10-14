package com.tecnostore.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionDB {

    private static final ConexionDB INSTANCIA = new ConexionDB();

    private final String url;
    private final String usuario;
    private final String password;

    private ConexionDB() {
        url = "jdbc:mysql://localhost:3306/tecnostore_db";
        usuario = System.getenv("DB_USER");
        password = System.getenv("DB_PASSWORD");
    }

    public static ConexionDB getInstancia() {
        return INSTANCIA;
    }

    public static Connection conectar() throws SQLException {
        return getInstancia().abrirConexion();
    }

    private Connection abrirConexion() throws SQLException {
        if (usuario == null || password == null) {
            throw new SQLException(
                    "Debes configurar DB_USER y DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(url, usuario, password);
    }

    public static void main(String[] args) {
        ConexionDB primera = ConexionDB.getInstancia();
        ConexionDB segunda = ConexionDB.getInstancia();

        System.out.println(
                "Misma instancia: " + (primera == segunda)
        );

        try (Connection conexion = ConexionDB.conectar()) {
            if (conexion.isValid(5)) {
                System.out.println("Conexion con MySQL exitosa.");
            } else {
                System.out.println("No se pudo validar la conexion.");
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error de conexion: " + e.getMessage()
            );
        }
    }
}   