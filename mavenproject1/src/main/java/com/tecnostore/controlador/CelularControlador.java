package com.tecnostore.controlador;

import com.tecnostore.modelo.Celular;
import com.tecnostore.servicio.GestorCelulares;
import com.tecnostore.vista.CelularVista;
import java.sql.SQLException;
import java.util.List;

public class CelularControlador {

    private final GestorCelulares gestor;
    private final CelularVista vista;

    public CelularControlador(
            GestorCelulares gestor, CelularVista vista
    ) {
        this.gestor = gestor;
        this.vista = vista;
    }

    public void registrar() {
        try {
            Celular celular = vista.pedirDatos(0);
            gestor.registrar(celular);
            vista.mostrarMensaje("Celular registrado correctamente.");
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
            vista.mostrarCelulares(gestor.listar());
        } catch (SQLException e) {
            vista.mostrarMensaje(
                    "Error de base de datos: " + e.getMessage()
            );
        }
    }
    public void actualizar() {
    try {
        List<Celular> celulares = gestor.listar();

        if (celulares.isEmpty()) {
            vista.mostrarMensaje("No hay celulares para actualizar.");
            return;
        }

        vista.mostrarCelulares(celulares);
        int id = vista.pedirId();

        Celular seleccionado = null;

        for (Celular celular : celulares) {
            if (celular.getId() == id) {
                seleccionado = celular;
                break;
            }
        }

        if (seleccionado == null) {
            vista.mostrarMensaje("No existe un celular con ese ID.");
            return;
        }

        vista.mostrarMensaje("Celular seleccionado: " + seleccionado);
        vista.mostrarMensaje("Escribe todos los datos que debe tener.");

        Celular actualizado = vista.pedirDatos(id);

        if (gestor.actualizar(actualizado)) {
            vista.mostrarMensaje("Celular actualizado correctamente.");
        } else {
            vista.mostrarMensaje("No se confirmo la actualizacion.");
        }

        } catch (IllegalArgumentException e) {
        vista.mostrarMensaje("Datos invalidos: " + e.getMessage());
    } catch (SQLException e) {
        vista.mostrarMensaje(
                "Error de base de datos: " + e.getMessage()
        );
    }
}

    
    public void eliminar() {
    try {
        List<Celular> celulares = gestor.listar();

        if (celulares.isEmpty()) {
            vista.mostrarMensaje("No hay celulares para eliminar.");
            return;
        }

        vista.mostrarCelulares(celulares);
        int id = vista.pedirId();

        Celular seleccionado = null;

        for (Celular celular : celulares) {
            if (celular.getId() == id) {
                seleccionado = celular;
                break;
            }
        }

        if (seleccionado == null) {
            vista.mostrarMensaje("No existe un celular con ese ID.");
            return;
        }

        if (!vista.confirmarEliminacion(seleccionado)) {
            vista.mostrarMensaje("Eliminacion cancelada.");
            return;
        }

        if (gestor.eliminar(id)) {
            vista.mostrarMensaje("Celular eliminado correctamente.");
        } else {
            vista.mostrarMensaje("El celular ya no existe.");
        }

    } catch (IllegalArgumentException e) {
        vista.mostrarMensaje("Datos invalidos: " + e.getMessage());
    } catch (SQLException e) {
        if (e.getErrorCode() == 1451) {
            vista.mostrarMensaje(
                    "No se puede eliminar: el celular tiene ventas asociadas."
            );
        } else {
            vista.mostrarMensaje(
                    "Error de base de datos: " + e.getMessage()
            );
        }
    }
}  
}
