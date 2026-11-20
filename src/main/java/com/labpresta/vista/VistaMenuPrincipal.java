package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VistaMenuPrincipal extends JFrame {

    private static final Color AZUL_OSCURO = new Color(31, 56, 100);
    private static final Color AZUL = new Color(47, 84, 150);
    private static final Color AZUL_CLARO = new Color(68, 114, 196);
    private static final Color FONDO = new Color(244, 246, 250);

    private final JButton btnEquipos = new BotonMenu("Equipos", "Registrar y consultar el inventario");
    private final JButton btnUsuarios = new BotonMenu("Usuarios", "Registrar estudiantes y docentes");
    private final JButton btnPrestamos = new BotonMenu("Préstamos y devoluciones", "Prestar y recibir equipos");

    public VistaMenuPrincipal() {
        setTitle("LabPresta - Menú principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 480);
        setMinimumSize(new Dimension(400, 440));
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearBotones(), BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(AZUL_OSCURO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(28, 20, 24, 20));

        JLabel titulo = new JLabel("LabPresta");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Préstamo de equipos del laboratorio");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(200, 215, 240));
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(javax.swing.Box.createVerticalStrut(4));
        encabezado.add(subtitulo);
        return encabezado;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 14));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(26, 40, 14, 40));
        panel.add(btnEquipos);
        panel.add(btnUsuarios);
        panel.add(btnPrestamos);
        return panel;
    }

    private JPanel crearPie() {
        JPanel pie = new JPanel();
        pie.setBackground(FONDO);
        pie.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        JLabel texto = new JLabel("Equipo 7 · Proyecto IB", SwingConstants.CENTER);
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        texto.setForeground(new Color(130, 140, 160));
        pie.add(texto);
        return pie;
    }

    public JButton getBtnEquipos() {
        return btnEquipos;
    }

    public JButton getBtnUsuarios() {
        return btnUsuarios;
    }

    public JButton getBtnPrestamos() {
        return btnPrestamos;
    }

    public void avisar(String pantalla) {
        JOptionPane.showMessageDialog(this, "La pantalla de " + pantalla + " aún no está disponible.");
    }

    // Botón plano con esquinas redondeadas y efecto al pasar el mouse.
    private static class BotonMenu extends JButton {

        private final String titulo;
        private final String detalle;
        private boolean encima = false;

        BotonMenu(String titulo, String detalle) {
            this.titulo = titulo;
            this.detalle = detalle;
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    encima = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    encima = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Color fondo = getModel().isPressed() ? AZUL_OSCURO : (encima ? AZUL_CLARO : AZUL);
            g2.setColor(fondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

            Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 17);
            Font fuenteDetalle = new Font("Segoe UI", Font.PLAIN, 12);

            g2.setFont(fuenteTitulo);
            FontMetrics fmTitulo = g2.getFontMetrics();
            g2.setFont(fuenteDetalle);
            FontMetrics fmDetalle = g2.getFontMetrics();

            int altoTexto = fmTitulo.getHeight() + fmDetalle.getHeight();
            int y = (getHeight() - altoTexto) / 2 + fmTitulo.getAscent();

            g2.setFont(fuenteTitulo);
            g2.setColor(Color.WHITE);
            g2.drawString(titulo, (getWidth() - fmTitulo.stringWidth(titulo)) / 2, y);

            g2.setFont(fuenteDetalle);
            g2.setColor(new Color(215, 226, 245));
            g2.drawString(detalle, (getWidth() - fmDetalle.stringWidth(detalle)) / 2,
                    y + fmDetalle.getHeight());

            g2.dispose();
        }
    }
}