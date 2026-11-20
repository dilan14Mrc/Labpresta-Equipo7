
package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Disponible;
import com.labpresta.modelo.EnMantenimiento;
import com.labpresta.modelo.EstadoEquipo;
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

        // Conectar el boton Cambiar estado.
        vista.getBtnCambiarEstado().addActionListener(
                e -> cambiarEstadoEquipo()
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

    // Cambiar entre Disponible y En mantenimiento.
    private void cambiarEstadoEquipo() {

        // Obtener el equipo seleccionado en la tabla.
        String codigo = vista.getCodigoEquipoSeleccionado();

        if (codigo == null) {
            vista.mostrarMensaje(
                    "Debe seleccionar un equipo de la tabla."
            );
            return;
        }

        // Buscar el equipo en el repositorio.
        Equipo equipo = repositorio.buscar(codigo);

        if (equipo == null) {
            vista.mostrarMensaje(
                    "No se encontro el equipo seleccionado."
            );
            return;
        }

        try {

            EstadoEquipo nuevoEstado;

            // Disponible a En mantenimiento.
            if (equipo.getEstado() instanceof Disponible) {

                nuevoEstado = new EnMantenimiento();

            // En mantenimiento a Disponible.
            } else if (equipo.getEstado() instanceof EnMantenimiento) {

                nuevoEstado = new Disponible();

            // No modificar equipos prestados.
            } else {

                vista.mostrarMensaje(
                        "No se puede cambiar el estado de un equipo prestado."
                );
                return;
            }

            // Validar la transicion con el patron State.
            equipo.setEstado(nuevoEstado);

            // Guardar el cambio en el repositorio.
            repositorio.guardar(equipo);

            // Actualizar el estado en la tabla.
            vista.actualizarEstadoEquipo(
                    equipo.getCodigo(),
                    equipo.getEstado().getNombre()
            );

            vista.mostrarMensaje(
                    "Estado actualizado a: "
                    + equipo.getEstado().getNombre()
            );

        } catch (IllegalArgumentException ex) {

            vista.mostrarMensaje(ex.getMessage());
        }
    }
}
