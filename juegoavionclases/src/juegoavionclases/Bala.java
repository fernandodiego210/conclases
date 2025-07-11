package juegoavionclases;

import java.awt.*;

public class Bala {
    private int x, y;
    private int velocidad;
    private final int ancho = 4;
    private final int alto = 10;
    private boolean esDelJugador;
    
    public Bala(int x, int y, boolean esDelJugador) {
        this.x = x;
        this.y = y;
        this.esDelJugador = esDelJugador;
        this.velocidad = esDelJugador ? 7 : 4; // Balas del jugador más rápidas
    }
    
    public void mover() {
        if (esDelJugador) {
            y -= velocidad; // Hacia arriba
        } else {
            y += velocidad; // Hacia abajo
        }
    }
    
    public void dibujar(Graphics g) {
        if (esDelJugador) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(x, y, ancho, alto);
    }
    
    public boolean fueraDePantalla() {
        return y < 0 || y > 500;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
    
    // Getters
    public boolean esDelJugador() { return esDelJugador; }
    public int getX() { return x; }
    public int getY() { return y; }
}