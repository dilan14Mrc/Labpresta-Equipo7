
package com.labpresta.modelo;

import java.time.LocalDate;

public interface PoliticaPlazo {

    // Calcula la fecha límite de un préstamo.
    LocalDate calcularFechaLimite(LocalDate fechaPrestamo);

}
