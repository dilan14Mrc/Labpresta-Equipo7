package com.labpresta.vista;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VistaMenuPrincipal extends JFrame {

    private final JButton btnEquipos = new JButton("Equipos");
    private final JButton btnUsuarios = new JButton("Usuarios");
    private final JButton btnPrestamos = new JButton("Préstamos y devoluciones");

    public VistaMenuPrincipal() {
        setTitle("LabPresta - Menú principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("LabPresta", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(24f));

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.add(titulo);
        panel.add(btnEquipos);
        panel.add(btnUsuarios);
        panel.add(btnPrestamos);
        add(panel);

        // Por ahora solo avisan. Cuando las otras ventanas estén subidas,
        // se cambia cada mensaje por: new VistaX().setVisible(true);
        btnEquipos.addActionListener(e -> avisar("Equipos"));
        btnUsuarios.addActionListener(e -> avisar("Usuarios"));
        btnPrestamos.addActionListener(e -> avisar("Préstamos y devoluciones"));
    }

    private void avisar(String pantalla) {
        JOptionPane.showMessageDialog(this, "La pantalla de " + pantalla + " aún no está disponible.");
    }
}