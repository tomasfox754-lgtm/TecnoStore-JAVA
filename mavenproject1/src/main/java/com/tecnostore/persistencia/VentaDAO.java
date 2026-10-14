package com.tecnostore.persistencia;

import com.tecnostore.modelo.ItemVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.tecnostore.modelo.Venta;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class VentaDAO {
public int registrar(Venta venta) throws SQLException {
    String sqlVenta = """
            INSERT INTO ventas (id_cliente, fecha, total)
            VALUES (?, ?, ?)
            """;

    String sqlDetalle = """
            INSERT INTO detalle_ventas
            (id_venta, id_celular, cantidad, subtotal)
            VALUES (?, ?, ?, ?)
            """;

    try (Connection conexion = ConexionDB.conectar()) {
        conexion.setAutoCommit(false);

        try {
            int idVenta;

            try (PreparedStatement sentencia = conexion.prepareStatement(
                    sqlVenta, Statement.RETURN_GENERATED_KEYS
            )) {
                sentencia.setInt(1, venta.getCliente().getId());
                sentencia.setTimestamp(
                        2, Timestamp.valueOf(venta.getFecha())
                );
                sentencia.setBigDecimal(3, venta.getTotal());

                sentencia.executeUpdate();

                try (ResultSet claves = sentencia.getGeneratedKeys()) {
                    if (!claves.next()) {
                        throw new SQLException(
                                "No se pudo obtener el ID de la venta."
                        );
                    }

                    idVenta = claves.getInt(1);
                }
            }

            try (PreparedStatement sentencia =
                         conexion.prepareStatement(sqlDetalle)) {

                for (ItemVenta item : venta.getItems()) {
                    descontarStock(conexion, item);

                    sentencia.setInt(1, idVenta);
                    sentencia.setInt(2, item.getCelular().getId());
                    sentencia.setInt(3, item.getCantidad());
                    sentencia.setBigDecimal(4, item.getSubtotal());

                    sentencia.executeUpdate();
                }
            }

            conexion.commit();
            return idVenta;

        } catch (SQLException | RuntimeException e) {
            try {
                conexion.rollback();
            } catch (SQLException errorRollback) {
                e.addSuppressed(errorRollback);
            }

            throw e;
        }
    }
}   
    private void descontarStock(
            Connection conexion, ItemVenta item
    ) throws SQLException {

        String sql = """
                UPDATE celulares
                SET stock = stock - ?
                WHERE id = ? AND stock >= ?
                """;

        try (PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setInt(1, item.getCantidad());
            sentencia.setInt(2, item.getCelular().getId());
            sentencia.setInt(3, item.getCantidad());

            int filasActualizadas = sentencia.executeUpdate();

            if (filasActualizadas == 0) {
                throw new SQLException(
                        "El celular no existe o no tiene stock suficiente: "
                        + item.getCelular().getId()
                );
            }
        }
    }
}