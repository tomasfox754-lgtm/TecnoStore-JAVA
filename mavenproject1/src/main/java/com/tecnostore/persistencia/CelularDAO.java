package com.tecnostore.persistencia;

import com.tecnostore.modelo.CategoriaGama;
import com.tecnostore.modelo.Celular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CelularDAO {

    public void registrar(Celular celular) throws SQLException {
        String sql = """
                INSERT INTO celulares
                (marca, modelo, sistema_operativo, gama, precio, stock)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, celular.getMarca());
            sentencia.setString(2, celular.getModelo());
            sentencia.setString(3, celular.getSistemaOperativo());
            sentencia.setString(4, celular.getGama().name());
            sentencia.setBigDecimal(5, celular.getPrecio());
            sentencia.setInt(6, celular.getStock());

            sentencia.executeUpdate();
        }
    }

    public List<Celular> listar() throws SQLException {
        List<Celular> celulares = new ArrayList<>();

        String sql = """
                SELECT id, marca, modelo, sistema_operativo,
                       gama, precio, stock
                FROM celulares
                ORDER BY id
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Celular celular = new Celular(
                        resultado.getInt("id"),
                        resultado.getString("marca"),
                        resultado.getString("modelo"),
                        resultado.getBigDecimal("precio"),
                        resultado.getInt("stock"),
                        resultado.getString("sistema_operativo"),
                        CategoriaGama.valueOf(
                                resultado.getString("gama")
                        )
                );

                celulares.add(celular);
            }
        }

        return celulares;
    }
}