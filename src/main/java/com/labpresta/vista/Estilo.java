package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

// Colores, fuentes y componentes compartidos por todas las pantallas.
public final class Estilo {

    public static final Color AZUL_OSCURO = new Color(24, 44, 82);
    public static final Color AZUL = new Color(47, 84, 150);
    public static final Color AZUL_CLARO = new Color(68, 114, 196);
    public static final Color AZUL_SUAVE = new Color(232, 240, 255);
    public static final Color FONDO = new Color(242, 245, 250);
    public static final Color TEXTO = new Color(33, 43, 66);
    public static final Color GRIS = new Color(110, 122, 145);
    public static final Color BORDE = new Color(205, 213, 228);
    public static final String FUENTE = "Segoe UI";

    private Estilo() {
    }

    // Franja azul oscura con el título de la pantalla.
    public static JPanel encabezado(String titulo, String subtitulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AZUL_OSCURO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel t = new JLabel(titulo);
        t.setFont(new Font(FUENTE, Font.BOLD, 24));
        t.setForeground(Color.WHITE);

        JLabel s = new JLabel(subtitulo);
        s.setFont(new Font(FUENTE, Font.PLAIN, 13));
        s.setForeground(new Color(170, 188, 222));

        panel.add(t, BorderLayout.CENTER);
        panel.add(s, BorderLayout.SOUTH);
        return panel;
    }

    public static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font(FUENTE, Font.BOLD, 13));
        l.setForeground(TEXTO);
        return l;
    }

    public static void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(new Font(FUENTE, Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(300, 38));
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(new Font(FUENTE, Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(300, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE), BorderFactory.createEmptyBorder(4, 10, 4, 10)));
    }
    
        // Campo de texto que muestra un ejemplo en gris mientras está vacío.
    public static JTextField campoConPista(String pista) {
        JTextField campo = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(new Color(160, 168, 185));
                    g2.setFont(getFont());
                    Insets in = getInsets();
                    int y = (getHeight() + g2.getFontMetrics().getAscent()
                            - g2.getFontMetrics().getDescent()) / 2;
                    g2.drawString(pista, in.left, y);
                    g2.dispose();
                }
            }
        };
        estilizarCampo(campo);
        return campo;
    }

    // Solo deja escribir dígitos, hasta un máximo de caracteres.
    public static void soloDigitos(JTextField campo, int maximo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String texto, AttributeSet a)
                    throws BadLocationException {
                replace(fb, offset, 0, texto, a);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int largo, String texto, AttributeSet a)
                    throws BadLocationException {
                if (texto == null) {
                    texto = "";
                }
                String actual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String nuevo = actual.substring(0, offset) + texto + actual.substring(offset + largo);
                if (nuevo.matches("\\d*") && nuevo.length() <= maximo) {
                    super.replace(fb, offset, largo, texto, a);
                }
            }
        });
    }

    public static void estilizarSpinner(JSpinner spinner) {
        spinner.setFont(new Font(FUENTE, Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(300, 38));
        spinner.setBorder(BorderFactory.createLineBorder(BORDE));
        if (spinner.getEditor() instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField()
                    .setFont(new Font(FUENTE, Font.PLAIN, 14));
        }
    }

    // Cuadro destacado para un dato calculado (por ejemplo, la fecha límite).
    public static void estilizarDato(JLabel dato) {
        dato.setOpaque(true);
        dato.setBackground(AZUL_SUAVE);
        dato.setForeground(AZUL);
        dato.setFont(new Font(FUENTE, Font.BOLD, 18));
        dato.setHorizontalAlignment(SwingConstants.LEFT);
        dato.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        dato.setPreferredSize(new Dimension(300, 42));
    }

    // Coloca una etiqueta y su campo, uno debajo del otro, en un formulario GridBag.
    public static void fila(JPanel formulario, int numero, String etiqueta, Component campo) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        g.gridy = numero * 2;
        g.insets = new Insets(numero == 0 ? 0 : 16, 0, 5, 0);
        formulario.add(etiqueta(etiqueta), g);

        g.gridy = numero * 2 + 1;
        g.insets = new Insets(0, 0, 0, 0);
        formulario.add(campo, g);
    }

    // Tarjeta blanca con esquinas redondeadas y una sombra suave.
    public static JPanel tarjeta() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 14));
                g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 18, 18);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 4, 18, 18);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(26, 28, 26, 31));
        return p;
    }

    public static JButton botonPrimario(String texto) {
        return new BotonRedondeado(texto);
    }

    // Tabla con encabezado azul, filas más altas y filas alternadas.
    public static void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(30);
        tabla.setFont(new Font(FUENTE, Font.PLAIN, 14));
        tabla.setGridColor(new Color(228, 233, 243));
        tabla.setShowVerticalLines(false);
        tabla.setSelectionBackground(new Color(200, 218, 250));
        tabla.setSelectionForeground(TEXTO);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 36));
        tabla.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer encabezado = new DefaultTableCellRenderer();
        encabezado.setBackground(AZUL);
        encabezado.setForeground(Color.WHITE);
        encabezado.setFont(new Font(FUENTE, Font.BOLD, 13));
        encabezado.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        for (int i = 0; i < tabla.getColumnModel().getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setHeaderRenderer(encabezado);
        }

        DefaultTableCellRenderer celdas = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object valor, boolean seleccionada,
                                                           boolean foco, int fila, int columna) {
                Component c = super.getTableCellRendererComponent(t, valor, seleccionada, foco, fila, columna);
                if (!seleccionada) {
                    c.setBackground(fila % 2 == 0 ? Color.WHITE : new Color(246, 248, 252));
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        };
        tabla.setDefaultRenderer(Object.class, celdas);
    }

    // Botón azul redondeado con efecto al pasar el mouse.
    private static class BotonRedondeado extends JButton {

        private boolean encima = false;

        BotonRedondeado(String texto) {
            super(texto);
            setFont(new Font(FUENTE, Font.BOLD, 15));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setPreferredSize(new Dimension(300, 44));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    encima = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    encima = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? AZUL_OSCURO : (encima ? AZUL_CLARO : AZUL));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}