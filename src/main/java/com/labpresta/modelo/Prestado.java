
package com.labpresta.modelo;

public class Prestado implements EstadoEquipo {

    // Un equipo prestado no puede prestarse nuevamente.
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
        return "Prestado";
    }
}
