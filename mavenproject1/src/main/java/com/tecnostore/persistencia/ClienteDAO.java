package com.tecnostore.persistencia;

import com.tecnostore.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    public void registrar(Cliente cliente) throws SQLException {
        String sql = """
                INSERT INTO clientes
                (nombre, identificacion, correo, telefono)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getIdentificacion());
            sentencia.setString(3, cliente.getCorreo());
            sentencia.setString(4, cliente.getTelefono());

            sentencia.executeUpdate();
        }
    }
}