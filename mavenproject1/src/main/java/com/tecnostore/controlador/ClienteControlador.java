package com.tecnostore.controlador;

import com.tecnostore.modelo.Cliente;
import com.tecnostore.servicio.GestorClientes;
import com.tecnostore.vista.ClienteVista;
import java.sql.SQLException;

public class ClienteControlador {

    private final GestorClientes gestor;
    private final ClienteVista vista;

    public ClienteControlador(
            GestorClientes gestor, ClienteVista vista
    ) {
        this.gestor = gestor;
        this.vista = vista;
    }

    public void registrar() {
        try {
            Cliente cliente = vista.pedirDatos();
            gestor.registrar(cliente);
            vista.mostrarMensaje("Cliente registrado correctamente.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Datos invalidos: " + e.getMessage());
        } catch (SQLException e) {
            vista.mostrarMensaje(
                    "Error de base de datos: " + e.getMessage()
            );
        }
    }

    public void listar() {
        try {
            vista.mostrarClientes(gestor.listar());
        } catch (SQLException e) {
            vista.mostrarMensaje(
                    "Error de base de datos: " + e.getMessage()
            );
        }
    }
}