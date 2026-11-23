package com.labpresta.controlador;

import com.labpresta.modelo.TipoUsuario;
import com.labpresta.modelo.Usuario;
import com.labpresta.repositorio.Repositorio;
import com.labpresta.vista.VistaUsuarios;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ControladorUsuarios {

    private final Repositorio<Usuario> repositorio;
    private final ValidadorUsuario validador;

    public ControladorUsuarios(Repositorio<Usuario> repositorio, ValidadorUsuario validador) {
        this.repositorio = Objects.requireNonNull(repositorio, "El repositorio no puede ser nulo");
        this.validador = Objects.requireNonNull(validador, "El validador no puede ser nulo");
    }

    // Valida, crea el usuario y lo guarda. Si algo está mal, el validador lanza
    // IllegalArgumentException con el mensaje que la vista debe mostrar.
    public Usuario registrar(String cedula, String nombreCompleto,
                             TipoUsuario tipo, String telefono) {
        validador.validar(cedula, nombreCompleto, tipo, telefono, repositorio);

        Usuario usuario = new Usuario(cedula.trim(), nombreCompleto.trim(), tipo, telefono.trim());
        repositorio.guardar(usuario);
        return usuario;
    }

    public List<Usuario> listar() {
        return repositorio.listar();
    }

    // Busca por cédula o por parte del nombre, sin importar mayúsculas,
    // minúsculas ni tildes. Si el texto está vacío, devuelve todos los usuarios.
    public List<Usuario> buscar(String texto) {
        String filtro = normalizar(texto);
        if (filtro.isEmpty()) {
            return repositorio.listar();
        }

        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : repositorio.listar()) {
            if (normalizar(u.getCedula()).contains(filtro)
                    || normalizar(u.getNombreCompleto()).contains(filtro)) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinTildes = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase();
    }

    public void conectar(VistaUsuarios vista) {
        refrescarTabla(vista, "");

        vista.getBtnRegistrar().addActionListener(e -> registrarDesdeVista(vista));

        // La búsqueda se actualiza mientras se escribe.
        vista.getCampoBusqueda().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refrescarTabla(vista, vista.getTxtBusqueda());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refrescarTabla(vista, vista.getTxtBusqueda());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refrescarTabla(vista, vista.getTxtBusqueda());
            }
        });
    }

    private void registrarDesdeVista(VistaUsuarios vista) {
        try {
            TipoUsuario tipo = convertirTipo(vista.getTipoSeleccionado());
            registrar(vista.getCedula(), vista.getNombreCompleto(), tipo, vista.getTelefono());
            vista.limpiarCampos();
            refrescarTabla(vista, "");
            vista.mostrarMensaje("Usuario registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

    // El selector de la vista devuelve texto; aquí se pasa al enum.
    private TipoUsuario convertirTipo(String texto) {
        if ("Estudiante".equals(texto)) {
            return TipoUsuario.ESTUDIANTE;
        }
        if ("Docente".equals(texto)) {
            return TipoUsuario.DOCENTE;
        }
        return null; // sin elegir: el validador mostrará el mensaje
    }

    private void refrescarTabla(VistaUsuarios vista, String texto) {
        vista.limpiarTabla();
        for (Usuario u : buscar(texto)) {
            String tipo = u.getTipo() == TipoUsuario.ESTUDIANTE ? "Estudiante" : "Docente";
            vista.agregarFila(u.getCedula(), u.getNombreCompleto(), tipo, u.getTelefono());
        }
    }
}