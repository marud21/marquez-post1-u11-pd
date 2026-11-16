# Refactoring U11 — Refactorización Guiada por Pruebas

**UFPS — Patrones de Diseño de Software (1156701-A) · Post-contenido Unidad 11**
**Estudiante:** Diego Armando Márquez Rigaud — **Código:** 1152115

## Parte 1 — Red de seguridad y refactorización

| Smell | Evidencia antes | Técnica de Fowler | Evidencia después |
|---|---|---|---|
| Long Method en `actualizarInventario()` | 38 líneas, CC 12, 5 responsabilidades | Extract Method + Replace Loop with Pipeline | 11 líneas, CC 5 |
| Switch Statements duplicados | 2 `switch (producto.getTipo())` con las mismas 4 ramas | Replace Conditional with Polymorphism (enum con métodos abstractos) | 0 `switch`/`case` en ambas clases |

`mvn test`: 18 pruebas en verde antes de refactorizar, después del Long Method y después del switch
(ninguna prueba modificada).

## Parte 2 — Diagnóstico del acoplamiento entre paquetes

`ArquitecturaPaquetesTest` (ArchUnit) falla: `InventarioService` importa `ReporteBajoStockService` y este
importa `InventarioService`.

```
 ┌──────────────┐  InventarioService → ReporteBajoStockService  ┌────────────┐
 │  inventario  │ ────────────────────────────────────────────▶ │  reportes  │
 │              │ ◀──────────────────────────────────────────── │            │
 └──────────────┘  ReporteBajoStockService → InventarioService   └────────────┘
                         ciclo: inventario → reportes → inventario
```

![Prueba de arquitectura en rojo](docs/01-arquitectura-en-rojo.png)
