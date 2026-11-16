package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.Producto;
import com.universidad.refactoringu11.inventario.TipoProducto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReporteBajoStockServiceTest {

    @Test
    void notificacionPorEventoQuedaRegistradaYApareceEnElReporte() {
        InventarioService inventario = new InventarioService();
        ReporteBajoStockService reporte = new ReporteBajoStockService(inventario);
        inventario.setNotificador(reporte);
        inventario.agregarProducto(new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 20, 5));

        assertEquals("OK_BAJO_STOCK_REORDEN_12", inventario.actualizarInventario(1L, 17, "SALIDA"));

        assertEquals(1, reporte.alertasRegistradas().size());
        assertEquals("Clean Code: reordenar 12 unidades", reporte.alertasRegistradas().get(0));
        assertTrue(reporte.generarReporte().contains("- Clean Code: stock 3 / minimo 5"));
    }

    @Test
    void sinProductosBajoMinimo_elReporteLoIndica() {
        ReporteBajoStockService reporte = new ReporteBajoStockService(new InventarioService());
        assertTrue(reporte.generarReporte().startsWith("REPORTE BAJO STOCK: sin productos bajo el minimo"));
    }
}
