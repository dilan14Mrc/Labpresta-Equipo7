package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

    // Igual que el encabezado normal, pero con un botón "Volver" que cierra la ventana.
    public static JPanel encabezado(String titulo, String subtitulo, JFrame ventana) {
        JPanel panel = encabezado(titulo, subtitulo);
        JButton volver = new BotonVolver();
        volver.addActionListener(e -> ventana.dispose());
        JPanel lado = new JPanel(new GridBagLayout());
        lado.setOpaque(false);
        lado.add(volver);
        panel.add(lado, BorderLayout.EAST);
        return panel;
    }

    public static JButton botonSecundario(String texto) {
        return new BotonRedondeado(texto, true);
    }

    // Cuadro de aviso con el estilo de la aplicación (reemplaza al JOptionPane).
    public static void mensaje(Window padre, String texto) {
        JDialog dialogo = new JDialog(padre, "Aviso", Dialog.ModalityType.APPLICATION_MODAL);
        JPanel icono = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(AZUL_SUAVE);
                g2.fillOval(0, 0, 44, 44);
                g2.setColor(AZUL);
                g2.setFont(new Font(FUENTE, Font.BOLD, 26));
                FontMetrics fm = g2.getFontMetrics();
                int x = (44 - fm.stringWidth("i")) / 2;
                int y = (44 + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString("i", x, y);
                g2.dispose();
            }
        };
        icono.setOpaque(false);
        icono.setPreferredSize(new Dimension(44, 44));

        String seguro = texto == null ? "" : texto.replace("&", "&amp;").replace("<", "&lt;");
        JLabel lblMensaje = new JLabel("<html><body style='width:260px'>" + seguro + "</body></html>");
        lblMensaje.setFont(new Font(FUENTE, Font.PLAIN, 14));
        lblMensaje.setForeground(TEXTO);

        JButton aceptar = botonPrimario("Aceptar");
        aceptar.setPreferredSize(new Dimension(120, 40));
        aceptar.addActionListener(e -> dialogo.dispose());

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pie.setOpaque(false);
        pie.add(aceptar);

        JPanel contenido = new JPanel(new BorderLayout(18, 20));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(BorderFactory.createEmptyBorder(26, 26, 22, 26));
        contenido.add(icono, BorderLayout.WEST);
        contenido.add(lblMensaje, BorderLayout.CENTER);
        contenido.add(pie, BorderLayout.SOUTH);

        dialogo.setContentPane(contenido);
        dialogo.getRootPane().setDefaultButton(aceptar);
        dialogo.setResizable(false);
        dialogo.pack();
        dialogo.setLocationRelativeTo(padre);
        dialogo.setVisible(true);
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

    // Solo deja escribir letras y espacios (bloquea números y símbolos).
    public static void soloLetras(JTextField campo) {
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
                if (nuevo.matches("[\\p{L}\\s]*")) {
                    super.replace(fb, offset, largo, texto, a);
                }
            }
        });
    }

    // Convierte a mayúsculas lo que se escribe, hasta un máximo de caracteres.
    public static void mayusculas(JTextField campo, int maximo) {
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
                if (nuevo.length() <= maximo) {
                    super.replace(fb, offset, largo, texto.toUpperCase(), a);
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
        return new BotonRedondeado(texto, false);
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

    // Botón redondeado (primario azul o secundario blanco con borde azul).
    private static class BotonRedondeado extends JButton {

        private boolean encima = false;
        private final boolean secundario;

        BotonRedondeado(String texto, boolean secundario) {
            super(texto);
            this.secundario = secundario;
            setFont(new Font(FUENTE, Font.BOLD, 15));
            setForeground(secundario ? AZUL : Color.WHITE);
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
            if (secundario) {
                g2.setColor(encima || getModel().isPressed() ? AZUL_SUAVE : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.setColor(AZUL);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            } else {
                g2.setColor(getModel().isPressed() ? AZUL_OSCURO : (encima ? AZUL_CLARO : AZUL));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Botón "← Volver" translúcido para el encabezado azul.
    private static class BotonVolver extends JButton {

        private boolean encima = false;

        BotonVolver() {
            super("←  Volver");
            setFont(new Font(FUENTE, Font.BOLD, 14));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setPreferredSize(new Dimension(110, 38));
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
            g2.setColor(new Color(255, 255, 255, getModel().isPressed() ? 80 : (encima ? 55 : 28)));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}