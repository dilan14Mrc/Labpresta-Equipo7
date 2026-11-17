
package com.labpresta.modelo;

public class FabricaPoliticaPlazo {

    // Selecciona la política según el tipo de usuario.
    public static PoliticaPlazo crear(TipoUsuario tipoUsuario) {

        // Verifica que el tipo de usuario no sea nulo.
        if (tipoUsuario == null) {
            throw new IllegalArgumentException(
                "El tipo de usuario no puede ser nulo"
            );
        }

        // Si es estudiante, utiliza la política de 1 día.
        if (tipoUsuario.name().equalsIgnoreCase("ESTUDIANTE")) {
            return new PlazoEstudiante();
        }

        // Si es docente, utiliza la política de 3 días.
        if (tipoUsuario.name().equalsIgnoreCase("DOCENTE")) {
            return new PlazoDocente();
        }

        // Rechaza tipos de usuario desconocidos.
        throw new IllegalArgumentException(
            "Tipo de usuario no válido"
        );
    }
}
