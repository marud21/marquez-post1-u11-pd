package com.universidad.refactoringu11.inventario;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private static final String ENTRADA = "ENTRADA";
    private static final String SALIDA = "SALIDA";
    private static final int FACTOR_REORDEN = 3;

    private final List<Producto> productos = new ArrayList<>();
    private final List<String> movimientos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public String actualizarInventario(Long productoId, int cantidad, String tipoMovimiento) {
        if (!esSolicitudValida(productoId, cantidad, tipoMovimiento)) return "ERROR_DATOS_INVALIDOS";
        Optional<Producto> encontrado = buscarProducto(productoId);
        if (encontrado.isEmpty()) return "ERROR_PRODUCTO_NO_EXISTE";
        Producto producto = encontrado.get();
        int nuevoStock = calcularNuevoStock(producto, cantidad, tipoMovimiento);
        if (SALIDA.equals(tipoMovimiento) && nuevoStock < 0) return "ERROR_STOCK_INSUFICIENTE";
        producto.setStock(nuevoStock);
        registrarMovimiento(producto, cantidad, tipoMovimiento);
        return evaluarReorden(producto);
    }

    private boolean esSolicitudValida(Long productoId, int cantidad, String tipoMovimiento) {
        return productoId != null && cantidad != 0
                && (tipoMovimiento.equals(ENTRADA) || tipoMovimiento.equals(SALIDA));
    }

    private Optional<Producto> buscarProducto(Long productoId) {
        return productos.stream().filter(p -> p.getId().equals(productoId)).findFirst();
    }

    private int calcularNuevoStock(Producto producto, int cantidad, String tipoMovimiento) {
        return ENTRADA.equals(tipoMovimiento) ? producto.getStock() + cantidad : producto.getStock() - cantidad;
    }

    private void registrarMovimiento(Producto producto, int cantidad, String tipoMovimiento) {
        String registro = tipoMovimiento + " de " + cantidad + " unidades de "
                + producto.getNombre() + " -> stock final: " + producto.getStock();
        movimientos.add(registro);
        System.out.println(registro);
    }

    private String evaluarReorden(Producto producto) {
        if (producto.getStock() >= producto.getStockMinimo()) {
            return "OK_" + producto.getStock();
        }
        int cantidadReorden = calcularCantidadReorden(producto);
        System.out.println("ALERTA: " + producto.getNombre()
                + " bajo stock minimo. Reordenar " + cantidadReorden + " unidades.");
        return "OK_BAJO_STOCK_REORDEN_" + cantidadReorden;
    }

    /** Politica de reorden: reponer hasta el triple del stock minimo (Decision de diseno 1). */
    private int calcularCantidadReorden(Producto producto) {
        return producto.getStockMinimo() * FACTOR_REORDEN - producto.getStock();
    }
}
