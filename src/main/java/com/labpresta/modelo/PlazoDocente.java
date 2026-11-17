
package com.labpresta.modelo;

import java.time.LocalDate;

public class PlazoDocente implements PoliticaPlazo {

    // Los docentes tienen 3 días para devolver el equipo.
    @Override
    public LocalDate calcularFechaLimite(LocalDate fechaPrestamo) {

        return fechaPrestamo.plusDays(3);
    }
}
