package com.labpresta;

import com.labpresta.controlador.ControladorMenu;
import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Prestamo;
import com.labpresta.modelo.Usuario;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.repositorio.RepositorioEquipoCSV;
import com.labpresta.repositorio.RepositorioPrestamoCSV;
import com.labpresta.repositorio.RepositorioUsuarioCSV;
import com.labpresta.vista.VistaMenuPrincipal;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Labpresta {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Cada repositorio se crea una sola vez y se comparte.
                Repositorio<Equipo> repositorioEquipos = new RepositorioEquipoCSV();
                Repositorio<Usuario> repositorioUsuarios = new RepositorioUsuarioCSV();
                Repositorio<Prestamo> repositorioPrestamos =
                        new RepositorioPrestamoCSV(repositorioEquipos, repositorioUsuarios);

                VistaMenuPrincipal menu = new VistaMenuPrincipal();
                new ControladorMenu(menu, repositorioEquipos, repositorioUsuarios, repositorioPrestamos);
                menu.setVisible(true);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo iniciar la aplicación:\n" + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
