package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.Producto;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ReporteBajoStockService {

    private final InventarioService inventarioService;

    public ReporteBajoStockService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public String generarReporte() {
        String detalle = inventarioService.productosBajoStockMinimo().stream()
                .map(this::lineaDe)
                .collect(Collectors.joining(System.lineSeparator()));
        return detalle.isEmpty() ? "REPORTE BAJO STOCK: sin productos bajo el minimo"
                : "REPORTE BAJO STOCK" + System.lineSeparator() + detalle;
    }

    private String lineaDe(Producto p) {
        return "- " + p.getNombre() + ": stock " + p.getStock() + " / minimo " + p.getStockMinimo();
    }
}
