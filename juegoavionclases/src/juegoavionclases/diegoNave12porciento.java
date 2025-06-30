package juegoavionclases;

import java.awt.*;

public class diegoNave12porciento {
    private int x, y;
    private int vidas;
    private final int velocidad = 3;
    private final int ancho = 30;
    private final int alto = 30;
    private boolean tieneEscudo = false;
    private int tiempoEscudo = 0;
    private boolean disparoRapido = false;
    private int tiempoDisparoRapido = 0;
    
    public Nave(int x, int y) {
        this.x = x;
        this.y = y;
        this.vidas = 3;
    }
    
    public void moverIzquierda() {
        if (x > 15) x -= velocidad;
    }
    
    public void moverDerecha() {
        if (x < 385) x += velocidad;
    }
    
    public void moverArriba() {
        if (y > 15) y -= velocidad;
    }
    
    public void moverAbajo() {
        if (y < 485) y += velocidad;
    }
    
  