package juegoavionclases;

import java.awt.*;

public class Enemigo {
    private int x, y;
    private final int velocidad = 2;
    private final int ancho = 30;
    private final int alto = 30;
    private int tiempoUltimoDisparo = 0;
    private final int frecuenciaDisparo = 100; // Disparar cada 5 segundos aprox
    
    public Enemigo(int x, int y) {
        this.x = x;
        this.y = y;
        this.tiempoUltimoDisparo = (int)(Math.random() * frecuenciaDisparo);
    }
    
    public void mover() {
        y += velocidad;
        tiempoUltimoDisparo++;
    }
    
    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, ancho, alto);
    }
    
    public boolean puedeDisparar() {
        return tiempoUltimoDisparo >= frecuenciaDisparo && Math.random() < 0.02;
    }
    
    public Bala disparar() {
        tiempoUltimoDisparo = 0;
        return new Bala(x+13, y+30, false); // false = bala del enemigo
    }
    
    public boolean fueraDePantalla() {
        return y > 500;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
}