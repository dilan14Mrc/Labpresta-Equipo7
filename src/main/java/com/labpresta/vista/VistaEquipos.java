package com.labpresta.vista;

import com.labpresta.modelo.TipoEquipo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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
        setSize(1000, 580);
        setMinimumSize(new Dimension(900, 520));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCodigo = new JTextField();
        Estilo.estilizarCampo(txtCodigo);
        cmbTipo = new JComboBox<>(TipoEquipo.values());
        Estilo.estilizarCombo(cmbTipo);
        txtDescripcion = new JTextField();
        Estilo.estilizarCampo(txtDescripcion);
        btnRegistrar = Estilo.botonPrimario("Registrar equipo");
        btnCambiarEstado = Estilo.botonPrimario("Cambiar estado");

        JPanel formulario = Estilo.tarjeta();
        formulario.setLayout(new GridBagLayout());
        Estilo.fila(formulario, 0, "Código", txtCodigo);
        Estilo.fila(formulario, 1, "Tipo de equipo", cmbTipo);
        Estilo.fila(formulario, 2, "Descripción", txtDescripcion);
        Estilo.fila(formulario, 3, "", btnRegistrar);
        Estilo.fila(formulario, 4, "", btnCambiarEstado);

        modeloTabla = new DefaultTableModel(
                new String[]{"Código", "Tipo", "Descripción", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaEquipos = new JTable(modeloTabla);
        Estilo.estilizarTabla(tablaEquipos);
        JScrollPane scroll = new JScrollPane(tablaEquipos);
        scroll.setBorder(BorderFactory.createLineBorder(Estilo.BORDE));
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel izquierda = new JPanel(new BorderLayout());
        izquierda.setOpaque(false);
        izquierda.add(formulario, BorderLayout.NORTH);

        JPanel cuerpo = new JPanel(new BorderLayout(20, 0));
        cuerpo.setBackground(Estilo.FONDO);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        cuerpo.add(izquierda, BorderLayout.WEST);
        cuerpo.add(scroll, BorderLayout.CENTER);

        add(Estilo.encabezado("Equipos", "Registre los equipos y cambie su estado"), BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
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
