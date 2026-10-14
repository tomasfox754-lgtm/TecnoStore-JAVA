package com.tecnostore.vista;

import com.tecnostore.modelo.Celular;
import com.tecnostore.modelo.Cliente;
import com.tecnostore.modelo.ItemVenta;
import com.tecnostore.modelo.Venta;
import java.util.List;
import java.util.Scanner;

public class VentaVista {

    private final Scanner scanner;

    public VentaVista(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarClientes(List<Cliente> clientes) {
        System.out.println("\n===== CLIENTES =====");

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void mostrarCelulares(List<Celular> celulares) {
        System.out.println("\n===== CELULARES =====");

        for (Celular celular : celulares) {
            System.out.println(celular);
        }
    }

    public int pedirIdCliente() {
        System.out.print("ID del cliente: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int pedirIdCelular() {
        System.out.print("ID del celular (0 para terminar): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int pedirCantidad() {
        System.out.print("Cantidad: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public void mostrarResumen(Venta venta) {
        System.out.println("\n===== RESUMEN DE VENTA =====");
        System.out.println("Cliente: " + venta.getCliente().getNombre());

        for (ItemVenta item : venta.getItems()) {
            Celular celular = item.getCelular();

            System.out.println(
                    celular.getMarca() + " " + celular.getModelo()
                    + " | Cantidad: " + item.getCantidad()
                    + " | Precio: " + item.getPrecioUnitario()
                    + " | Subtotal: " + item.getSubtotal()
            );
        }

        System.out.println("Subtotal: " + venta.getSubtotal());
        System.out.println("IVA (19%): " + venta.getIva());
        System.out.println("Total: " + venta.getTotal());
    }

    public boolean confirmarVenta() {
        System.out.print("Escribe SI para confirmar la venta: ");
        return scanner.nextLine().trim().equalsIgnoreCase("SI");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}