package juegoavionclases;

import java.awt.event.KeyEvent;

public class ControladorTeclado {
    private boolean izquierda, derecha, arriba, abajo, disparo;
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                izquierda = true;
                break;
            case KeyEvent.VK_RIGHT:
                derecha = true;
                break;
            case KeyEvent.VK_UP:
                arriba = true;
                break;
            case KeyEvent.VK_DOWN:
                abajo = true;
                break;
            case KeyEvent.VK_SPACE:
                disparo = true;
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                izquierda = false;
                break;
            case KeyEvent.VK_RIGHT:
                derecha = false;
                break;
            case KeyEvent.VK_UP:
                arriba = false;
                break;
            case KeyEvent.VK_DOWN:
                abajo = false;
                break;
            case KeyEvent.VK_SPACE:
                disparo = false;
                break;
        }
    }
    
    public boolean isIzquierda() { return izquierda; }
    public boolean isDerecha() { return derecha; }
    public boolean isArriba() { return arriba; }
    public boolean isAbajo() { return abajo; }
    public boolean isDisparo() { return disparo; }
    
    public void setDisparo(boolean disparo) { this.disparo = disparo; }
}