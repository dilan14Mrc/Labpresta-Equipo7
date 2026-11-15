
package com.labpresta.modelo;

public class Disponible implements EstadoEquipo {

    // Un equipo disponible puede prestarse.
    @Override
    public boolean puedePrestar() {
        return true;
    }

    // Permite pasar a Prestado o En mantenimiento.
    @Override
    public boolean puedeCambiarA(EstadoEquipo nuevoEstado) {
        return nuevoEstado instanceof Prestado
                || nuevoEstado instanceof EnMantenimiento;
    }

    // Devuelve el nombre del estado.
    @Override
    public String getNombre() {
        return "Disponible";
    }
}
