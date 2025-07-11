package juegoavionclases;

import java.awt.*;

public class Nave {
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
    
    public void actualizar() {
        // Actualizar power-ups
        if (tieneEscudo) {
            tiempoEscudo--;
            if (tiempoEscudo <= 0) {
                tieneEscudo = false;
            }
        }
        
        if (disparoRapido) {
            tiempoDisparoRapido--;
            if (tiempoDisparoRapido <= 0) {
                disparoRapido = false;
            }
        }
    }
    
    public void dibujar(Graphics g) {
        // Dibujar escudo si está activo
        if (tieneEscudo) {
            g.setColor(new Color(0, 255, 255, 100)); // Cyan transparente
            g.fillOval(x-25, y-25, 50, 50);
        }
        
        // Dibujar nave
        if (disparoRapido) {
            g.setColor(Color.ORANGE); // Color especial con disparo rápido
        } else {
            g.setColor(Color.CYAN);
        }
        
        int[] xPoints = {x, x-15, x+15};
        int[] yPoints = {y-15, y+15, y+15};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    public Bala disparar() {
        return new Bala(x-2, y-20, true); // true = bala del jugador
    }
    
    public void recibirDanio() {
        if (!tieneEscudo) {
            vidas--;
        }
    }
    
    public void aplicarPowerUp(String tipo) {
        switch (tipo) {
            case "ESCUDO":
                tieneEscudo = true;
                tiempoEscudo = 300; // 15 segundos a 20fps
                break;
            case "VIDA":
                vidas++;
                break;
            case "DISPARO_RAPIDO":
                disparoRapido = true;
                tiempoDisparoRapido = 400; // 20 segundos a 20fps
                break;
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x-15, y-15, 30, 30);
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVidas() { return vidas; }
    public boolean tieneEscudo() { return tieneEscudo; }
    public boolean tieneDisparoRapido() { return disparoRapido; }
    public boolean estaVivo() { return vidas > 0; }
}