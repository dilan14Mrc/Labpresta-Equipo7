
package com.labpresta.modelo;

public class EnMantenimiento implements EstadoEquipo {

    // Un equipo en mantenimiento no puede prestarse.
    @Override
    public boolean puedePrestar() {
        return false;
    }

    // Solo permite volver al estado Disponible.
    @Override
    public boolean puedeCambiarA(EstadoEquipo nuevoEstado) {
        return nuevoEstado instanceof Disponible;
    }

    // Devuelve el nombre del estado.
    @Override
    public String getNombre() {
        return "En mantenimiento";
    }
}
