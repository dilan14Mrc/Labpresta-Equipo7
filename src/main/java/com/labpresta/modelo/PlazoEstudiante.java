
package com.labpresta.modelo;

import java.time.LocalDate;

public class PlazoEstudiante implements PoliticaPlazo {

    // Los estudiantes pueden prestar equipos por 1 día.
    @Override
    public LocalDate calcularFechaLimite(LocalDate fechaPrestamo) {

        return fechaPrestamo.plusDays(1);
    }
}
