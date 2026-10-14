package com.tecnostore.vista;

import java.util.Scanner;

public class MenuVista {

    private final Scanner scanner;

    public MenuVista(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarPrincipal() {
        System.out.println("\n===== TECNOSTORE =====");
        System.out.println("1. Registrar celular");
        System.out.println("2. Listar celulares");
        System.out.println("3. Actualizar celular");
        System.out.println("4. Eliminar celular");
        System.out.println("5. Registrar cliente");
        System.out.println("6. Listar clientes");
        System.out.println("7. Registrar venta");
        System.out.println("8. Reporte de stock bajo");
        System.out.println("9. Top 3 celulares mas vendidos");
        System.out.println("10. Ventas totales por mes");
        System.out.println("11. Exportar reporte de ventas");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    public String leerOpcion() {
        return scanner.nextLine().trim();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}