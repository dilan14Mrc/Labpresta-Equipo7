
package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Disponible;
import com.labpresta.modelo.TipoEquipo;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaEquipos;

public class ControladorEquipos {

    private final VistaEquipos vista;
    private final Repositorio<Equipo> repositorio;
    private final ValidadorEquipo validador;

    // Constructor del controlador.
    public ControladorEquipos(
            VistaEquipos vista,
            Repositorio<Equipo> repositorio) {

        this.vista = vista;
        this.repositorio = repositorio;
        this.validador = new ValidadorEquipo();

        // Conectar el boton Registrar.
        vista.getBtnRegistrar().addActionListener(
                e -> registrarEquipo()
        );

        // Mostrar los equipos existentes.
        cargarEquipos();
    }

    // Registrar un nuevo equipo.
    private void registrarEquipo() {

        String codigo = vista.getCodigo();
        TipoEquipo tipo = vista.getTipoSeleccionado();
        String descripcion = vista.getDescripcion();

        try {

            // Validar los datos con la clase de Katherin.
            validador.validar(
                    codigo,
                    tipo,
                    descripcion,
                    repositorio
            );

            // Crear el equipo con estado Disponible.
            Equipo equipo = new Equipo(
                    codigo,
                    tipo,
                    descripcion,
                    new Disponible()
            );

            // Guardar utilizando el repositorio de Andrea.
            repositorio.guardar(equipo);

            // Agregar el equipo a la tabla.
            vista.agregarFila(
                    equipo.getCodigo(),
                    equipo.getTipo(),
                    equipo.getDescripcion(),
                    equipo.getEstado().getNombre()
            );

            // Limpiar los campos del formulario.
            vista.limpiarCampos();

            vista.mostrarMensaje(
                    "Equipo registrado correctamente."
            );

        } catch (IllegalArgumentException ex) {

            vista.mostrarMensaje(ex.getMessage());
        }
    }

    // Cargar los equipos guardados en la tabla.
    private void cargarEquipos() {

        for (Equipo equipo : repositorio.listar()) {

            vista.agregarFila(
                    equipo.getCodigo(),
                    equipo.getTipo(),
                    equipo.getDescripcion(),
                    equipo.getEstado().getNombre()
            );
        }
    }
}
