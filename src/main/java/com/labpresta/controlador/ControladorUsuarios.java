package com.labpresta.controlador;

import com.labpresta.modelo.TipoUsuario;
import com.labpresta.modelo.Usuario;
import com.labpresta.repositorio.Repositorio;
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
}