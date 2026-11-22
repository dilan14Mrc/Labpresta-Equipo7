package com.labpresta.vista;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class VistaDevolucion extends JFrame {

    // Componentes visuales
    private JComboBox<String> cbPrestamoActivo;
    private JSpinner spFechaDevolucion;
    private JButton btnDevolver;

    public VistaDevolucion() {
        setTitle("Registrar Devolución - LabPresta");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Selección del préstamo
        panel.add(new JLabel("Seleccionar Préstamo Activo:"));
        cbPrestamoActivo = new JComboBox<>();
        panel.add(cbPrestamoActivo);

        // Fecha de devolución
        panel.add(new JLabel("Fecha de Devolución:"));
        spFechaDevolucion = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spFechaDevolucion, "dd/MM/yyyy");
        spFechaDevolucion.setEditor(editor);
        spFechaDevolucion.setValue(new Date()); // Por defecto, hoy
        panel.add(spFechaDevolucion);

        // Espacio y Botón
        panel.add(new JLabel("")); 
        btnDevolver = new JButton("Registrar Devolución");
        btnDevolver.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnDevolver);

        add(panel, BorderLayout.CENTER);
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