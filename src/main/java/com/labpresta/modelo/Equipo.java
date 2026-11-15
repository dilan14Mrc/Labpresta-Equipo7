package com.labpresta.modelo;

public class Equipo {

    private final String codigo;
    private TipoEquipo tipo;
    private String descripcion;
    private EstadoEquipo estado;

    public Equipo(String codigo, TipoEquipo tipo,
                  String descripcion, EstadoEquipo estado) {

        this.codigo = codigo;
        this.tipo = tipo;
        this.descripcion = descripcion;

        this.estado = java.util.Objects.requireNonNull(
            estado, "El estado no puede ser nulo"
        );
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoEquipo getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }

    public void setTipo(TipoEquipo tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(EstadoEquipo nuevoEstado) {

        if (nuevoEstado == null) {
            throw new IllegalArgumentException(
                "El nuevo estado no puede ser nulo"
            );
        }

        if (!this.estado.puedeCambiarA(nuevoEstado)) {
            throw new IllegalArgumentException(
                "No se puede cambiar de "
                + this.estado.getNombre()
                + " a " + nuevoEstado.getNombre()
            );
        }

        this.estado = nuevoEstado;
    }
}
