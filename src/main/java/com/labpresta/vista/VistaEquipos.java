package com.labpresta.vista;

import com.labpresta.modelo.TipoEquipo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaEquipos extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtDescripcion;
    private JComboBox<TipoEquipo> cmbTipo;
    private JButton btnRegistrar;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;

    public VistaEquipos() {

        setTitle("LabPresta - Registro de equipos");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Tipo de equipo:"));
        cmbTipo = new JComboBox<>(TipoEquipo.values());
        panelFormulario.add(cmbTipo);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        btnRegistrar = new JButton("Registrar");
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnRegistrar);

        // Tabla de equipos
        modeloTabla = new DefaultTableModel(
            new String[]{"Código", "Tipo", "Descripción", "Estado"}, 0
        );

        tablaEquipos = new JTable(modeloTabla);

        // Organización de la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);
    }
}