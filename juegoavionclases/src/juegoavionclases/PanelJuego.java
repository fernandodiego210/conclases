package juegoavionclases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    
    private Nave nave;
    private ArrayList<Bala> balas;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<PowerUp> powerUps;
    private Timer timer;
    private ControladorTeclado controlador;
    private int puntos = 0;
    private int contadorDisparo = 0;
    private boolean juegoTerminado = false;
    
    public PanelJuego() {
        setPreferredSize(new Dimension(400, 500));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        inicializarJuego();
        
        timer = new Timer(20, this);
        timer.start();
    }
    
    private void inicializarJuego() {
        nave = new Nave(200, 400);
        balas = new ArrayList<>();
        enemigos = new ArrayList<>();
        powerUps = new ArrayList<>();
        controlador = new ControladorTeclado();
        
        // Crear enemigos iniciales
        for (int i = 0; i < 5; i++) {
            enemigos.add(new Enemigo(i * 70 + 35, 50));
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (juegoTerminado) {
            dibujarGameOver(g);
            return;
        }
        
        // Dibujar nave
        nave.dibujar(g);
        
        // Dibujar balas
        for (Bala bala : balas) {
            bala.dibujar(g);
        }
        
        // Dibujar enemigos
        for (Enemigo enemigo : enemigos) {
            enemigo.dibujar(g);
        }
        
        // Dibujar power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.dibujar(g);
        }
        
        // Dibujar interfaz
        dibujarInterfaz(g);
    }
    
    private void dibujarInterfaz(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + puntos, 10, 20);
        g.drawString("Vidas: " + nave.getVidas(), 10, 40);
        
        // Mostrar estado de power-ups
        if (nave.tieneEscudo()) {
            g.setColor(Color.CYAN);
            g.drawString("ESCUDO ACTIVO", 10, 60);
        }
        if (nave.tieneDisparoRapido()) {
            g.setColor(Color.ORANGE);
            g.drawString("DISPARO RAPIDO", 10, 80);
        }
        
        g.setColor(Color.WHITE);
        g.drawString("Flechas = mover, ESPACIO = disparar", 10, 480);
    }
    
    private void dibujarGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("GAME OVER", 120, 200);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Puntuación Final: " + puntos, 140, 250);
        g.drawString("Presiona R para reiniciar", 130, 300);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado) {
            return;
        }
        
        if (!nave.estaVivo()) {
            juegoTerminado = true;
            return;
        }
        
        // Actualizar nave
        nave.actualizar();
        
        // Mover nave
        if (controlador.isIzquierda()) nave.moverIzquierda();
        if (controlador.isDerecha()) nave.moverDerecha();
        if (controlador.isArriba()) nave.moverArriba();
        if (controlador.isAbajo()) nave.moverAbajo();
        
        // Disparar
        int frecuenciaDisparo = nave.tieneDisparoRapido() ? 5 : 15;
        if (controlador.isDisparo() && contadorDisparo <= 0) {
            balas.add(nave.disparar());
            contadorDisparo = frecuenciaDisparo;
        }
        if (contadorDisparo > 0) contadorDisparo--;
        
        // Actualizar objetos del juego
        actualizarBalas();
        actualizarEnemigos();
        actualizarPowerUps();
        
        // Verificar colisiones
        verificarColisiones();
        
        // Generar nuevos elementos
        generarNuevosEnemigos();
        generarNuevosPowerUps();
        
        repaint();
    }
    
    private void actualizarBalas() {
        for (int i = balas.size() - 1; i >= 0; i--) {
            Bala bala = balas.get(i);
            bala.mover();
            
            if (bala.fueraDePantalla()) {
                balas.remove(i);
            }
        }
    }
    
    private void actualizarEnemigos() {
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            Enemigo enemigo = enemigos.get(i);
            enemigo.mover();
            
            // Enemigo dispara
            if (enemigo.puedeDisparar()) {
                balas.add(enemigo.disparar());
            }
            
            if (enemigo.fueraDePantalla()) {
                enemigos.remove(i);
            }
        }
    }
    
    private void actualizarPowerUps() {
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.mover();
            
            if (powerUp.fueraDePantalla()) {
                powerUps.remove(i);
            }
        }
    }
    
    private void verificarColisiones() {
        // Colisiones bala-enemigo y bala-nave
        for (int i = balas.size() - 1; i >= 0; i--) {
            Bala bala = balas.get(i);
            
            if (bala.esDelJugador()) {
                // Bala del jugador vs enemigos
                for (int j = enemigos.size() - 1; j >= 0; j--) {
                    if (bala.getBounds().intersects(enemigos.get(j).getBounds())) {
                        balas.remove(i);
                        enemigos.remove(j);
                        puntos += 10;
                        break;
                    }
                }
            } else {
                // Bala del enemigo vs nave
                if (bala.getBounds().intersects(nave.getBounds())) {
                    balas.remove(i);
                    nave.recibirDanio();
                }
            }
        }
        
        // Colisiones nave-power-up
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            if (powerUps.get(i).getBounds().intersects(nave.getBounds())) {
                nave.aplicarPowerUp(powerUps.get(i).getTipo());
                powerUps.remove(i);
                puntos += 5;
            }
        }
        
        // Colisiones nave-enemigo
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getBounds().intersects(nave.getBounds())) {
                nave.recibirDanio();
            }
        }
    }
    
    private void generarNuevosEnemigos() {
        if (Math.random() < 0.015) {
            int x = (int)(Math.random() * 340) + 30;
            enemigos.add(new Enemigo(x, 0));
        }
    }
    
    private void generarNuevosPowerUps() {
        if (Math.random() < 0.003) { // Menos frecuente que enemigos
            int x = (int)(Math.random() * 360) + 20;
            String[] tipos = {"ESCUDO", "VIDA", "DISPARO_RAPIDO"};
            String tipo = tipos[(int)(Math.random() * tipos.length)];
            powerUps.add(new PowerUp(x, 0, tipo));
        }
    }
    
    private void reiniciarJuego() {
        juegoTerminado = false;
        puntos = 0;
        contadorDisparo = 0;
        balas.clear();
        enemigos.clear();
        powerUps.clear();
        inicializarJuego();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado && e.getKeyCode() == KeyEvent.VK_R) {
            reiniciarJuego();
        } else {
            controlador.keyPressed(e);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        controlador.keyReleased(e);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}