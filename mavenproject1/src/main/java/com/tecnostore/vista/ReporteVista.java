package com.tecnostore.vista;

import com.tecnostore.modelo.Celular;
import java.util.List;
import com.tecnostore.persistencia.ReporteDAO.DetalleVendido;
import java.util.Map;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ReporteVista {

    public void mostrarStockBajo(List<Celular> celulares) {
        System.out.println("\n===== CELULARES CON STOCK MENOR A 5 =====");

        if (celulares.isEmpty()) {
            mostrarMensaje("No hay celulares con stock bajo.");
            return;
        }

        for (Celular celular : celulares) {
            System.out.println(celular);
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    public void mostrarTopTres(
        List<Map.Entry<Integer, Long>> ranking,
        List<DetalleVendido> detalles
) {
    System.out.println("\n===== TOP 3 CELULARES MAS VENDIDOS =====");

    if (ranking.isEmpty()) {
        mostrarMensaje("Todavia no hay celulares vendidos.");
        return;
    }

    int posicion = 1;

    for (Map.Entry<Integer, Long> entrada : ranking) {
        String nombre = "Celular ID " + entrada.getKey();

        for (DetalleVendido detalle : detalles) {
            if (detalle.idCelular() == entrada.getKey()) {
                nombre = detalle.marca() + " " + detalle.modelo();
                break;
            }
        }

        System.out.println(
                posicion + ". " + nombre
                + " | Unidades vendidas: " + entrada.getValue()
        );

        posicion++;
    }
}
    public void mostrarVentasPorMes(
        Map<YearMonth, BigDecimal> totales
) {
    System.out.println("\n===== VENTAS TOTALES POR MES =====");

    if (totales.isEmpty()) {
        mostrarMensaje("Todavia no hay ventas registradas.");
        return;
    }

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("MM/yyyy");

    for (Map.Entry<YearMonth, BigDecimal> entrada
            : totales.entrySet()) {

        System.out.println(
                entrada.getKey().format(formato)
                + " | Total con IVA: "
                + entrada.getValue().toPlainString()
        );
    }
}
}