package com.tecnostore.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class ReporteDAO {

    public record DetalleVendido(
            int idCelular,
            String marca,
            String modelo,
            int cantidad
    ) {
    }

    public List<DetalleVendido> listarDetallesVendidos()
            throws SQLException {

        List<DetalleVendido> detalles = new ArrayList<>();

        String sql = """
                SELECT c.id AS id_celular,
                       c.marca, c.modelo, d.cantidad
                FROM detalle_ventas d
                INNER JOIN celulares c ON c.id = d.id_celular
                ORDER BY d.id
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                detalles.add(new DetalleVendido(
                        resultado.getInt("id_celular"),
                        resultado.getString("marca"),
                        resultado.getString("modelo"),
                        resultado.getInt("cantidad")
                ));
            }
        }

        return detalles;
    }
    public record VentaResumen(
        int id,
        String cliente,
        LocalDateTime fecha,
        BigDecimal total
) {
}
    public List<VentaResumen> listarVentas() throws SQLException {
    List<VentaResumen> ventas = new ArrayList<>();

    String sql = """
            SELECT v.id, c.nombre AS cliente, v.fecha, v.total
            FROM ventas v
            INNER JOIN clientes c ON c.id = v.id_cliente
            ORDER BY v.fecha, v.id
            """;

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement sentencia =
                 conexion.prepareStatement(sql);
         ResultSet resultado = sentencia.executeQuery()) {

        while (resultado.next()) {
            ventas.add(new VentaResumen(
                    resultado.getInt("id"),
                    resultado.getString("cliente"),
                    resultado.getTimestamp("fecha").toLocalDateTime(),
                    resultado.getBigDecimal("total")
            ));
        }
    }

    return ventas;
}
}