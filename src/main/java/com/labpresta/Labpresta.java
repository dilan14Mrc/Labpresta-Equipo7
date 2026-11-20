package com.labpresta;

import com.labpresta.controlador.ControladorMenu;
import com.labpresta.modelo.Equipo;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.repositorio.RepositorioEquipoCSV;
import com.labpresta.vista.VistaMenuPrincipal;

public class Labpresta {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Repositorio<Equipo> repositorioEquipos = new RepositorioEquipoCSV();

            VistaMenuPrincipal menu = new VistaMenuPrincipal();
            new ControladorMenu(menu, repositorioEquipos);
            menu.setVisible(true);
        });
    }
}
