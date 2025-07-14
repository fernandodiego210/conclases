package juegoavionclases;

import java.util.ArrayList;

public class EstadoJuego {
    private Nave nave;
    private ArrayList<Bala> balas;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<Enemigo2> enemigos2;
    private ArrayList<PowerUp> powerUps;
    private int puntos = 0;
    private int enemigosEliminados = 0;
    private boolean juegoTerminado = false;
    
    public EstadoJuego() {
        inicializar();
    }
    
    public void inicializar() {
        nave = new Nave(200, 400);
        balas = new ArrayList<>();
        enemigos = new ArrayList<>();
        enemigos2 = new ArrayList<>();
        powerUps = new ArrayList<>();
        
        // Crear enemigos iniciales
        for (int i = 0; i < 5; i++) {
            enemigos.add(new Enemigo(i * 70 + 35, 50));
        }
    }
    
    public void reiniciar() {
        juegoTerminado = false;
        puntos = 0;
        enemigosEliminados = 0;
        balas.clear();
        enemigos.clear();
        enemigos2.clear();
        powerUps.clear();
        inicializar();
    }
    
    public void generarEnemigo2() {
        int x = (int)(Math.random() * 320) + 40;
        enemigos2.add(new Enemigo2(x, 0));
    }
    
    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }
    
    public void incrementarEnemigosEliminados() {
        this.enemigosEliminados++;
    }
    
    public boolean debeTerminarJuego() {
        return !nave.estaVivo();
    }
    
    // Getters
    public Nave getNave() { return nave; }
    public ArrayList<Bala> getBalas() { return balas; }
    public ArrayList<Enemigo> getEnemigos() { return enemigos; }
    public ArrayList<Enemigo2> getEnemigos2() { return enemigos2; }
    public ArrayList<PowerUp> getPowerUps() { return powerUps; }
    public int getPuntos() { return puntos; }
    public int getEnemigosEliminados() { return enemigosEliminados; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    
    // Setters
    public void setJuegoTerminado(boolean juegoTerminado) { 
        this.juegoTerminado = juegoTerminado; 
    }
}