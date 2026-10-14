package com.tecnostore.persistencia;

import com.tecnostore.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public List<Cliente> listar() throws SQLException {
    List<Cliente> clientes = new ArrayList<>();

    String sql = """
            SELECT id, nombre, identificacion, correo, telefono
            FROM clientes
            ORDER BY id
            """;

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement sentencia = conexion.prepareStatement(sql);
         ResultSet resultado = sentencia.executeQuery()) {

        while (resultado.next()) {
            Cliente cliente = new Cliente(
                    resultado.getInt("id"),
                    resultado.getString("nombre"),
                    resultado.getString("identificacion"),
                    resultado.getString("correo"),
                    resultado.getString("telefono")
            );

            clientes.add(cliente);
        }
    }

    return clientes;
}
}

