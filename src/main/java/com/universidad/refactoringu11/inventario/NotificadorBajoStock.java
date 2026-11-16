package com.universidad.refactoringu11.inventario;

/**
 * Abstraccion definida por el paquete de mayor nivel (inventario, el dominio).
 * Los paquetes de infraestructura/salida, como reportes, la implementan (DIP).
 */
public interface NotificadorBajoStock {

    void notificar(Producto producto, int cantidadReorden);
}
