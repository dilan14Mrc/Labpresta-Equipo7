package com.labpresta.repositorio;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.TipoEquipo;
import com.labpresta.modelo.Disponible;
import com.labpresta.modelo.EstadoEquipo;
import com.labpresta.modelo.EnMantenimiento;
import com.labpresta.modelo.Prestado;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEquipoCSV implements Repositorio<Equipo> {

    private final String rutaArchivo = "equipos.csv";
    private List<Equipo> listaEquipos;

    public RepositorioEquipoCSV() {
        this.listaEquipos = new ArrayList<>();
        cargarDesdeCSV(); 
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

    private void cargarDesdeCSV() {
        File archivo = new File(rutaArchivo);
        
        if (!archivo.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                pw.println("codigo,tipo,descripcion,estado");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e.getMessage());
            }
            return; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine(); 
            
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                int c1 = linea.indexOf(',');
                int c2 = linea.indexOf(',', c1 + 1);
                int c3 = linea.lastIndexOf(',');

                if (c1 > 0 && c2 > c1 && c3 > c2) {
                    String codigo = linea.substring(0, c1).trim();
                    TipoEquipo tipo = TipoEquipo.valueOf(linea.substring(c1 + 1, c2).trim().toUpperCase());
                    String descripcion = linea.substring(c2 + 1, c3).trim();
                    String nombreEstado = linea.substring(c3 + 1).trim();
                    
                    EstadoEquipo estado;
                    if (nombreEstado.equalsIgnoreCase("Prestado")) {
                        estado = new Prestado();
                    } else if (nombreEstado.equalsIgnoreCase("En mantenimiento")) {
                        estado = new EnMantenimiento();
                    } else {
                        estado = new Disponible(); 
                    }
                    
                    Equipo eq = new Equipo(codigo, tipo, descripcion, estado); 
                    listaEquipos.add(eq);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
    }

    private void guardarEnCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            pw.println("codigo,tipo,descripcion,estado");
            for (Equipo e : listaEquipos) {
                pw.println(e.getCodigo() + "," + e.getTipo() + "," + 
                           e.getDescripcion() + "," + e.getEstado().getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}