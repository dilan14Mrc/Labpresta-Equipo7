
package com.labpresta.repositorio;

import com.labpresta.modelo.Usuario;
import com.labpresta.modelo.TipoUsuario;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RepositorioUsuarioCSV implements Repositorio<Usuario> {

    private final String rutaArchivo = "usuarios.csv";
    private final List<Usuario> listaUsuarios;

    public RepositorioUsuarioCSV() {
        listaUsuarios = new ArrayList<>();
        cargarDesdeCSV();
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(listaUsuarios);
    }

    @Override
    public Usuario buscar(String cedula) {
        if (cedula == null) {
            return null;
        }

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCedula().equalsIgnoreCase(cedula.trim())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void guardar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        if (usuario.getCedula() == null || usuario.getCedula().isBlank()
                || usuario.getNombreCompleto() == null
                || usuario.getTelefono() == null
                || usuario.getTipo() == null) {
            throw new IllegalArgumentException("Datos incompletos del usuario.");
        }

        if (usuario.getCedula().contains(",")
                || usuario.getNombreCompleto().contains(",")
                || usuario.getTelefono().contains(",")) {
            throw new IllegalArgumentException(
                    "Los datos no pueden contener comas.");
        }

        List<Usuario> nuevaLista = new ArrayList<>(listaUsuarios);

        nuevaLista.removeIf(u ->
                u.getCedula().equalsIgnoreCase(usuario.getCedula()));

        nuevaLista.add(usuario);

        guardarEnCSV(nuevaLista);

        listaUsuarios.clear();
        listaUsuarios.addAll(nuevaLista);
    }

    private void cargarDesdeCSV() {
        Path archivo = Paths.get(rutaArchivo);

        try {
            if (Files.notExists(archivo)) {
                Files.writeString(
                        archivo,
                        "cedula,nombreCompleto,tipo,telefono"
                        + System.lineSeparator(),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE_NEW
                );

                System.out.println("usuarios.csv creado correctamente.");
                return;
            }

            try (BufferedReader br = Files.newBufferedReader(
                    archivo, StandardCharsets.UTF_8)) {

                String encabezado = br.readLine();

                if (!"cedula,nombreCompleto,tipo,telefono".equals(encabezado)) {
                    throw new IllegalStateException(
                            "Encabezado de usuarios.csv incorrecto.");
                }

                String linea;
                int numeroLinea = 1;

                while ((linea = br.readLine()) != null) {
                    numeroLinea++;

                    if (linea.isBlank()) {
                        continue;
                    }

                    String[] datos = linea.split(",", -1);

                    if (datos.length != 4) {
                        throw new IllegalStateException(
                                "Formato incorrecto en linea " + numeroLinea);
                    }

                    TipoUsuario tipo = TipoUsuario.valueOf(
                            datos[2].trim().toUpperCase(Locale.ROOT)
                    );

                    Usuario usuario = new Usuario(
                            datos[0].trim(),
                            datos[1].trim(),
                            tipo,
                            datos[3].trim()
                    );

                    listaUsuarios.add(usuario);
                }
            }

            System.out.println(
                    "Usuarios cargados desde CSV: "
                    + listaUsuarios.size()
            );

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Error al leer usuarios.csv", e);
        }
    }

    private void guardarEnCSV(List<Usuario> usuarios) {
        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(rutaArchivo), StandardCharsets.UTF_8)) {

            bw.write("cedula,nombreCompleto,tipo,telefono");
            bw.newLine();

            for (Usuario usuario : usuarios) {
                bw.write(
                        usuario.getCedula() + ","
                        + usuario.getNombreCompleto() + ","
                        + usuario.getTipo().name() + ","
                        + usuario.getTelefono()
                );
                bw.newLine();
            }

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Error al guardar usuarios.csv", e);
        }
    }
}
