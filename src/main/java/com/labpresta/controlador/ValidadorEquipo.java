
package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.TipoEquipo;
import com.labpresta.modelo.Disponible;
import com.labpresta.repositorio.Repositorio;

public class ValidadorEquipo {

    public void validar(String codigo, TipoEquipo tipo,
                        String descripcion,
                        Repositorio<Equipo> repositorio) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo es obligatorio.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion es obligatoria.");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de equipo.");
        }

        if (repositorio.buscar(codigo.trim()) != null) {
            throw new IllegalArgumentException("Ya existe un equipo con ese codigo.");
        }

        if (descripcion.contains(",")) {
            throw new IllegalArgumentException("La descripcion no puede contener comas.");
        }
    }

    // Verifica la disponibilidad para préstamo.
    public void validarDisponible(Equipo equipo) {

        if (equipo == null) {
            throw new IllegalArgumentException(
                    "El equipo no existe.");
        }

        if (!(equipo.getEstado() instanceof Disponible)) {
            throw new IllegalArgumentException(
                    "El equipo no esta disponible para préstamo.");
        }
    }
}
