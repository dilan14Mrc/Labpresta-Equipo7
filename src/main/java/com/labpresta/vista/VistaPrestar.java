package com.labpresta.vista;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class VistaPrestar extends JFrame {

    // Componentes visuales
    private JComboBox<String> cbUsuario;
    private JComboBox<String> cbEquipo;
    private JSpinner spFechaPrestamo;
    private JLabel lblFechaLimite;
    private JButton btnPrestar;

    public VistaPrestar() {
        setTitle("Registrar Préstamo - LabPresta");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        // Panel principal con formulario
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Usuario
        panel.add(new JLabel("Seleccionar Usuario:"));
        cbUsuario = new JComboBox<>();
        panel.add(cbUsuario);

        // Equipo
        panel.add(new JLabel("Seleccionar Equipo Disponible:"));
        cbEquipo = new JComboBox<>();
        panel.add(cbEquipo);

        // Fecha de préstamo (JSpinner con formato de fecha)
        panel.add(new JLabel("Fecha de Préstamo:"));
        spFechaPrestamo = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spFechaPrestamo, "dd/MM/yyyy");
        spFechaPrestamo.setEditor(editor);
        spFechaPrestamo.setValue(new Date()); // Por defecto, hoy
        panel.add(spFechaPrestamo);

        // Fecha límite (Solo lectura, calculada por el Strategy de Justin)
        panel.add(new JLabel("Fecha Límite Calculada:"));
        lblFechaLimite = new JLabel("---");
        lblFechaLimite.setFont(new Font("Arial", Font.BOLD, 14));
        lblFechaLimite.setForeground(Color.BLUE);
        panel.add(lblFechaLimite);

        // Espacio y Botón
        panel.add(new JLabel("")); // Espacio vacío
        btnPrestar = new JButton("Registrar Préstamo");
        btnPrestar.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnPrestar);

        add(panel, BorderLayout.CENTER);
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

    // Método para que el controlador actualice la etiqueta azul
    public void setFechaLimite(String fecha) {
        lblFechaLimite.setText(fecha);
    }

    // Método para llenar el combo de usuarios (formato: "Cédula - Nombre")
    public void agregarUsuarioCombo(String texto) {
        cbUsuario.addItem(texto);
    }

    // Método para llenar el combo de equipos (formato: "Código - Descripción")
    public void agregarEquipoCombo(String texto) {
        cbEquipo.addItem(texto);
    }

    // Método para limpiar los combos cuando se registre un préstamo
    public void limpiarCombos() {
        cbUsuario.removeAllItems();
        cbEquipo.removeAllItems();
    }

    // Método para mostrar mensajes (errores o éxitos)
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}