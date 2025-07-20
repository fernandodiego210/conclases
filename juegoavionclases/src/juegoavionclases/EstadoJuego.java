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
    private String nombreJugador = "";
    private ManejadorPuntajes manejadorPuntajes;
    private boolean puntajeGuardado = false;
    
    // Estados del juego
    public enum EstadoJuegoEnum {
        MENU_INICIAL,
        JUGANDO,
        GAME_OVER
    }
    
    private EstadoJuegoEnum estadoActual = EstadoJuegoEnum.MENU_INICIAL;

    public EstadoJuego() {
        manejadorPuntajes = new ManejadorPuntajes();
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

    public void empezarJuego(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.estadoActual = EstadoJuegoEnum.JUGANDO;
        reiniciar();
    }

    public void reiniciar() {
        juegoTerminado = false;
        puntajeGuardado = false;
        puntos = 0;
        enemigosEliminados = 0;
        balas.clear();
        enemigos.clear();
        enemigos2.clear();
        powerUps.clear();
        inicializar();
        
        if (estadoActual == EstadoJuegoEnum.GAME_OVER) {
            estadoActual = EstadoJuegoEnum.MENU_INICIAL;
        }
    }

    public void terminarJuego() {
        if (!juegoTerminado) {
            juegoTerminado = true;
            estadoActual = EstadoJuegoEnum.GAME_OVER;
            
            // Guardar puntaje solo una vez
            if (!puntajeGuardado && !nombreJugador.isEmpty()) {
                if (manejadorPuntajes.esNuevoRecord(puntos)) {
                    manejadorPuntajes.agregarPuntaje(nombreJugador, puntos);
                }
                puntajeGuardado = true;
            }
        }
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
        return !nave.estaVivo() && estadoActual == EstadoJuegoEnum.JUGANDO;
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
    public String getNombreJugador() { return nombreJugador; }
    public ManejadorPuntajes getManejadorPuntajes() { return manejadorPuntajes; }
    public EstadoJuegoEnum getEstadoActual() { return estadoActual; }

    // Setters
    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
        if (juegoTerminado) {
            estadoActual = EstadoJuegoEnum.GAME_OVER;
        }
    }
    
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    
    public void setEstadoActual(EstadoJuegoEnum estado) {
        this.estadoActual = estado;
    }
}