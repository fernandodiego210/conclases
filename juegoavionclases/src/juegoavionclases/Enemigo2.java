package juegoavionclases;

import java.awt.*;

public class Enemigo2 {
    private int x, y;
    private final int velocidad = 1; // Más lento que enemigo normal
    private final int ancho = 40;
    private final int alto = 40;
    private int vidas = 3; // Requiere 3 disparos para destruir
    private int tiempoUltimoDisparo = 0;
    private final int frecuenciaDisparo = 60; // Dispara más frecuentemente
    private int animacion = 0;
    
    public Enemigo2(int x, int y) {
        this.x = x;
        this.y = y;
        this.tiempoUltimoDisparo = (int)(Math.random() * frecuenciaDisparo);
    }
    
    public void mover() {
        y += velocidad;
        tiempoUltimoDisparo++;
        animacion++;
    }
    
    public void dibujar(Graphics g) {
        // Efecto de brillo para indicar que es especial
        int brillo = (int)(Math.sin(animacion * 0.1) * 30 + 225);
        
        // Color basado en las vidas restantes
        if (vidas == 3) {
            g.setColor(new Color(brillo, 0, 0)); // Rojo brillante
        } else if (vidas == 2) {
            g.setColor(new Color(brillo, brillo/2, 0)); // Naranja
        } else {
            g.setColor(new Color(brillo, brillo, 0)); // Amarillo
        }
        
        // Dibujar triángulo
        int[] xPoints = {x + ancho/2, x, x + ancho};
        int[] yPoints = {y, y + alto, y + alto};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // Contorno negro
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
        
        // Mostrar vidas restantes
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString(String.valueOf(vidas), x + ancho/2 - 3, y + alto/2 + 3);
    }
    
    public boolean puedeDisparar() {
        return tiempoUltimoDisparo >= frecuenciaDisparo && Math.random() < 0.05;
    }
    
    public Bala disparar() {
        tiempoUltimoDisparo = 0;
        return new Bala(x + ancho/2 - 2, y + alto, false); // false = bala del enemigo
    }
    
    public boolean recibirDanio() {
        vidas--;
        return vidas <= 0; // Retorna true si fue destruido
    }
    
    public boolean fueraDePantalla() {
        return y > 500;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVidas() { return vidas; }
}