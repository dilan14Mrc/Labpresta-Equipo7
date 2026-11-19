package com.labpresta.controlador;

import com.labpresta.modelo.TipoUsuario;
import com.labpresta.modelo.Usuario;
import com.labpresta.repositorio.Repositorio;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}