package juegoavionclases;

import java.util.ArrayList;

public class ManejadorColisiones {
    private EstadoJuego estadoJuego;
    
    public ManejadorColisiones(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    
    public void verificarTodasLasColisiones() {
        verificarColisionesBalas();
        verificarColisionesPowerUps();
        verificarColisionesNaveEnemigos();
    }
    
    private void verificarColisionesBalas() {
        ArrayList<Bala> balas = estadoJuego.getBalas();
        ArrayList<Enemigo> enemigos = estadoJuego.getEnemigos();
        ArrayList<Enemigo2> enemigos2 = estadoJuego.getEnemigos2();
        Nave nave = estadoJuego.getNave();
        
        for (int i = balas.size() - 1; i >= 0; i--) {
            Bala bala = balas.get(i);
            
            if (bala.esDelJugador()) {
                // Bala del jugador vs enemigos normales
                if (verificarColisionBalaEnemigos(bala, enemigos, i)) {
                    continue; // Bala ya fue eliminada
                }
                
                // Bala del jugador vs enemigos triangulares
                verificarColisionBalaEnemigos2(bala, enemigos2, i);
                
            } else {
                // Bala del enemigo vs nave
                if (bala.getBounds().intersects(nave.getBounds())) {
                    balas.remove(i);
                    nave.recibirDanio();
                }
            }
        }
    }
    
    private boolean verificarColisionBalaEnemigos(Bala bala, ArrayList<Enemigo> enemigos, int indiceBala) {
        for (int j = enemigos.size() - 1; j >= 0; j--) {
            if (bala.getBounds().intersects(enemigos.get(j).getBounds())) {
                estadoJuego.getBalas().remove(indiceBala);
                enemigos.remove(j);
                estadoJuego.agregarPuntos(10);
                estadoJuego.incrementarEnemigosEliminados();
                
                // Generar Enemigo2 cada 15 enemigos eliminados
                if (estadoJuego.getEnemigosEliminados() % 15 == 0) {
                    estadoJuego.generarEnemigo2();
                }
                return true;
            }
        }
        return false;
    }
    
    private void verificarColisionBalaEnemigos2(Bala bala, ArrayList<Enemigo2> enemigos2, int indiceBala) {
        if (indiceBala >= 0 && indiceBala < estadoJuego.getBalas().size()) {
            for (int j = enemigos2.size() - 1; j >= 0; j--) {
                if (bala.getBounds().intersects(enemigos2.get(j).getBounds())) {
                    estadoJuego.getBalas().remove(indiceBala);
                    boolean destruido = enemigos2.get(j).recibirDanio();
                    if (destruido) {
                        enemigos2.remove(j);
                        estadoJuego.agregarPuntos(50);
                        estadoJuego.incrementarEnemigosEliminados();
                    }
                    break;
                }
            }
        }
    }
    
    private void verificarColisionesPowerUps() {
        ArrayList<PowerUp> powerUps = estadoJuego.getPowerUps();
        Nave nave = estadoJuego.getNave();
        
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            if (powerUps.get(i).getBounds().intersects(nave.getBounds())) {
                nave.aplicarPowerUp(powerUps.get(i).getTipo());
                powerUps.remove(i);
                estadoJuego.agregarPuntos(5);
            }
        }
    }
    
    private void verificarColisionesNaveEnemigos() {
        Nave nave = estadoJuego.getNave();
        
        // Colisiones nave-enemigo normal
        for (Enemigo enemigo : estadoJuego.getEnemigos()) {
            if (enemigo.getBounds().intersects(nave.getBounds())) {
                nave.recibirDanio();
            }
        }
        
        // Colisiones nave-enemigo triangular
        for (Enemigo2 enemigo2 : estadoJuego.getEnemigos2()) {
            if (enemigo2.getBounds().intersects(nave.getBounds())) {
                nave.recibirDanio();
            }
        }
    }
}