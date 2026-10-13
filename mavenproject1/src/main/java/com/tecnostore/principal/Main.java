package com.tecnostore.principal;

import com.tecnostore.modelo.CategoriaGama;
import com.tecnostore.modelo.Celular;
import com.tecnostore.servicio.GestorCelulares;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GestorCelulares gestor = new GestorCelulares(); 

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;

            while (continuar) {
                System.out.println("\n===== TECNOSTORE =====");
                System.out.println("1. Registrar celular");
                System.out.println("2. Listar celulares");
                System.out.println("3. Actualizar celular");
                System.out.println("4. Eliminar celular");
                System.out.println("0. Salir");
                System.out.print("Selecciona una opción: ");

                String opcion = scanner.nextLine().trim();

                try {
                    switch (opcion) {
                        case "1" -> registrarCelular(scanner, gestor);
                        case "2" -> listarCelulares(gestor);
                        case "3" -> actualizarCelular(scanner, gestor);
                        case "4" -> eliminarCelular(scanner, gestor);
                        case "0" -> {
                            continuar = false;
                            System.out.println("Hasta pronto.");
                        }
                        default ->
                            System.out.println("Opción inválida.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Datos inválidos: " + e.getMessage());
                } catch (SQLException e) {
                  System.out.println("Mensaje: " + e.getMessage());
                  System.out.println("Estado SQL: " + e.getSQLState());
                  System.out.println("Código: " + e.getErrorCode());
                  e.printStackTrace();
                }
            }
        }
    }

    private static void registrarCelular(
            Scanner scanner, GestorCelulares gestor
    ) throws SQLException {

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
                    "El precio admite máximo dos decimales "
                    + "y no puede superar 99999999.99."
            );
        }

        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());

        Celular celular = new Celular(
                0, marca, modelo, precio, stock,
                sistemaOperativo, gama
        );

        gestor.registrar(celular);
        System.out.println("Celular registrado correctamente.");
    }

        private static void listarCelulares(
            GestorCelulares gestor
    ) throws SQLException {

        List<Celular> celulares = gestor.listar();

        if (celulares.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return;
        }

        System.out.println("\n===== CATÁLOGO =====");

        for (Celular celular : celulares) {
            System.out.println(celular);
        }
    }

    private static void actualizarCelular(
        Scanner scanner, GestorCelulares gestor
) throws SQLException {

    List<Celular> celulares = gestor.listar();

    if (celulares.isEmpty()) {
        System.out.println("No hay celulares para actualizar.");
        return;
    }

    for (Celular celular : celulares) {
        System.out.println(celular);
    }

    System.out.print("ID del celular que deseas actualizar: ");
    int id = Integer.parseInt(scanner.nextLine().trim());

    Celular seleccionado = null;

    for (Celular celular : celulares) {
        if (celular.getId() == id) {
            seleccionado = celular;
            break;
        }
    }

    if (seleccionado == null) {
        System.out.println("No existe un celular con ese ID.");
        return;
    }

    System.out.println("Celular seleccionado: " + seleccionado);
    System.out.println("Escribe todos los datos que debe tener.");

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

    System.out.print("Precio (ejemplo: 1400000.00): ");
    BigDecimal precio = new BigDecimal(
            scanner.nextLine().trim()
    );

    if (precio.scale() > 2
            || precio.compareTo(new BigDecimal("99999999.99")) > 0) {
        throw new IllegalArgumentException(
                "El precio admite máximo dos decimales "
                + "y no puede superar 99999999.99."
        );
    }

    System.out.print("Stock: ");
    int stock = Integer.parseInt(scanner.nextLine().trim());

    Celular actualizado = new Celular(
            id, marca, modelo, precio, stock,
            sistemaOperativo, gama
    );

    if (gestor.actualizar(actualizado)) {
        System.out.println("Celular actualizado correctamente.");
    } else {
        System.out.println("No se confirmo la actualizacion del celular.");
    }
}
    private static void eliminarCelular(
        Scanner scanner, GestorCelulares gestor
) throws SQLException {

    List<Celular> celulares = gestor.listar();

    if (celulares.isEmpty()) {
        System.out.println("No hay celulares para eliminar.");
        return;
    }

    for (Celular celular : celulares) {
        System.out.println(celular);
    }

    System.out.print("ID del celular que deseas eliminar: ");
    int id = Integer.parseInt(scanner.nextLine().trim());

    Celular seleccionado = null;

    for (Celular celular : celulares) {
        if (celular.getId() == id) {
            seleccionado = celular;
            break;
        }
    }

    if (seleccionado == null) {
        System.out.println("No existe un celular con ese ID.");
        return;
    }

    System.out.println("Celular seleccionado: " + seleccionado);
    System.out.print("Escribe SI para confirmar la eliminación: ");
    String confirmacion = scanner.nextLine().trim();

    if (!confirmacion.equalsIgnoreCase("SI")) {
        System.out.println("Eliminación cancelada.");
        return;
    }

    try {
        if (gestor.eliminar(id)) {
            System.out.println("Celular eliminado correctamente.");
        } else {
            System.out.println("El celular ya no existe.");
        }
    } catch (SQLException e) {
        if (e.getErrorCode() == 1451) {
            System.out.println(
                    "No se puede eliminar: el celular tiene ventas asociadas."
            );
        } else {
            throw e;
        }
    }
}
}
