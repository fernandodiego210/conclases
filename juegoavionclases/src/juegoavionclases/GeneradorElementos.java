package juegoavionclases;

import java.util.ArrayList;

public class GeneradorElementos {
    private EstadoJuego estadoJuego;
    
    public GeneradorElementos(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    
    public void generarElementos() {
        generarEnemigos();
        generarPowerUps();
    }
    
    private void generarEnemigos() {
        if (Math.random() < 0.015) {
            int x = (int)(Math.random() * 340) + 30;
            estadoJuego.getEnemigos().add(new Enemigo(x, 0));
        }
    }
    
    private void generarPowerUps() {
        if (Math.random() < 0.003) {
            int x = (int)(Math.random() * 360) + 20;
            String[] tipos = {"ESCUDO", "VIDA", "DISPARO_RAPIDO"};
            String tipo = tipos[(int)(Math.random() * tipos.length)];
            estadoJuego.getPowerUps().add(new PowerUp(x, 0, tipo));
        }
    }
}