package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.NotificadorBajoStock;
import com.universidad.refactoringu11.inventario.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Implementa la abstraccion del dominio; depende de inventario en la unica direccion permitida. */
@Service
public class ReporteBajoStockService implements NotificadorBajoStock {

    private final InventarioService inventarioService;
    private final List<String> alertas = new ArrayList<>();

    public ReporteBajoStockService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Override
    public void notificar(Producto producto, int cantidadReorden) {
        alertas.add(producto.getNombre() + ": reordenar " + cantidadReorden + " unidades");
    }

    public List<String> alertasRegistradas() {
        return Collections.unmodifiableList(alertas);
    }

    public String generarReporte() {
        String detalle = inventarioService.productosBajoStockMinimo().stream()
                .map(this::lineaDe)
                .collect(Collectors.joining(System.lineSeparator()));
        String resumen = "Alertas recibidas: " + alertas.size();
        return detalle.isEmpty() ? "REPORTE BAJO STOCK: sin productos bajo el minimo. " + resumen
                : "REPORTE BAJO STOCK" + System.lineSeparator() + detalle + System.lineSeparator() + resumen;
    }

    private String lineaDe(Producto p) {
        return "- " + p.getNombre() + ": stock " + p.getStock() + " / minimo " + p.getStockMinimo();
    }
}
