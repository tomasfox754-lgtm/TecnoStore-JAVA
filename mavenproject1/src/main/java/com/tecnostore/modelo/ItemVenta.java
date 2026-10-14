package com.tecnostore.modelo;

import java.math.BigDecimal;

public class ItemVenta {

    private final Celular celular;
    private final int cantidad;
    private final BigDecimal precioUnitario;

    public ItemVenta(Celular celular, int cantidad) {
        if (celular == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un celular."
            );
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }

        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = celular.getPrecio();
    }

    public Celular getCelular() {
        return celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(
                BigDecimal.valueOf(cantidad)
        );
    }
}