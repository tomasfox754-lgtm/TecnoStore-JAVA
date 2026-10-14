package com.tecnostore.vista;

import com.tecnostore.modelo.Cliente;
import java.util.List;
import java.util.Scanner;

public class ClienteVista {

    private final Scanner scanner;

    public ClienteVista(Scanner scanner) {
        this.scanner = scanner;
    }

    public Cliente pedirDatos() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Identificacion: ");
        String identificacion = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        return new Cliente(
                0, nombre, identificacion, correo, telefono
        );
    }

    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            mostrarMensaje("No hay clientes registrados.");
            return;
        }

        System.out.println("\n===== CLIENTES =====");

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}