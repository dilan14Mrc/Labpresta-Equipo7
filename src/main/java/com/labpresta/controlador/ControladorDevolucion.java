package com.labpresta.controlador;

import com.labpresta.modelo.Disponible;
import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Prestamo;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaDevolucion;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorDevolucion {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio<Equipo> repositorioEquipos;
    private final Repositorio<Prestamo> repositorioPrestamos;
    private final VistaDevolucion vista;
    private List<Prestamo> activosMostrados = new ArrayList<>();

    // Si la vista es null, solo se usa la lógica (útil para pruebas).
    public ControladorDevolucion(VistaDevolucion vista,
                                 Repositorio<Equipo> repositorioEquipos,
                                 Repositorio<Prestamo> repositorioPrestamos) {
        this.vista = vista;
        this.repositorioEquipos = Objects.requireNonNull(repositorioEquipos);
        this.repositorioPrestamos = Objects.requireNonNull(repositorioPrestamos);

        if (vista != null) {
            vista.getBtnDevolver().addActionListener(e -> devolverDesdeVista());
            vista.getCbPrestamoActivo().addActionListener(e -> ajustarFechaMinima());
            cargarCombo();
        }
    }

    public List<Prestamo> listarActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : repositorioPrestamos.listar()) {
            if (p.estaActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }

    // Guarda la fecha de devolución y deja el equipo Disponible.
    public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) {
        if (prestamo == null) {
            throw new IllegalArgumentException("Debe elegir un préstamo activo.");
        }
        prestamo.registrarDevolucion(fechaDevolucion);
        prestamo.getEquipo().setEstado(new Disponible());

        repositorioEquipos.guardar(prestamo.getEquipo());
        repositorioPrestamos.guardar(prestamo);
    }

    private void ajustarFechaMinima() {
        int indice = vista.getCbPrestamoActivo().getSelectedIndex() - 1;
        if (indice < 0 || indice >= activosMostrados.size()) {
            vista.setFechaMinima(null);
            return;
        }
        vista.setFechaMinima(activosMostrados.get(indice).getFechaPrestamo());
    }

    private void devolverDesdeVista() {
        int indice = vista.getCbPrestamoActivo().getSelectedIndex() - 1;
        if (indice < 0 || indice >= activosMostrados.size()) {
            vista.mostrarMensaje("Debe elegir un préstamo activo.");
            return;
        }

        Prestamo prestamo = activosMostrados.get(indice);
        LocalDate fecha = vista.getFechaDevolucion().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            devolver(prestamo, fecha);
            cargarCombo();
            vista.mostrarMensaje("Devolución registrada. El equipo "
                    + prestamo.getEquipo().getCodigo() + " vuelve a estar Disponible.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    private void cargarCombo() {
        vista.limpiarCombo();
        activosMostrados = listarActivos();
        for (Prestamo p : activosMostrados) {
            vista.agregarPrestamoCombo(p.getEquipo().getCodigo() + " - "
                    + p.getUsuario().getNombreCompleto() + " - "
                    + p.getFechaPrestamo().format(FORMATO));
        }
    }
}