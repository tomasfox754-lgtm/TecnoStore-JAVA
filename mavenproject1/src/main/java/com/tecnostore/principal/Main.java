package com.tecnostore.principal;

import com.tecnostore.controlador.CelularControlador;
import com.tecnostore.controlador.ClienteControlador;
import com.tecnostore.controlador.PrincipalControlador;
import com.tecnostore.controlador.VentaControlador;
import com.tecnostore.servicio.GestorCelulares;
import com.tecnostore.servicio.GestorClientes;
import com.tecnostore.servicio.GestorVentas;
import com.tecnostore.vista.CelularVista;
import com.tecnostore.vista.ClienteVista;
import com.tecnostore.vista.MenuVista;
import com.tecnostore.vista.VentaVista;
import java.util.Scanner;
import com.tecnostore.controlador.ReporteControlador;
import com.tecnostore.vista.ReporteVista;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GestorCelulares gestorCelulares = new GestorCelulares();
            GestorClientes gestorClientes = new GestorClientes();
            GestorVentas gestorVentas = new GestorVentas();

            MenuVista menuVista = new MenuVista(scanner);

            CelularControlador celulares = new CelularControlador(
                    gestorCelulares,
                    new CelularVista(scanner)
            );

            ClienteControlador clientes = new ClienteControlador(
                    gestorClientes,
                    new ClienteVista(scanner)
            );

            VentaControlador ventas = new VentaControlador(
                    gestorVentas,
                    gestorClientes,
                    gestorCelulares,
                    new VentaVista(scanner)
            );

          ReporteControlador reportes = new ReporteControlador(         
        gestorCelulares,
        new ReporteVista()
);

        PrincipalControlador principal = new PrincipalControlador(
        menuVista, celulares, clientes, ventas, reportes
            );

            principal.iniciar();
        }
    }
}