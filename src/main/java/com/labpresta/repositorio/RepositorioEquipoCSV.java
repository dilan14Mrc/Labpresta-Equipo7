package com.labpresta.repositorio;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.TipoEquipo;
import com.labpresta.modelo.Disponible;
import com.labpresta.modelo.EstadoEquipo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEquipoCSV implements Repositorio<Equipo> {

    private final String rutaArchivo = "equipos.csv";
    private List<Equipo> listaEquipos;

    public RepositorioEquipoCSV() {
        this.listaEquipos = new ArrayList<>();
        cargarDesdeCSV(); // TAREA C1.7
    }

    @Override
    public List<Equipo> listar() {
        return listaEquipos;
    }

    @Override
    public void guardar(Equipo equipo) {
        listaEquipos.removeIf(e -> e.getCodigo().equals(equipo.getCodigo()));
        listaEquipos.add(equipo);
        guardarEnCSV();
    }

    @Override
    public Equipo buscar(String codigo) {
        for (Equipo e : listaEquipos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    // --- LECTURA DEL CSV (TAREA C1.7) ---
    private void cargarDesdeCSV() {
        File archivo = new File(rutaArchivo);
       
        // Si no existe, lo creamos con su encabezado
        if (!archivo.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                pw.println("codigo,tipo,descripcion,estado");
                System.out.println("Archivo equipos.csv no existía. Creado con éxito.");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e.getMessage());
            }
            return; 
        }

        // Si existe, lo leemos
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine(); // Saltar encabezado
            
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    String codigo = partes[0].trim();
                    TipoEquipo tipo = TipoEquipo.valueOf(partes[1].trim());
                    String descripcion = partes[2].trim();
                    
                    // Leemos el estado del CSV usando el patrón de Justin
                    String nombreEstado = partes[3].trim();
                    EstadoEquipo estado;
                    if (nombreEstado.equalsIgnoreCase("Prestado")) {
                        estado = new com.labpresta.modelo.Prestado();
                    } else if (nombreEstado.equalsIgnoreCase("En mantenimiento")) {
                        estado = new com.labpresta.modelo.EnMantenimiento();
                    } else {
                        estado = new Disponible(); // Por defecto
                    }
                    
                    // Usamos el constructor de 4 parámetros de Katherin
                    Equipo eq = new Equipo(codigo, tipo, descripcion, estado); 
                    listaEquipos.add(eq);
                }
            }
            System.out.println("Equipos cargados desde CSV: " + listaEquipos.size());
            
        } catch (Exception e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
    }

    // --- GUARDADO EN CSV (PARA LA SIGUIENTE TAREA C1.9) ---
    private void guardarEnCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            pw.println("codigo,tipo,descripcion,estado");
            for (Equipo e : listaEquipos) {
                // Método getNombre() que hizo Justin para el State
                pw.println(e.getCodigo() + "," + e.getTipo() + "," + 
                           e.getDescripcion() + "," + e.getEstado().getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}