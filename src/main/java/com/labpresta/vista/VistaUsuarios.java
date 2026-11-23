package com.labpresta.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaUsuarios extends JFrame {

    // Declaración de los componentes
    private JTextField txtCedula;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cbTipo;
    private JTextField txtTelefono;
    private JButton btnRegistrar;
    private JTextField txtBusqueda;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public VistaUsuarios() {
        setTitle("Gestión de Usuarios - LabPresta");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Para que solo cierre esta ventana
        initUI(); // Llama al método que arma la ventana
    }

    private void initUI() {
        // Panel del Formulario 
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Usuario"));

        panelFormulario.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelFormulario.add(txtCedula);

        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombreCompleto = new JTextField();
        panelFormulario.add(txtNombreCompleto);

        panelFormulario.add(new JLabel("Tipo de Usuario:"));
        cbTipo = new JComboBox<>(new String[]{"", "Estudiante", "Docente"}); // Opciones
        panelFormulario.add(cbTipo);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("")); 
        btnRegistrar = new JButton("Registrar Usuario");
        panelFormulario.add(btnRegistrar);

        // Panel de Búsqueda y Tabla 
        JPanel panelTabla = new JPanel(new BorderLayout(5, 5));
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Usuarios"));
        
        // Caja de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar (Cédula o Nombre):"));
        txtBusqueda = new JTextField(25);
        panelBusqueda.add(txtBusqueda);
        panelTabla.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Cédula", "Nombre", "Tipo", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaUsuarios = new JTable(modeloTabla);
        panelTabla.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        // Agregar todo a la ventana 
        setLayout(new BorderLayout(10, 10));
        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
    }

    
    // MÉTODOS DE ACCESO 
    

    public String getCedula() { 
        return txtCedula.getText().trim(); 
    }
    
    public String getNombreCompleto() { 
        return txtNombreCompleto.getText().trim(); 
    }
    
    public String getTipoSeleccionado() { 
        return cbTipo.getSelectedItem().toString(); 
    }
    
    public String getTelefono() { 
        return txtTelefono.getText().trim(); 
    }
    
    public JButton getBtnRegistrar() { 
        return btnRegistrar; 
    }
    
    public String getTxtBusqueda() { 
        return txtBusqueda.getText().trim(); 
    }

    public void agregarFila(String cedula, String nombre, String tipo, String telefono) {
        modeloTabla.addRow(new Object[]{cedula, nombre, tipo, telefono});
    }

    public void limpiarCampos() {
        txtCedula.setText("");
        txtNombreCompleto.setText("");
        cbTipo.setSelectedIndex(0);
        txtTelefono.setText("");
        txtBusqueda.setText("");
        //Se eliminó modeloTabla.setRowCount(0); evitando que se borre la lista de usuarios
    }
    
    public JTextField getCampoBusqueda() {
        return txtBusqueda;
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}