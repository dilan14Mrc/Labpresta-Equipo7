package com.labpresta.controlador;

import com.labpresta.modelo.Prestamo;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaSeguimiento;
import java.time.format.DateTimeFormatter;

public class ControladorSeguimiento {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final VistaSeguimiento vista;
    private final Repositorio<Prestamo> repositorioPrestamos;

    public ControladorSeguimiento(VistaSeguimiento vista, Repositorio<Prestamo> repositorioPrestamos) {
        this.vista = vista;
        this.repositorioPrestamos = repositorioPrestamos;
        vista.getCmbFiltro().addActionListener(e -> cargar());
        cargar();
    }

    private void cargar() {
        vista.limpiar();
        String filtro = (String) vista.getCmbFiltro().getSelectedItem();
        int vencidos = 0;
        int atrasados = 0;
        for (Prestamo p : repositorioPrestamos.listar()) {
            boolean vencido = p.estaVencido();
            boolean atrasado = p.estaAtrasado();
            if (vencido) {
                vencidos++;
            }
            if (atrasado) {
                atrasados++;
            }
            if ("Vencidos".equals(filtro) && !vencido) {
                continue;
            }
            if ("Atrasados".equals(filtro) && !atrasado) {
                continue;
            }
            String estado;
            if (p.estaActivo()) {
                estado = vencido ? "VENCIDO" : "En plazo";
            } else {
                estado = atrasado ? "Devuelto con ATRASO" : "Devuelto a tiempo";
            }
            vista.agregarFila(
                    p.getEquipo().getCodigo(),
                    p.getUsuario().getNombreCompleto(),
                    String.valueOf(p.getUsuario().getTipo()),
                    p.getFechaPrestamo().format(FORMATO),
                    p.getFechaLimite().format(FORMATO),
                    p.estaActivo() ? "-" : p.getFechaDevolucion().format(FORMATO),
                    estado);
        }
        vista.setResumen("Vencidos: " + vencidos + "   |   Devueltos con atraso: " + atrasados);
    }
}