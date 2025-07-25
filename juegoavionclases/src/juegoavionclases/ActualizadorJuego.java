package juegoavionclases;

import java.util.ArrayList;

public class ActualizadorJuego {
    private EstadoJuego estadoJuego;
    
    public ActualizadorJuego(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    
    public void actualizarTodosLosElementos() {
        actualizarNave();
        actualizarBalas();
        actualizarEnemigos();
        actualizarEnemigos2();
        actualizarPowerUps();
    }
    
    private void actualizarNave() {
        estadoJuego.getNave().actualizar();
    }
    
    private void actualizarBalas() {
        ArrayList<Bala> balas = estadoJuego.getBalas();
        for (int i = balas.size() - 1; i >= 0; i--) {
            Bala bala = balas.get(i);
            bala.mover();
            
            if (bala.fueraDePantalla()) {
                balas.remove(i);
            }
        }
    }
    
    private void actualizarEnemigos() {
        ArrayList<Enemigo> enemigos = estadoJuego.getEnemigos();
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            Enemigo enemigo = enemigos.get(i);
            enemigo.mover();
            
            // Enemigo dispara
            if (enemigo.puedeDisparar()) {
                estadoJuego.getBalas().add(enemigo.disparar());
            }
            
            if (enemigo.fueraDePantalla()) {
                enemigos.remove(i);
            }
        }
    }
    
    private void actualizarEnemigos2() {
        ArrayList<Enemigo2> enemigos2 = estadoJuego.getEnemigos2();
        for (int i = enemigos2.size() - 1; i >= 0; i--) {
            Enemigo2 enemigo2 = enemigos2.get(i);
            enemigo2.mover();
            
            // Enemigo2 dispara
            if (enemigo2.puedeDisparar()) {
                estadoJuego.getBalas().add(enemigo2.disparar());
            }
            
            if (enemigo2.fueraDePantalla()) {
                enemigos2.remove(i);
            }
        }
    }
    
    private void actualizarPowerUps() {
        ArrayList<PowerUp> powerUps = estadoJuego.getPowerUps();
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.mover();
            
            if (powerUp.fueraDePantalla()) {
                powerUps.remove(i);
            }
        }
    }
}