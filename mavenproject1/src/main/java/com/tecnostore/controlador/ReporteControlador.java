package com.tecnostore.controlador;

import com.tecnostore.modelo.Celular;
import com.tecnostore.servicio.GestorCelulares;
import com.tecnostore.util.ReporteUtils;
import com.tecnostore.vista.ReporteVista;
import java.sql.SQLException;
import java.util.List;
import com.tecnostore.persistencia.ReporteDAO;
import com.tecnostore.persistencia.ReporteDAO.DetalleVendido;
import java.util.Map;
import com.tecnostore.persistencia.ReporteDAO.VentaResumen;
import java.math.BigDecimal;
import java.time.YearMonth;
import com.tecnostore.util.ArchivoUtils;
import java.io.IOException;
import java.nio.file.Path;
public class ReporteControlador {

    private final GestorCelulares gestorCelulares;
    private final ReporteVista vista;
    private final ReporteDAO reporteDAO = new ReporteDAO();

    public ReporteControlador(
            GestorCelulares gestorCelulares,
            ReporteVista vista
    ) {
        this.gestorCelulares = gestorCelulares;
        this.vista = vista;
    }

    public void mostrarStockBajo() {
        try {
            List<Celular> celulares = gestorCelulares.listar();

            List<Celular> resultado =
                    ReporteUtils.celularesConStockBajo(celulares);

            vista.mostrarStockBajo(resultado);

        } catch (SQLException e) {
            vista.mostrarMensaje(
                    "No se pudo consultar el stock: " + e.getMessage()
            );
        }
    }
    public void mostrarTopTres() {
    try {
        List<DetalleVendido> detalles =
                reporteDAO.listarDetallesVendidos();

        List<Map.Entry<Integer, Long>> ranking =
                ReporteUtils.topTresMasVendidos(detalles);

        vista.mostrarTopTres(ranking, detalles);

    } catch (SQLException e) {
        vista.mostrarMensaje(
                "No se pudo consultar el top 3: " + e.getMessage()
        );
    }
}
    public void mostrarVentasPorMes() {
    try {
        List<VentaResumen> ventas = reporteDAO.listarVentas();

        Map<YearMonth, BigDecimal> totales =
                ReporteUtils.ventasPorMes(ventas);

        vista.mostrarVentasPorMes(totales);

    } catch (SQLException e) {
        vista.mostrarMensaje(
                "No se pudo consultar las ventas por mes: "
                + e.getMessage()
        );
    }
    
}
    public void exportarVentas() {
    try {
        List<VentaResumen> ventas = reporteDAO.listarVentas();

        Path archivo = ArchivoUtils.generarReporteVentas(ventas);

        vista.mostrarMensaje(
                "Reporte generado en: " + archivo
        );

    } catch (SQLException e) {
        vista.mostrarMensaje(
                "No se pudieron consultar las ventas: " + e.getMessage()
        );
    } catch (IOException e) {
        vista.mostrarMensaje(
                "No se pudo escribir el archivo: " + e.getMessage()
        );
    }
}
}