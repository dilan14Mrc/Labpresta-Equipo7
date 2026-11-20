
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
    private JButton btnCambiarEstado;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;

    public VistaEquipos() {

        setTitle("LabPresta - Registro de equipos");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel del formulario
        JPanel panelFormulario = new JPanel(
                new GridLayout(5, 2, 10, 10));

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

        // Botón para cambiar el estado del equipo
        btnCambiarEstado = new JButton("Cambiar estado");
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnCambiarEstado);

        // Tabla de equipos
        modeloTabla = new DefaultTableModel(
                new String[]{"Código", "Tipo", "Descripción", "Estado"}, 0
        );

        tablaEquipos = new JTable(modeloTabla);

        // Organización de la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);
    }

    public String getCodigo() {
        return txtCodigo.getText().trim();
    }

    public TipoEquipo getTipoSeleccionado() {
        return (TipoEquipo) cmbTipo.getSelectedItem();
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnCambiarEstado() {
        return btnCambiarEstado;
    }

    // Obtener el código del equipo seleccionado
    public String getCodigoEquipoSeleccionado() {

        int fila = tablaEquipos.getSelectedRow();

        if (fila == -1) {
            return null;
        }

        int filaModelo = tablaEquipos.convertRowIndexToModel(fila);

        return modeloTabla.getValueAt(filaModelo, 0).toString();
    }

    // Actualizar el estado en la tabla
    public void actualizarEstadoEquipo(
            String codigo, String nuevoEstado) {

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {

            String codigoFila =
                    modeloTabla.getValueAt(i, 0).toString();

            if (codigoFila.equalsIgnoreCase(codigo)) {

                modeloTabla.setValueAt(nuevoEstado, i, 3);
                return;
            }
        }
    }

    public void agregarFila(
            String codigo,
            TipoEquipo tipo,
            String descripcion,
            String estado) {

        modeloTabla.addRow(
                new Object[]{codigo, tipo, descripcion, estado}
        );
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
