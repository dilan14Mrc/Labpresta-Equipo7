package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class VistaDevolucion extends JFrame {

    // Componentes visuales
    private JComboBox<String> cbPrestamoActivo;
    private JSpinner spFechaDevolucion;
    private JButton btnDevolver;

    public VistaDevolucion() {
        setTitle("Registrar devolución - LabPresta");
        setSize(480, 470);
        setMinimumSize(new Dimension(440, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        cbPrestamoActivo = new JComboBox<>();
        Estilo.estilizarCombo(cbPrestamoActivo);

        // Fecha de devolución: por defecto hoy, y se puede cambiar.
        spFechaDevolucion = new JSpinner(new SpinnerDateModel());
        spFechaDevolucion.setEditor(new JSpinner.DateEditor(spFechaDevolucion, "dd/MM/yyyy"));
        spFechaDevolucion.setValue(new Date());
        Estilo.estilizarSpinner(spFechaDevolucion);

        btnDevolver = Estilo.botonPrimario("Registrar devolución");

        JPanel formulario = Estilo.tarjeta();
        formulario.setLayout(new GridBagLayout());
        Estilo.fila(formulario, 0, "Préstamo activo (equipo - usuario - fecha)", cbPrestamoActivo);
        Estilo.fila(formulario, 1, "Fecha de devolución", spFechaDevolucion);
        Estilo.fila(formulario, 2, "", btnDevolver);

        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(Estilo.FONDO);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        cuerpo.add(formulario, BorderLayout.NORTH);

        add(Estilo.encabezado("Registrar devolución", "Elija el préstamo que se devuelve y la fecha"), BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
    }

    // MÉTODOS DE ACCESO
    // Para el controlador

    public JComboBox<String> getCbPrestamoActivo() {
        return cbPrestamoActivo;
    }

    public Date getFechaDevolucion() {
        return (Date) spFechaDevolucion.getValue();
    }

    public JButton getBtnDevolver() {
        return btnDevolver;
    }

    // Método para llenar el combo con los préstamos (ej: "LAP-01 - Juan Perez - 16/11/2026")
    public void agregarPrestamoCombo(String texto) {
        cbPrestamoActivo.addItem(texto);
    }

    // Limpiar los combos
    public void limpiarCombo() {
        cbPrestamoActivo.removeAllItems();
    }

    // Mostrar mensajes
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}