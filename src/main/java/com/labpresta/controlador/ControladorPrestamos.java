package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.FabricaPoliticaPlazo;
import com.labpresta.modelo.Prestado;
import com.labpresta.modelo.Prestamo;
import com.labpresta.modelo.Usuario;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaPrestar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ControladorPrestamos {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio<Equipo> repositorioEquipos;
    private final Repositorio<Usuario> repositorioUsuarios;
    private final Repositorio<Prestamo> repositorioPrestamos;
    private final VistaPrestar vista;
    private boolean cargando = false;

    // Si la vista es null, solo se usa la lógica (útil para pruebas).
    public ControladorPrestamos(VistaPrestar vista,
                                Repositorio<Equipo> repositorioEquipos,
                                Repositorio<Usuario> repositorioUsuarios,
                                Repositorio<Prestamo> repositorioPrestamos) {
        this.vista = vista;
        this.repositorioEquipos = Objects.requireNonNull(repositorioEquipos);
        this.repositorioUsuarios = Objects.requireNonNull(repositorioUsuarios);
        this.repositorioPrestamos = Objects.requireNonNull(repositorioPrestamos);

        if (vista != null) {
            vista.getBtnPrestar().addActionListener(e -> prestarDesdeVista());
            vista.getCbUsuario().addActionListener(e -> actualizarFechaLimite());
            vista.agregarListenerFecha(e -> actualizarFechaLimite());
            cargarCombos();
        }
    }


    // Registra el préstamo. Si algo no se puede, lanza IllegalArgumentException
    // con el mensaje que se le muestra al usuario.
    public Prestamo prestar(String cedula, String codigoEquipo, LocalDate fechaPrestamo) {
        Usuario usuario = repositorioUsuarios.buscar(cedula);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario seleccionado no existe.");
        }

        Equipo equipo = repositorioEquipos.buscar(codigoEquipo);
        if (equipo == null) {
            throw new IllegalArgumentException("El equipo seleccionado no existe.");
        }

        // HU-C3: solo se presta un equipo Disponible.
        if (!equipo.getEstado().puedePrestar()) {
            throw new IllegalArgumentException("El equipo " + equipo.getCodigo()
                    + " no se puede prestar porque está " + equipo.getEstado().getNombre() + ".");
        }

        LocalDate fechaLimite = FabricaPoliticaPlazo.crear(usuario.getTipo())
                .calcularFechaLimite(fechaPrestamo);

        Prestamo prestamo = new Prestamo(equipo, usuario, fechaPrestamo, fechaLimite);
        equipo.setEstado(new Prestado());

        repositorioEquipos.guardar(equipo);
        repositorioPrestamos.guardar(prestamo);
        return prestamo;
    }

    public LocalDate calcularFechaLimite(String cedula, LocalDate fechaPrestamo) {
        Usuario usuario = repositorioUsuarios.buscar(cedula);
        if (usuario == null) {
            return null;
        }
        return FabricaPoliticaPlazo.crear(usuario.getTipo()).calcularFechaLimite(fechaPrestamo);
    }


    private void prestarDesdeVista() {
        if (vista.getCbUsuario().getSelectedIndex() <= 0
                || vista.getCbEquipo().getSelectedIndex() <= 0) {
            vista.mostrarMensaje("Debe elegir un usuario y un equipo.");
            return;
        }
        String usuarioElegido = (String) vista.getCbUsuario().getSelectedItem();
        String equipoElegido = (String) vista.getCbEquipo().getSelectedItem();
        String cedula = usuarioElegido.split(" - ")[0].trim();
        String codigo = equipoElegido.split(" - ")[0].trim();
        
        try {
            Prestamo prestamo = prestar(cedula, codigo, fechaElegida());
            cargarCombos();
            vista.mostrarMensaje("Préstamo registrado. Fecha límite de devolución: "
                    + prestamo.getFechaLimite().format(FORMATO));
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    private LocalDate fechaElegida() {
        return vista.getFechaPrestamo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void actualizarFechaLimite() {
        if (vista.getCbUsuario().getSelectedIndex() <= 0) {
            vista.setFechaLimite("---");
            return;
        }
        String usuarioElegido = (String) vista.getCbUsuario().getSelectedItem();
        if (usuarioElegido == null) {
            vista.setFechaLimite("---");
            return;
        }
        LocalDate limite = calcularFechaLimite(usuarioElegido.split(" - ")[0].trim(), fechaElegida());
        vista.setFechaLimite(limite == null ? "---" : limite.format(FORMATO));
    }
    
    private void cargarCombos() {
        cargando = true;
        vista.limpiarCombos();
        for (Usuario u : repositorioUsuarios.listar()) {
            vista.agregarUsuarioCombo(u.getCedula() + " - " + u.getNombreCompleto());
        }
        for (Equipo e : repositorioEquipos.listar()) {
            vista.agregarEquipoCombo(e.getCodigo() + " - " + e.getDescripcion()
                    + " (" + e.getEstado().getNombre() + ")");
        }
        cargando = false;
        actualizarFechaLimite();
    }
}