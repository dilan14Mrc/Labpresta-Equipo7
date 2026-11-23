
package com.labpresta.repositorio;

import com.labpresta.modelo.Prestamo;
import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPrestamoCSV implements Repositorio<Prestamo> {

    private final String rutaArchivo = "prestamos.csv";
    private final List<Prestamo> listaPrestamos;

    private final Repositorio<Equipo> repositorioEquipos;
    private final Repositorio<Usuario> repositorioUsuarios;

    public RepositorioPrestamoCSV(
            Repositorio<Equipo> repositorioEquipos,
            Repositorio<Usuario> repositorioUsuarios) {

        this.repositorioEquipos = repositorioEquipos;
        this.repositorioUsuarios = repositorioUsuarios;
        this.listaPrestamos = new ArrayList<>();

        cargarDesdeCSV();
    }

    @Override
    public List<Prestamo> listar() {
        return new ArrayList<>(listaPrestamos);
    }
    
    @Override
    public Prestamo buscar(String codigoEquipo) {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.getEquipo().getCodigo()
                .equalsIgnoreCase(codigoEquipo)) {
            return prestamo;
        }
    }
    return null;
}
    @Override
    public void guardar(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException(
            "El préstamo no puede ser nulo.");
    }
        // Si el préstamo ya está en la lista (por ejemplo, al devolverlo),
        // solo se reescribe el archivo para no duplicarlo.
        if (!listaPrestamos.contains(prestamo)) {
            listaPrestamos.add(prestamo);
        }
        guardarEnCSV();
    }

    private void cargarDesdeCSV() {
        Path archivo = Paths.get(rutaArchivo);

        try {
            if (Files.notExists(archivo)) {
                Files.writeString(
                        archivo,
                        "codigoEquipo,cedulaUsuario,fechaPrestamo,fechaLimite,fechaDevolucion"
                                + System.lineSeparator(),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE_NEW
                );
                return;
            }

            try (BufferedReader br = Files.newBufferedReader(
                    archivo, StandardCharsets.UTF_8)) {

                String encabezado = br.readLine();

                if (!"codigoEquipo,cedulaUsuario,fechaPrestamo,fechaLimite,fechaDevolucion"
                        .equals(encabezado)) {
                    throw new IllegalStateException(
                            "Encabezado incorrecto en prestamos.csv");
                }

                String linea;
                int numeroLinea = 1;

                while ((linea = br.readLine()) != null) {
                    numeroLinea++;

                    if (linea.isBlank()) {
                        continue;
                    }

                    String[] datos = linea.split(",", -1);

                    if (datos.length != 5) {
                        throw new IllegalStateException(
                                "Formato incorrecto en la línea "
                                + numeroLinea);
                    }

                    String codigoEquipo = datos[0].trim();
                    String cedulaUsuario = datos[1].trim();

                    Equipo equipo = repositorioEquipos.buscar(codigoEquipo);
                    Usuario usuario = repositorioUsuarios.buscar(cedulaUsuario);

                    if (equipo == null || usuario == null) {
                        throw new IllegalStateException(
                                "No se encontró el equipo o usuario "
                                + "de la línea " + numeroLinea);
                    }

                    LocalDate fechaPrestamo =
                            LocalDate.parse(datos[2].trim());

                    LocalDate fechaLimite =
                            LocalDate.parse(datos[3].trim());

                    LocalDate fechaDevolucion = datos[4].isBlank()
                            ? null
                            : LocalDate.parse(datos[4].trim());

                    Prestamo prestamo = new Prestamo(
                            equipo,
                            usuario,
                            fechaPrestamo,
                            fechaLimite,
                            fechaDevolucion
                    );

                    listaPrestamos.add(prestamo);
                }
            }

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Error al leer prestamos.csv", e);
        }
    }
    
    private void guardarEnCSV() {
        try (java.io.BufferedWriter bw = java.nio.file.Files.newBufferedWriter(
                java.nio.file.Paths.get(rutaArchivo),
                java.nio.charset.StandardCharsets.UTF_8)) {
            bw.write("codigoEquipo,cedulaUsuario,fechaPrestamo,fechaLimite,fechaDevolucion");
            bw.newLine();
            
            for (Prestamo prestamo : listaPrestamos) {
                bw.write(
                        prestamo.getEquipo().getCodigo() + ","
                                + prestamo.getUsuario().getCedula() + ","
                                + prestamo.getFechaPrestamo() + ","
                                + prestamo.getFechaLimite() + ","
                                + (prestamo.getFechaDevolucion() == null
                                        ? ""
                            : prestamo.getFechaDevolucion())
            );
            bw.newLine();
        }
        } catch (java.io.IOException e) {
            throw new java.io.UncheckedIOException(
                "Error al guardar prestamos.csv", e);
    }
    }
}

