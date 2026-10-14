package com.tecnostore.servicio;

import com.tecnostore.modelo.Venta;
import com.tecnostore.persistencia.VentaDAO;
import java.math.BigDecimal;
import java.sql.SQLException;

public class GestorVentas {

    private final VentaDAO ventaDAO = new VentaDAO();

    public int registrar(Venta venta) throws SQLException {
        if (venta == null) {
            throw new IllegalArgumentException(
                    "Debes proporcionar una venta."
            );
        }

        BigDecimal total = venta.getTotal();

        if (total.signum() <= 0) {
            throw new IllegalArgumentException(
                    "El total de la venta debe ser mayor que cero."
            );
        }

        if (total.compareTo(new BigDecimal("99999999.99")) > 0) {
            throw new IllegalArgumentException(
                    "El total supera el limite permitido de 99999999.99."
            );
        }

        return ventaDAO.registrar(venta);
    }
}   