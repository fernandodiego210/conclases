package juegoavionclases;

import java.awt.*;
import java.util.ArrayList;

public class RenderizadorJuego {
    private EstadoJuego estadoJuego;

    public RenderizadorJuego(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    public void renderizar(Graphics g) {
        switch (estadoJuego.getEstadoActual()) {
            case MENU_INICIAL:
                dibujarMenuInicial(g);
                break;
            case JUGANDO:
                dibujarElementosJuego(g);
                dibujarInterfaz(g);
                break;
            case GAME_OVER:
                dibujarGameOver(g);
                break;
        }
    }

    private void dibujarMenuInicial(Graphics g) {
        // Fondo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 500);
        
        // Título del juego
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("AVIÓN DE COMBATE", 50, 100);
        
        // Instrucciones
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Ingresa tu nombre y presiona ENTER", 80, 200);
        g.drawString("Nombre: " + estadoJuego.getNombreJugador(), 100, 250);
        
        // Controles
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("CONTROLES:", 150, 320);
        g.drawString("Flechas = Mover", 130, 340);
        g.drawString("ESPACIO = Disparar", 130, 360);
        g.drawString("R = Reiniciar (en Game Over)", 110, 380);
        
        // Mejores puntajes
        dibujarMejoresPuntajes(g, 150, 420);
    }

    private void dibujarElementosJuego(Graphics g) {
        // Dibujar nave
        estadoJuego.getNave().dibujar(g);

        // Dibujar balas
        for (Bala bala : estadoJuego.getBalas()) {
            bala.dibujar(g);
        }

        // Dibujar enemigos normales
        for (Enemigo enemigo : estadoJuego.getEnemigos()) {
            enemigo.dibujar(g);
        }

        // Dibujar enemigos triangulares
        for (Enemigo2 enemigo2 : estadoJuego.getEnemigos2()) {
            enemigo2.dibujar(g);
        }

        // Dibujar power-ups
        for (PowerUp powerUp : estadoJuego.getPowerUps()) {
            powerUp.dibujar(g);
        }
    }

    private void dibujarInterfaz(Graphics g) {
        Nave nave = estadoJuego.getNave();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Jugador: " + estadoJuego.getNombreJugador(), 10, 20);
        g.drawString("Puntos: " + estadoJuego.getPuntos(), 10, 35);
        g.drawString("Vidas: " + nave.getVidas(), 10, 50);
        g.drawString("Enemigos eliminados: " + estadoJuego.getEnemigosEliminados(), 10, 65);

        // Mostrar estado de power-ups
        int yPowerUp = 80;
        if (nave.tieneEscudo()) {
            g.setColor(Color.CYAN);
            g.drawString("ESCUDO ACTIVO", 10, yPowerUp);
            yPowerUp += 15;
        }
        if (nave.tieneDisparoRapido()) {
            g.setColor(Color.ORANGE);
            g.drawString("DISPARO RAPIDO", 10, yPowerUp);
        }
    }

    private void dibujarGameOver(Graphics g) {
        // Fondo semi-transparente
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, 400, 500);
        
        // Título Game Over
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", 100, 150);
        
        // Información del jugador
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Jugador: " + estadoJuego.getNombreJugador(), 120, 200);
        g.drawString("Puntuación Final: " + estadoJuego.getPuntos(), 110, 230);
        g.drawString("Enemigos eliminados: " + estadoJuego.getEnemigosEliminados(), 100, 260);
        
        // Verificar si es un nuevo récord
        if (estadoJuego.getManejadorPuntajes().esNuevoRecord(estadoJuego.getPuntos()) && 
            estadoJuego.getPuntos() > 0) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("¡NUEVO RÉCORD!", 140, 290);
        }
        
        // Mejores puntajes
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("MEJORES PUNTAJES:", 130, 330);
        
        dibujarMejoresPuntajes(g, 120, 350);
        
        // Instrucciones para reiniciar
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Presiona R para volver al menú", 120, 450);
    }
    
    private void dibujarMejoresPuntajes(Graphics g, int x, int y) {
        ArrayList<ManejadorPuntajes.RegistroPuntaje> puntajes = 
            estadoJuego.getManejadorPuntajes().getMejoresPuntajes();
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        
        if (puntajes.isEmpty()) {
            g.drawString("No hay puntajes aún", x, y);
        } else {
            for (int i = 0; i < puntajes.size(); i++) {
                ManejadorPuntajes.RegistroPuntaje registro = puntajes.get(i);
                String texto = (i + 1) + ". " + registro.toString();
                g.drawString(texto, x - 20, y + (i * 15));
            }
        }
    }
}