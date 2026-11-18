package com.labpresta.controlador;

import com.labpresta.modelo.Usuario;
import com.labpresta.modelo.TipoUsuario;
import com.labpresta.repositorio.Repositorio;

public class ValidadorUsuario {

    public void validar(String cedula, String nombreCompleto,
                        TipoUsuario tipo, String telefono,
                        Repositorio<Usuario> repositorio) {

        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula es obligatoria.");
        }

        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de usuario.");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }

        // Verificar cédula repetida eliminando los espacios al inicio y al final
        if (repositorio.buscar(cedula.trim()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con esa cédula.");
        }

        if (nombreCompleto.contains(",")) {
            throw new IllegalArgumentException("El nombre no puede contener comas.");
        }

        if (telefono.contains(",")) {
            throw new IllegalArgumentException("El teléfono no puede contener comas.");
        }
    }
}