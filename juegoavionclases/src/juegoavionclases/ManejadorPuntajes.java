package juegoavionclases;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ManejadorPuntajes {
    private ArrayList<RegistroPuntaje> mejoresPuntajes;
    private String archivoGuardado = "puntajes.txt";
    private final int MAX_PUNTAJES = 3;
    
    public ManejadorPuntajes() {
        mejoresPuntajes = new ArrayList<>();
        cargarPuntajes();
    }
    
    public void agregarPuntaje(String nombreJugador, int puntos) {
        RegistroPuntaje nuevoPuntaje = new RegistroPuntaje(nombreJugador, puntos);
        mejoresPuntajes.add(nuevoPuntaje);
        
        // Ordenar por puntaje descendente
        Collections.sort(mejoresPuntajes, new Comparator<RegistroPuntaje>() {
            @Override
            public int compare(RegistroPuntaje p1, RegistroPuntaje p2) {
                return Integer.compare(p2.getPuntos(), p1.getPuntos());
            }
        });
        
        // Mantener solo los mejores 3 puntajes
        while (mejoresPuntajes.size() > MAX_PUNTAJES) {
            mejoresPuntajes.remove(mejoresPuntajes.size() - 1);
        }
        
        guardarPuntajes();
    }
    
    public boolean esNuevoRecord(int puntos) {
        if (mejoresPuntajes.size() < MAX_PUNTAJES) {
            return true;
        }
        return puntos > mejoresPuntajes.get(MAX_PUNTAJES - 1).getPuntos();
    }
    
    private void cargarPuntajes() {
        try {
            File archivo = new File(archivoGuardado);
            if (!archivo.exists()) {
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombre = partes[0];
                    int puntos = Integer.parseInt(partes[1]);
                    mejoresPuntajes.add(new RegistroPuntaje(nombre, puntos));
                }
            }
            reader.close();
            
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar puntajes: " + e.getMessage());
        }
    }
    
    private void guardarPuntajes() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoGuardado));
            
            for (RegistroPuntaje registro : mejoresPuntajes) {
                writer.write(registro.getNombre() + "," + registro.getPuntos());
                writer.newLine();
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al guardar puntajes: " + e.getMessage());
        }
    }
    
    public ArrayList<RegistroPuntaje> getMejoresPuntajes() {
        return new ArrayList<>(mejoresPuntajes);
    }
    
    // Clase interna para representar un registro de puntaje
    public static class RegistroPuntaje {
        private String nombre;
        private int puntos;
        
        public RegistroPuntaje(String nombre, int puntos) {
            this.nombre = nombre;
            this.puntos = puntos;
        }
        
        public String getNombre() { return nombre; }
        public int getPuntos() { return puntos; }
        
        @Override
        public String toString() {
            return nombre + ": " + puntos + " pts";
        }
    }
}