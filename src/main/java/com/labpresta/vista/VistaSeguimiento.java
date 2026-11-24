package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class VistaSeguimiento extends JFrame {

    private final JComboBox<String> cmbFiltro = new JComboBox<>(new String[]{"Todos", "Vencidos", "Atrasados"});
    private final JLabel lblResumen = new JLabel(" ");
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Equipo", "Usuario", "Tipo", "Préstamo", "Fecha límite", "Devolución", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public VistaSeguimiento() {
        setTitle("LabPresta - Seguimiento de préstamos");
        setSize(1000, 560);
        setMinimumSize(new Dimension(900, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Estilo.estilizarCombo(cmbFiltro);
        cmbFiltro.setPreferredSize(new Dimension(160, 36));
        lblResumen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResumen.setForeground(Estilo.TEXTO);

        Estilo.estilizarTabla(tabla);
        tabla.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object valor, boolean sel,
                                                           boolean foco, int fila, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(t, valor, sel, foco, fila, col);
                String texto = String.valueOf(valor);
                c.setFont(new Font("Segoe UI", Font.BOLD, 13));
                c.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (texto.contains("VENCIDO") || texto.contains("ATRASO")) {
                    c.setForeground(new Color(200, 45, 55));
                } else if (texto.contains("a tiempo")) {
                    c.setForeground(new Color(30, 140, 85));
                } else {
                    c.setForeground(Estilo.GRIS);
                }
                if (!sel) {
                    c.setBackground(fila % 2 == 0 ? Color.WHITE : new Color(246, 248, 252));
                }
                return c;
            }
        });

        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filtro.setOpaque(false);
        filtro.add(Estilo.etiqueta("Mostrar:"));
        filtro.add(cmbFiltro);
        filtro.add(lblResumen);

        JPanel cuerpo = new JPanel(new BorderLayout(0, 14));
        cuerpo.setBackground(Estilo.FONDO);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(20, 28, 24, 28));
        cuerpo.add(filtro, BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(tabla), BorderLayout.CENTER);

        add(Estilo.encabezado("Seguimiento de préstamos",
                "Vea qué préstamos están vencidos y cuáles se devolvieron con atraso", this), BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
    }

    public JComboBox<String> getCmbFiltro() {
        return cmbFiltro;
    }

    public void limpiar() {
        modelo.setRowCount(0);
    }

    public void agregarFila(Object... datos) {
        modelo.addRow(datos);
    }

    public void setResumen(String texto) {
        lblResumen.setText(texto);
    }
}