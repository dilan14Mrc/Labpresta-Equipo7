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
        setSize(1000, 600);
        setMinimumSize(new Dimension(900, 540));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Para que solo cierre esta ventana
        initUI(); // Llama al método que arma la ventana
    }

    private void initUI() {
        txtCedula = Estilo.campoConPista("Ej: 1751906XXX");
        Estilo.soloDigitos(txtCedula, 10);
        txtNombreCompleto = Estilo.campoConPista("Ej: Juan Pérez");
        Estilo.soloLetras(txtNombreCompleto);
        txtTelefono = Estilo.campoConPista("Ej: 0991234567");
        Estilo.soloDigitos(txtTelefono, 10);
        cbTipo = new JComboBox<>(new String[]{"Seleccione tipo...", "Estudiante", "Docente"});
        Estilo.estilizarCombo(cbTipo);
        btnRegistrar = Estilo.botonPrimario("Registrar usuario");

        JPanel formulario = Estilo.tarjeta();
        formulario.setLayout(new GridBagLayout());
        Estilo.fila(formulario, 0, "Cédula", txtCedula);
        Estilo.fila(formulario, 1, "Nombre completo", txtNombreCompleto);
        Estilo.fila(formulario, 2, "Tipo de usuario", cbTipo);
        Estilo.fila(formulario, 3, "Teléfono", txtTelefono);
        Estilo.fila(formulario, 4, "", btnRegistrar);

        txtBusqueda = new JTextField();
        Estilo.estilizarCampo(txtBusqueda);
        txtBusqueda.setPreferredSize(new Dimension(300, 38));

        JPanel panelBusqueda = new JPanel(new BorderLayout(12, 0));
        panelBusqueda.setOpaque(false);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panelBusqueda.add(Estilo.etiqueta("Buscar por cédula o nombre"), BorderLayout.WEST);
        panelBusqueda.add(txtBusqueda, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(new Object[]{"Cédula", "Nombre", "Tipo", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloTabla);
        Estilo.estilizarTabla(tablaUsuarios);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBorder(BorderFactory.createLineBorder(Estilo.BORDE));
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel derecha = new JPanel(new BorderLayout());
        derecha.setOpaque(false);
        derecha.add(panelBusqueda, BorderLayout.NORTH);
        derecha.add(scroll, BorderLayout.CENTER);

        JPanel izquierda = new JPanel(new BorderLayout());
        izquierda.setOpaque(false);
        izquierda.add(formulario, BorderLayout.NORTH);

        JPanel cuerpo = new JPanel(new BorderLayout(20, 0));
        cuerpo.setBackground(Estilo.FONDO);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        cuerpo.add(izquierda, BorderLayout.WEST);
        cuerpo.add(derecha, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(Estilo.encabezado("Usuarios", "Registre y busque estudiantes y docentes"), BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
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