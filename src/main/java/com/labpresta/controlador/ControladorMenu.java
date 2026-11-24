package com.labpresta.controlador;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Prestamo;
import com.labpresta.modelo.Usuario;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaDevolucion;
import com.labpresta.vista.VistaEquipos;
import com.labpresta.vista.VistaMenuPrincipal;
import com.labpresta.vista.VistaPrestar;
import com.labpresta.vista.VistaSeguimiento;
import com.labpresta.vista.VistaUsuarios;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ControladorMenu {

    private final VistaMenuPrincipal vista;
    private final Repositorio<Equipo> repositorioEquipos;
    private final Repositorio<Usuario> repositorioUsuarios;
    private final Repositorio<Prestamo> repositorioPrestamos;

    private JFrame ventanaEquipos;
    private JFrame ventanaUsuarios;
    private JFrame ventanaPrestar;
    private JFrame ventanaDevolucion;
    private JFrame ventanaSeguimiento;

    public ControladorMenu(VistaMenuPrincipal vista,
                           Repositorio<Equipo> repositorioEquipos,
                           Repositorio<Usuario> repositorioUsuarios,
                           Repositorio<Prestamo> repositorioPrestamos) {
        this.vista = vista;
        this.repositorioEquipos = repositorioEquipos;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioPrestamos = repositorioPrestamos;

        vista.getBtnEquipos().addActionListener(e -> abrirEquipos());
        vista.getBtnUsuarios().addActionListener(e -> abrirUsuarios());
        vista.getBtnPrestamos().addActionListener(e -> abrirPrestamos());

        // El panel se actualiza al abrir y cada vez que el menú vuelve a tener el foco.
        actualizarResumen();
        vista.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                actualizarResumen();
            }
        });
    }

    private void actualizarResumen() {
        int disponibles = 0;
        int prestados = 0;
        int mantenimiento = 0;
        for (Equipo e : repositorioEquipos.listar()) {
            String estado = e.getEstado().getNombre();
            if ("Disponible".equals(estado)) {
                disponibles++;
            } else if ("Prestado".equals(estado)) {
                prestados++;
            } else {
                mantenimiento++;
            }
        }
        int activos = 0;
        for (Prestamo p : repositorioPrestamos.listar()) {
            if (p.estaVencido()) {
                activos++;
            }
        }
        vista.setResumen(repositorioEquipos.listar().size(), disponibles, prestados,
                mantenimiento, repositorioUsuarios.listar().size(), activos);
    }

    // Cada vez que se abre una pantalla se crea nueva, así siempre muestra
    // los datos más recientes (por ejemplo, el estado de los equipos).
    private void abrirEquipos() {
        cerrar(ventanaEquipos);
        VistaEquipos v = new VistaEquipos();
        new ControladorEquipos(v, repositorioEquipos);
        mostrar(v);
        ventanaEquipos = v;
    }

    private void abrirUsuarios() {
        cerrar(ventanaUsuarios);
        VistaUsuarios v = new VistaUsuarios();
        new ControladorUsuarios(repositorioUsuarios, new ValidadorUsuario()).conectar(v);
        mostrar(v);
        ventanaUsuarios = v;
    }

    private void abrirPrestamos() {
        int opcion = vista.elegirOperacionPrestamo();
        if (opcion == 0) {
            cerrar(ventanaPrestar);
            VistaPrestar v = new VistaPrestar();
            new ControladorPrestamos(v, repositorioEquipos, repositorioUsuarios, repositorioPrestamos);
            mostrar(v);
            ventanaPrestar = v;
        } else if (opcion == 1) {
            cerrar(ventanaDevolucion);
            VistaDevolucion v = new VistaDevolucion();
            new ControladorDevolucion(v, repositorioEquipos, repositorioPrestamos);
            mostrar(v);
            ventanaDevolucion = v;
        } else if (opcion == 2) {
            cerrar(ventanaSeguimiento);
            VistaSeguimiento v = new VistaSeguimiento();
            new ControladorSeguimiento(v, repositorioPrestamos);
            mostrar(v);
            ventanaSeguimiento = v;
        }
    }

    private void cerrar(JFrame ventana) {
        if (ventana != null) {
            ventana.dispose();
        }
    }

    private void mostrar(JFrame ventana) {
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vista.toFront();
            }
        });
        ventana.setVisible(true);
        ventana.toFront();
    }
}