package com.labpresta.vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VistaMenuPrincipal extends JFrame {

    private static final Color AZUL_OSCURO = new Color(24, 44, 82);
    private static final Color AZUL = new Color(47, 84, 150);
    private static final Color FONDO = new Color(242, 245, 250);
    private static final Color TEXTO = new Color(33, 43, 66);
    private static final Color GRIS = new Color(110, 122, 145);
    private static final String FUENTE = "Segoe UI";

    private final JButton btnEquipos = new BotonLateral("Equipos", "equipos");
    private final JButton btnUsuarios = new BotonLateral("Usuarios", "usuarios");
    private final JButton btnPrestamos = new BotonLateral("Préstamos", "prestamos");

    private final JLabel numEquipos = crearNumero();
    private final JLabel numDisponibles = crearNumero();
    private final JLabel numPrestados = crearNumero();
    private final JLabel numMantenimiento = crearNumero();
    private final JLabel numUsuarios = crearNumero();
    private final JLabel numActivos = crearNumero();

    public VistaMenuPrincipal() {
        setTitle("LabPresta - Préstamo de equipos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setMinimumSize(new Dimension(860, 540));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearBarraLateral(), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    // ---------- Construcción de la pantalla ----------

    private JPanel crearBarraLateral() {
        JPanel barra = new JPanel();
        barra.setLayout(new BoxLayout(barra, BoxLayout.Y_AXIS));
        barra.setBackground(AZUL_OSCURO);
        barra.setPreferredSize(new Dimension(240, 0));

        JLabel logo = new JLabel("LabPresta");
        logo.setFont(new Font(FUENTE, Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lema = new JLabel("Préstamo de equipos");
        lema.setFont(new Font(FUENTE, Font.PLAIN, 13));
        lema.setForeground(new Color(170, 188, 222));
        lema.setAlignmentX(LEFT_ALIGNMENT);

        JPanel cabecera = new JPanel();
        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setOpaque(false);
        cabecera.setBorder(BorderFactory.createEmptyBorder(30, 24, 30, 24));
        cabecera.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.setMaximumSize(new Dimension(240, 110));
        cabecera.add(logo);
        cabecera.add(Box.createVerticalStrut(2));
        cabecera.add(lema);

        JLabel seccion = new JLabel("MENÚ");
        seccion.setFont(new Font(FUENTE, Font.BOLD, 11));
        seccion.setForeground(new Color(130, 150, 190));
        seccion.setBorder(BorderFactory.createEmptyBorder(0, 24, 8, 0));
        seccion.setAlignmentX(LEFT_ALIGNMENT);

        barra.add(cabecera);
        barra.add(seccion);
        barra.add(btnEquipos);
        barra.add(btnUsuarios);
        barra.add(btnPrestamos);
        barra.add(Box.createVerticalGlue());

        JLabel pie = new JLabel("Equipo 7 · Proyecto IB");
        pie.setFont(new Font(FUENTE, Font.PLAIN, 11));
        pie.setForeground(new Color(130, 150, 190));
        pie.setBorder(BorderFactory.createEmptyBorder(0, 24, 20, 0));
        pie.setAlignmentX(LEFT_ALIGNMENT);
        barra.add(pie);
        return barra;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(30, 36, 30, 36));

        JLabel titulo = new JLabel("Panel principal");
        titulo.setFont(new Font(FUENTE, Font.BOLD, 28));
        titulo.setForeground(TEXTO);

        JLabel fecha = new JLabel(fechaDeHoy());
        fecha.setFont(new Font(FUENTE, Font.PLAIN, 14));
        fecha.setForeground(GRIS);

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);
        encabezado.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        fecha.setAlignmentX(LEFT_ALIGNMENT);
        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(fecha);

        JPanel tarjetas = new JPanel(new GridLayout(2, 3, 20, 20));
        tarjetas.setOpaque(false);
        tarjetas.add(new Tarjeta("Equipos registrados", numEquipos, new Color(47, 84, 150), "equipos"));
        tarjetas.add(new Tarjeta("Disponibles", numDisponibles, new Color(46, 160, 100), "disponibles"));
        tarjetas.add(new Tarjeta("Prestados", numPrestados, new Color(235, 130, 40), "prestados"));
        tarjetas.add(new Tarjeta("En mantenimiento", numMantenimiento, new Color(150, 110, 200), "mantenimiento"));
        tarjetas.add(new Tarjeta("Usuarios registrados", numUsuarios, new Color(30, 150, 190), "usuarios"));
        tarjetas.add(new Tarjeta("Préstamos vencidos", numActivos, new Color(215, 80, 90), "activos"));

        JLabel ayuda = new JLabel("Use el menú de la izquierda para registrar equipos y usuarios, y para prestar o devolver equipos.");
        ayuda.setFont(new Font(FUENTE, Font.PLAIN, 15));
        ayuda.setForeground(new Color(70, 82, 110));
        ayuda.setBorder(BorderFactory.createEmptyBorder(22, 0, 0, 0));

        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(tarjetas, BorderLayout.CENTER);
        contenido.add(ayuda, BorderLayout.SOUTH);
        return contenido;
    }

    private static JLabel crearNumero() {
        JLabel l = new JLabel("0");
        l.setFont(new Font(FUENTE, Font.BOLD, 48));
        l.setForeground(TEXTO);
        return l;
    }

    private static String fechaDeHoy() {
        String texto = LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "EC")));
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    // ---------- Métodos que usa el controlador ----------

    public JButton getBtnEquipos() {
        return btnEquipos;
    }

    public JButton getBtnUsuarios() {
        return btnUsuarios;
    }

    public JButton getBtnPrestamos() {
        return btnPrestamos;
    }

    public void setResumen(int equipos, int disponibles, int prestados,
                           int mantenimiento, int usuarios, int prestamosActivos) {
        numEquipos.setText(String.valueOf(equipos));
        numDisponibles.setText(String.valueOf(disponibles));
        numPrestados.setText(String.valueOf(prestados));
        numMantenimiento.setText(String.valueOf(mantenimiento));
        numUsuarios.setText(String.valueOf(usuarios));
        numActivos.setText(String.valueOf(prestamosActivos));
    }

    // Devuelve 0 Prestar, 1 Devolver, 2 Seguimiento y -1 si cierra el cuadro.
    public int elegirOperacionPrestamo() {
        final int[] eleccion = {-1};
        JDialog dialogo = new JDialog(this, "Préstamos y devoluciones", true);
        JButton btnPrestar = Estilo.botonPrimario("Prestar equipo");
        JButton btnDevolver = Estilo.botonPrimario("Devolver equipo");
        JButton btnCancelar = Estilo.botonSecundario("Cancelar");
        JButton btnSeguimiento = Estilo.botonPrimario("Ver seguimiento (vencidos y atrasos)");

        btnPrestar.addActionListener(e -> {
            eleccion[0] = 0;
            dialogo.dispose();
        });
        btnDevolver.addActionListener(e -> {
            eleccion[0] = 1;
            dialogo.dispose();
        });
        btnSeguimiento.addActionListener(e -> {
            eleccion[0] = 2;
            dialogo.dispose();
        });
        btnCancelar.addActionListener(e -> dialogo.dispose());

        JPanel cuerpo = new JPanel(new GridLayout(4, 1, 0, 12));
        cuerpo.setBackground(FONDO);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        cuerpo.add(btnPrestar);
        cuerpo.add(btnDevolver);
        cuerpo.add(btnSeguimiento);
        cuerpo.add(btnCancelar);

        dialogo.add(Estilo.encabezado("Préstamos", "¿Qué desea hacer?"), BorderLayout.NORTH);
        dialogo.add(cuerpo, BorderLayout.CENTER);
        dialogo.setResizable(false);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
        return eleccion[0];
    }

    public void avisar(String pantalla) {
        Estilo.mensaje(this, "La pantalla de " + pantalla + " aún no está disponible.");
    }

    // ---------- Componentes con diseño propio ----------

    // Botón de la barra lateral: icono dibujado, texto y efecto al pasar el mouse.
    private static class BotonLateral extends JButton {

        private final String texto;
        private final String icono;
        private boolean encima = false;

        BotonLateral(String texto, String icono) {
            this.texto = texto;
            this.icono = icono;
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setAlignmentX(LEFT_ALIGNMENT);
            setPreferredSize(new Dimension(240, 50));
            setMinimumSize(new Dimension(240, 50));
            setMaximumSize(new Dimension(240, 50));
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

            if (getModel().isPressed()) {
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillRect(0, 0, getWidth(), getHeight());
            } else if (encima) {
                g2.setColor(new Color(255, 255, 255, 28));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
            if (encima) {
                g2.setColor(new Color(110, 170, 255));
                g2.fillRect(0, 0, 4, getHeight());
            }

            Color colorIcono = encima ? Color.WHITE : new Color(200, 214, 240);
            dibujarIcono(g2, icono, 24, (getHeight() - 22) / 2, 22, colorIcono);

            g2.setFont(new Font(FUENTE, Font.PLAIN, 15));
            g2.setColor(encima ? Color.WHITE : new Color(215, 226, 245));
            int y = (getHeight() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;
            g2.drawString(texto, 60, y);
            g2.dispose();
        }

        private static void dibujarIcono(Graphics2D g2, String tipo, int x, int y, int s, Color color) {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            if ("equipos".equals(tipo)) {
                // portátil: pantalla y base
                g2.drawRoundRect(x + 3, y + 3, s - 6, s - 10, 3, 3);
                g2.drawLine(x, y + s - 3, x + s, y + s - 3);
            } else if ("usuarios".equals(tipo)) {
                // persona: cabeza y hombros
                g2.drawOval(x + s / 2 - 4, y + 1, 8, 8);
                g2.drawArc(x + 2, y + 11, s - 4, s - 2, 0, 180);
            } else {
                // préstamos: dos flechas en sentidos opuestos
                g2.drawLine(x + 1, y + 7, x + s - 2, y + 7);
                g2.drawLine(x + s - 7, y + 3, x + s - 2, y + 7);
                g2.drawLine(x + s - 7, y + 11, x + s - 2, y + 7);
                g2.drawLine(x + 1, y + 16, x + s - 2, y + 16);
                g2.drawLine(x + 6, y + 12, x + 1, y + 16);
                g2.drawLine(x + 6, y + 20, x + 1, y + 16);
            }
        }
    }

    // Tarjeta blanca con franja de color, título legible, número grande e icono.
    private static class Tarjeta extends JPanel {

        private final Color acento;
        private final String icono;

        Tarjeta(String titulo, JLabel numero, Color acento, String icono) {
            this.acento = acento;
            this.icono = icono;
            setOpaque(false);
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(20, 28, 18, 16));

            JLabel etiqueta = new JLabel(titulo);
            etiqueta.setFont(new Font(FUENTE, Font.BOLD, 16));
            etiqueta.setForeground(new Color(60, 72, 100));
            numero.setForeground(acento);

            add(etiqueta, BorderLayout.NORTH);
            add(numero, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();

            g2.setColor(new Color(0, 0, 0, 14));
            g2.fillRoundRect(2, 4, w - 4, h - 4, 18, 18);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, w - 3, h - 4, 18, 18);

            g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, w - 3, h - 4, 18, 18));
            g2.setColor(acento);
            g2.fillRect(0, 0, 7, h);
            g2.setClip(null);

            // círculo suave con el icono, abajo a la derecha
            int cx = w - 3 - 16 - 28;
            int cy = (h - 4) / 2 + 16;
            g2.setColor(new Color(acento.getRed(), acento.getGreen(), acento.getBlue(), 35));
            g2.fillOval(cx - 28, cy - 28, 56, 56);
            g2.setColor(acento);
            g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            dibujar(g2, cx, cy);
            g2.dispose();
        }

        private void dibujar(Graphics2D g2, int cx, int cy) {
            switch (icono) {
                case "equipos":
                    g2.drawRoundRect(cx - 11, cy - 10, 22, 15, 3, 3);
                    g2.drawLine(cx - 15, cy + 9, cx + 15, cy + 9);
                    break;
                case "disponibles":
                    g2.drawLine(cx - 10, cy, cx - 3, cy + 7);
                    g2.drawLine(cx - 3, cy + 7, cx + 10, cy - 8);
                    break;
                case "prestados":
                    g2.drawLine(cx - 11, cy - 6, cx + 11, cy - 6);
                    g2.drawLine(cx + 5, cy - 12, cx + 11, cy - 6);
                    g2.drawLine(cx + 5, cy, cx + 11, cy - 6);
                    g2.drawLine(cx - 11, cy + 7, cx + 11, cy + 7);
                    g2.drawLine(cx - 5, cy + 1, cx - 11, cy + 7);
                    g2.drawLine(cx - 5, cy + 13, cx - 11, cy + 7);
                    break;
                case "mantenimiento":
                    g2.setStroke(new BasicStroke(4.5f));
                    g2.drawOval(cx - 7, cy - 7, 14, 14);
                    g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                    for (int i = 0; i < 8; i++) {
                        double a = Math.PI / 4 * i;
                        g2.drawLine(cx + (int) (9 * Math.cos(a)), cy + (int) (9 * Math.sin(a)),
                                cx + (int) (14 * Math.cos(a)), cy + (int) (14 * Math.sin(a)));
                    }
                    break;
                case "usuarios":
                    g2.drawOval(cx - 5, cy - 13, 10, 10);
                    g2.drawArc(cx - 11, cy, 22, 20, 0, 180);
                    break;
                default:
                    g2.drawOval(cx - 12, cy - 12, 24, 24);
                    g2.drawLine(cx, cy, cx, cy - 8);
                    g2.drawLine(cx, cy, cx + 6, cy + 3);
            }
        }
    }
}