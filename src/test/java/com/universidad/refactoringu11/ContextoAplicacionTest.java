package com.universidad.refactoringu11;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.Producto;
import com.universidad.refactoringu11.inventario.TipoProducto;
import com.universidad.refactoringu11.reportes.ReporteBajoStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Verifica que Spring cablea la implementacion de reportes detras de la abstraccion del dominio. */
@SpringBootTest
class ContextoAplicacionTest {

    @Autowired
    InventarioService inventarioService;
    @Autowired
    ReporteBajoStockService reporteBajoStockService;

    @Test
    void inventarioNotificaAReportesAtravesDeNotificadorBajoStock() {
        inventarioService.agregarProducto(new Producto(10L, "Monitor", TipoProducto.ELECTRONICO, 900.0, 4, 3));

        inventarioService.actualizarInventario(10L, 2, "SALIDA"); // 2 < 3 -> reorden 3*3-2 = 7

        assertEquals(1, reporteBajoStockService.alertasRegistradas().size());
        assertEquals("Monitor: reordenar 7 unidades", reporteBajoStockService.alertasRegistradas().get(0));
    }
}
