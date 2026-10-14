package com.tecnostore.util;

import com.tecnostore.modelo.Celular;
import com.tecnostore.persistencia.ReporteDAO.DetalleVendido;
import com.tecnostore.persistencia.ReporteDAO.VentaResumen;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReporteUtils {

    private ReporteUtils() {
    }

    public static List<Celular> celularesConStockBajo(
            List<Celular> celulares
    ) {
        return celulares.stream()
                .filter(celular -> celular.getStock() < 5)
                .sorted(Comparator.comparingInt(Celular::getStock))
                .toList();
    }

    public static List<Map.Entry<Integer, Long>> topTresMasVendidos(
            List<DetalleVendido> detalles
    ) {
        Map<Integer, Long> cantidades = detalles.stream()
                .collect(Collectors.groupingBy(
                        DetalleVendido::idCelular,
                        Collectors.summingLong(DetalleVendido::cantidad)
                ));

        return cantidades.entrySet().stream()
                .sorted(
                        Map.Entry.<Integer, Long>comparingByValue()
                                .reversed()
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .limit(3)
                .toList();
    }

    public static Map<YearMonth, BigDecimal> ventasPorMes(
            List<VentaResumen> ventas
    ) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        venta -> YearMonth.from(venta.fecha()),
                        TreeMap::new,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                VentaResumen::total,
                                BigDecimal::add
                        )
                ));
    }
}