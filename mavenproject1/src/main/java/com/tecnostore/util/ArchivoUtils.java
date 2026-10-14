package com.tecnostore.util;

import com.tecnostore.persistencia.ReporteDAO.VentaResumen;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArchivoUtils {

    private ArchivoUtils() {
    }

    public static Path generarReporteVentas(
            List<VentaResumen> ventas
    ) throws IOException {

        Path archivo = Path.of("reporte_ventas.txt").toAbsolutePath();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivo, StandardCharsets.UTF_8
        )) {
            escritor.write("REPORTE DE VENTAS");
            escritor.newLine();
            escritor.write("=================");
            escritor.newLine();

            if (ventas.isEmpty()) {
                escritor.write("No hay ventas registradas.");
                escritor.newLine();
            }

            for (VentaResumen venta : ventas) {
                escritor.newLine();
                escritor.write("Venta ID: " + venta.id());
                escritor.newLine();
                escritor.write("Cliente: " + venta.cliente());
                escritor.newLine();
                escritor.write("Fecha: " + venta.fecha().format(formato));
                escritor.newLine();
                escritor.write(
                        "Total con IVA: " + venta.total().toPlainString()
                );
                escritor.newLine();
            }
        }

        return archivo;
    }
}