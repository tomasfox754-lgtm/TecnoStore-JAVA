package com.tecnostore.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class Venta {

    private static final BigDecimal TASA_IVA =
            new BigDecimal("0.19");

    private final Cliente cliente;
    private final LocalDateTime fecha;
    private final List<ItemVenta> items;

    public Venta(Cliente cliente, List<ItemVenta> items) {
        if (cliente == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un cliente registrado."
            );
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException(
                    "La venta debe tener al menos un item."
            );
        }

        for (ItemVenta item : items) {
            if (item == null || item.getCelular().getId() <= 0) {
                throw new IllegalArgumentException(
                        "Cada item debe tener un celular registrado."
                );
            }
        }

        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.items = List.copyOf(items);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(ItemVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getIva() {
        return getSubtotal()
                .multiply(TASA_IVA)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return getSubtotal()
                .add(getIva())
                .setScale(2, RoundingMode.HALF_UP);
    }
}