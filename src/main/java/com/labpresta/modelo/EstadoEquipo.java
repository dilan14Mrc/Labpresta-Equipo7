package com.labpresta.modelo;

public interface EstadoEquipo {

    // Indica si el equipo puede ser prestado.
    boolean puedePrestar();

    // Comprueba si se permite cambiar a otro estado.
    boolean puedeCambiarA(EstadoEquipo nuevoEstado);

    // Devuelve el nombre del estado actual.
    String getNombre();
}