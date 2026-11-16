package com.labpresta.modelo;

public class Usuario {

    private String cedula;
    private String nombreCompleto;
    private TipoUsuario tipo;
    private String telefono;

    public Usuario(String cedula, String nombreCompleto,
                   TipoUsuario tipo, String telefono) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
