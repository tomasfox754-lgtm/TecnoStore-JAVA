package com.tecnostore.controlador;

import com.tecnostore.vista.MenuVista;

public class PrincipalControlador {

    private final MenuVista vista;
    private final CelularControlador celulares;
    private final ClienteControlador clientes;
    private final VentaControlador ventas;
    private final ReporteControlador reportes;

    public PrincipalControlador(
            MenuVista vista,
            CelularControlador celulares,
            ClienteControlador clientes,
            VentaControlador ventas,
            ReporteControlador reportes
    ) {
        this.vista = vista;
        this.celulares = celulares;
        this.clientes = clientes;
        this.ventas = ventas;
        this.reportes = reportes;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            vista.mostrarPrincipal();
            String opcion = vista.leerOpcion();

            switch (opcion) {
                case "1" -> celulares.registrar();
                case "2" -> celulares.listar();
                case "3" -> celulares.actualizar();
                case "4" -> celulares.eliminar();
                case "5" -> clientes.registrar();
                case "6" -> clientes.listar();
                case "7" -> ventas.registrar();
                case "8" -> reportes.mostrarStockBajo();
                case "9" -> reportes.mostrarTopTres();
                case "10" -> reportes.mostrarVentasPorMes();
                case "11" -> reportes.exportarVentas();
                case "0" -> {
                    continuar = false;
                    vista.mostrarMensaje("Hasta pronto.");
                }
                default -> vista.mostrarMensaje("Opcion invalida.");
            }
        }
    }
}