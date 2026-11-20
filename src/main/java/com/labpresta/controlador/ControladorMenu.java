package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaEquipos;
import com.labpresta.vista.VistaMenuPrincipal;

public class ControladorMenu {

    private final VistaMenuPrincipal vista;
    private final Repositorio<Equipo> repositorioEquipos;
    private VistaEquipos ventanaEquipos;

    public ControladorMenu(VistaMenuPrincipal vista, Repositorio<Equipo> repositorioEquipos) {
        this.vista = vista;
        this.repositorioEquipos = repositorioEquipos;

        vista.getBtnEquipos().addActionListener(e -> abrirEquipos());
        // Estos dos se conectan cuando estén listas sus pantallas.
        vista.getBtnUsuarios().addActionListener(e -> vista.avisar("Usuarios"));
        vista.getBtnPrestamos().addActionListener(e -> vista.avisar("Préstamos y devoluciones"));
    }

    // Abre la ventana de equipos. Si ya está abierta, solo la trae al frente
    // para no tener dos ventanas con datos distintos.
    private void abrirEquipos() {
        if (ventanaEquipos == null || !ventanaEquipos.isDisplayable()) {
            ventanaEquipos = new VistaEquipos();
            new ControladorEquipos(ventanaEquipos, repositorioEquipos);
        }
        ventanaEquipos.setVisible(true);
        ventanaEquipos.toFront();
    }
}