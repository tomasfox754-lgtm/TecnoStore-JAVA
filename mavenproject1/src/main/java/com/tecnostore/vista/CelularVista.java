package com.tecnostore.vista;

import com.tecnostore.modelo.Celular;
import java.util.List;
import java.util.Scanner;
import com.tecnostore.modelo.CategoriaGama;
import java.math.BigDecimal;
import java.util.Locale;

public class CelularVista {

    private final Scanner scanner;

    public CelularVista(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarCelulares(List<Celular> celulares) {
        if (celulares.isEmpty()) {
            mostrarMensaje("No hay celulares registrados.");
            return;
        }

        System.out.println("\n===== CELULARES =====");

        for (Celular celular : celulares) {
            System.out.println(celular);
        }
    }

    public int pedirId() {
        System.out.print("ID del celular: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public boolean confirmarEliminacion(Celular celular) {
        System.out.println("Celular seleccionado: " + celular);
        System.out.print("Escribe SI para confirmar la eliminacion: ");

        return scanner.nextLine().trim().equalsIgnoreCase("SI");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    public Celular pedirDatos(int id) {
    System.out.print("Marca: ");
    String marca = scanner.nextLine();

    System.out.print("Modelo: ");
    String modelo = scanner.nextLine();

    System.out.print("Sistema operativo: ");
    String sistemaOperativo = scanner.nextLine();

    System.out.print("Gama (ALTA, MEDIA, BAJA): ");
    CategoriaGama gama = CategoriaGama.valueOf(
            scanner.nextLine().trim().toUpperCase(Locale.ROOT)
    );

    System.out.print("Precio (ejemplo: 1500000.00): ");
    BigDecimal precio = new BigDecimal(
            scanner.nextLine().trim()
    );

    if (precio.scale() > 2
            || precio.compareTo(new BigDecimal("99999999.99")) > 0) {
        throw new IllegalArgumentException(
                "El precio admite maximo dos decimales "
                + "y no puede superar 99999999.99."
        );
    }

    System.out.print("Stock: ");
    int stock = Integer.parseInt(scanner.nextLine().trim());

    return new Celular(
            id, marca, modelo, precio, stock,
            sistemaOperativo, gama
    );
}
}