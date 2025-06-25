package juegoavionclases;

import java.awt.*;

public class PowerUp {
    private int x, y;
    private String tipo;
    private final int velocidad = 3;
    private final int ancho = 20;
    private final int alto = 20;
    private int animacion = 0;
    
    public PowerUp(int x, int y, String tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }
    
    public void mover() {
        y += velocidad;
        animacion++;
    }
    
    public void dibujar(Graphics g) {
        // Efecto de brillo
        int brillo = (int)(Math.sin(animacion * 0.2) * 50 + 205);
        
        switch (tipo) {
            case "ESCUDO":
                g.setColor(new Color(0, brillo, 255));
                g.fillOval(x, y, ancho, alto);
                g.setColor(Color.WHITE);
                g.drawString("S", x+7, y+13);
                break;
            case "VIDA":
                g.setColor(new Color(brillo, 0, 0));
                g.fillOval(x, y, ancho, alto);
                g.setColor(Color.WHITE);
                g.drawString("+", x+7, y+13);
                break;
            case "DISPARO_RAPIDO":
                g.setColor(new Color(255, brillo, 0));
                g.fillOval(x, y, ancho, alto);
                g.setColor(Color.WHITE);
                g.drawString("R", x+7, y+13);
                break;
        }
    }
    
    public boolean fueraDePantalla() {
        return y > 500;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
    
    public String getTipo() { return tipo; }
}