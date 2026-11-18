package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.TipoEquipo;
import com.labpresta.repositorio.Repositorio;

public class ValidadorEquipo {

    public void validar(String codigo, TipoEquipo tipo,
                        String descripcion,
                        Repositorio<Equipo> repositorio) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de equipo.");
        }

        if (repositorio.buscar(codigo.trim()) != null) {
            throw new IllegalArgumentException("Ya existe un equipo con ese código.");
        }

        if (descripcion.contains(",")) {
            throw new IllegalArgumentException("La descripción no puede contener comas.");
        }
    }
}