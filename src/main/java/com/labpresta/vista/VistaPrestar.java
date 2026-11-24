package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeListener;

public class VistaPrestar extends JFrame {

    // Componentes visuales
    private JComboBox<String> cbUsuario;
    private JComboBox<String> cbEquipo;
    private JSpinner spFechaPrestamo;
    private JLabel lblFechaLimite;
    private JButton btnPrestar;

    public VistaPrestar() {
        setTitle("Registrar préstamo - LabPresta");
        setSize(480, 600);
        setMinimumSize(new Dimension(440, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        cbUsuario = new JComboBox<>();
        Estilo.estilizarCombo(cbUsuario);

        cbEquipo = new JComboBox<>();
        Estilo.estilizarCombo(cbEquipo);

        // Fecha de préstamo: por defecto hoy, y se puede cambiar.
        spFechaPrestamo = new JSpinner(new SpinnerDateModel());
        spFechaPrestamo.setEditor(new JSpinner.DateEditor(spFechaPrestamo, "dd/MM/yyyy"));
        spFechaPrestamo.setValue(new Date());
        Estilo.estilizarSpinner(spFechaPrestamo);

        // Fecha límite: solo lectura, se calcula según el tipo de usuario.
        lblFechaLimite = new JLabel("---");
        Estilo.estilizarDato(lblFechaLimite);

        btnPrestar = Estilo.botonPrimario("Registrar préstamo");

        JPanel formulario = Estilo.tarjeta();
        formulario.setLayout(new GridBagLayout());
        Estilo.fila(formulario, 0, "Usuario", cbUsuario);
        Estilo.fila(formulario, 1, "Equipo (se muestra su estado)", cbEquipo);
        Estilo.fila(formulario, 2, "Fecha de préstamo", spFechaPrestamo);
        Estilo.fila(formulario, 3, "Fecha límite de devolución", lblFechaLimite);
        Estilo.fila(formulario, 4, "", btnPrestar);

        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(Estilo.FONDO);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        cuerpo.add(formulario, BorderLayout.NORTH);

        add(Estilo.encabezado("Registrar préstamo", "Elija el usuario, el equipo y la fecha"), BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
    }

    // MÉTODOS DE ACCESO

    public JComboBox<String> getCbUsuario() {
        return cbUsuario;
    }

    public JComboBox<String> getCbEquipo() {
        return cbEquipo;
    }

    public Date getFechaPrestamo() {
        return (Date) spFechaPrestamo.getValue();
    }

    public JButton getBtnPrestar() {
        return btnPrestar;
    }

    // Para que el controlador recalcule la fecha límite al cambiar la fecha.
    public void agregarListenerFecha(ChangeListener listener) {
        spFechaPrestamo.addChangeListener(listener);
    }

    // Método para que el controlador actualice la fecha límite
    public void setFechaLimite(String fecha) {
        lblFechaLimite.setText(fecha);
    }

    // Método para llenar el combo de usuarios (formato: "Cédula - Nombre")
    public void agregarUsuarioCombo(String texto) {
        cbUsuario.addItem(texto);
    }

    // Método para llenar el combo de equipos (formato: "Código - Descripción (Estado)")
    public void agregarEquipoCombo(String texto) {
        cbEquipo.addItem(texto);
    }

    // Método para limpiar los combos cuando se registre un préstamo
        public void limpiarCombos() {
        cbUsuario.removeAllItems();
        cbEquipo.removeAllItems();
        cbUsuario.addItem("Seleccione usuario...");
        cbEquipo.addItem("Seleccione equipo...");
    }

    // Método para mostrar mensajes (errores o éxitos)
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}