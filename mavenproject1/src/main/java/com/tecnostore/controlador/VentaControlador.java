package com.tecnostore.controlador;

import com.tecnostore.modelo.Cliente;
import com.tecnostore.servicio.GestorCelulares;
import com.tecnostore.servicio.GestorClientes;
import com.tecnostore.servicio.GestorVentas;
import com.tecnostore.vista.VentaVista;
import java.sql.SQLException;
import java.util.List;
import com.tecnostore.modelo.Celular;
import com.tecnostore.modelo.ItemVenta;
import java.util.ArrayList;
import com.tecnostore.modelo.Venta;

public class VentaControlador {

    private final GestorVentas gestorVentas;
    private final GestorClientes gestorClientes;
    private final GestorCelulares gestorCelulares;
    private final VentaVista vista;

    public VentaControlador(
            GestorVentas gestorVentas,
            GestorClientes gestorClientes,
            GestorCelulares gestorCelulares,
            VentaVista vista
    ) {
        this.gestorVentas = gestorVentas;
        this.gestorClientes = gestorClientes;
        this.gestorCelulares = gestorCelulares;
        this.vista = vista;
    }

    private Cliente seleccionarCliente() throws SQLException {
        List<Cliente> clientes = gestorClientes.listar();

        if (clientes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debes registrar un cliente antes de realizar una venta."
            );
        }

        vista.mostrarClientes(clientes);
        int id = vista.pedirIdCliente();

        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }

        throw new IllegalArgumentException(
                "No existe un cliente con ese ID."
        );
    }
    private List<ItemVenta> seleccionarItems() throws SQLException {
    List<Celular> celulares = gestorCelulares.listar();
    List<ItemVenta> items = new ArrayList<>();

    if (celulares.isEmpty()) {
        throw new IllegalArgumentException(
                "No hay celulares registrados."
        );
    }

    vista.mostrarCelulares(celulares);

    while (true) {
        int id = vista.pedirIdCelular();

        if (id == 0) {
            break;
        }

        Celular seleccionado = null;

        for (Celular celular : celulares) {
            if (celular.getId() == id) {
                seleccionado = celular;
                break;
            }
        }

        if (seleccionado == null) {
            vista.mostrarMensaje("No existe un celular con ese ID.");
            continue;
        }

        boolean agregado = false;

        for (ItemVenta item : items) {
            if (item.getCelular().getId() == id) {
                agregado = true;
                break;
            }
        }

        if (agregado) {
            vista.mostrarMensaje(
                    "Ese celular ya fue agregado a la venta."
            );
            continue;
        }

        int cantidad = vista.pedirCantidad();

        if (cantidad <= 0) {
            vista.mostrarMensaje(
                    "La cantidad debe ser mayor que cero."
            );
            continue;
        }

        if (cantidad > seleccionado.getStock()) {
            vista.mostrarMensaje(
                    "Stock insuficiente. Disponible: "
                    + seleccionado.getStock()
            );
            continue;
        }

        items.add(new ItemVenta(seleccionado, cantidad));
        vista.mostrarMensaje("Celular agregado.");
    }

    if (items.isEmpty()) {
        throw new IllegalArgumentException(
                "La venta debe incluir al menos un celular."
        );
    }

    return items;
}
    public void registrar() {
    try {
        Cliente cliente = seleccionarCliente();
        List<ItemVenta> items = seleccionarItems();

        Venta venta = new Venta(cliente, items);

        vista.mostrarResumen(venta);

        if (!vista.confirmarVenta()) {
            vista.mostrarMensaje("Venta cancelada.");
            return;
        }

        int idVenta = gestorVentas.registrar(venta);

        vista.mostrarMensaje(
                "Venta registrada correctamente. ID: " + idVenta
        );

    } catch (IllegalArgumentException e) {
        vista.mostrarMensaje("Datos invalidos: " + e.getMessage());
    } catch (SQLException e) {
        vista.mostrarMensaje(
                "No se pudo registrar la venta: " + e.getMessage()
        );
    }
}
}