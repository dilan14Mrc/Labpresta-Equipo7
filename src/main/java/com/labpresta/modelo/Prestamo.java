package com.labpresta.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Prestamo {

    private final Equipo equipo;
    private final Usuario usuario;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaLimite;
    private LocalDate fechaDevolucion; // null = todavía no se devuelve

    // Para un préstamo nuevo (aún sin devolver)
    public Prestamo(Equipo equipo, Usuario usuario,
                    LocalDate fechaPrestamo, LocalDate fechaLimite) {
        this.equipo = Objects.requireNonNull(equipo, "El equipo no puede ser nulo");
        this.usuario = Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
        this.fechaPrestamo = Objects.requireNonNull(fechaPrestamo, "La fecha de préstamo no puede ser nula");
        this.fechaLimite = Objects.requireNonNull(fechaLimite, "La fecha límite no puede ser nula");

        if (fechaLimite.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException("La fecha límite no puede ser anterior al préstamo");
        }
    }

    // Para cargar desde prestamos.csv un préstamo que ya puede estar devuelto
    public Prestamo(Equipo equipo, Usuario usuario, LocalDate fechaPrestamo,
                    LocalDate fechaLimite, LocalDate fechaDevolucion) {
        this(equipo, usuario, fechaPrestamo, fechaLimite);
        if (fechaDevolucion != null) {
            registrarDevolucion(fechaDevolucion);
        }
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    // Un préstamo activo es el que todavía no se devuelve
    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public void registrarDevolucion(LocalDate fecha) {
        Objects.requireNonNull(fecha, "La fecha de devolución no puede ser nula");
        if (!estaActivo()) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
        if (fecha.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException("La devolución no puede ser antes del préstamo");
        }
        this.fechaDevolucion = fecha;
    }

    // Vencido: no se ha devuelto y la fecha límite ya pasó
    public boolean estaVencido(LocalDate hoy) {
        return estaActivo() && hoy.isAfter(fechaLimite);
    }

    public boolean estaVencido() {
        return estaVencido(LocalDate.now());
    }

    // Atrasado: se devolvió después de la fecha límite
    public boolean estaAtrasado() {
        return !estaActivo() && fechaDevolucion.isAfter(fechaLimite);
    }
}