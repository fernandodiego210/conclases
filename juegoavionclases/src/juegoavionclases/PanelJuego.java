package juegoavionclases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    
    // Componentes principales
    private EstadoJuego estadoJuego;
    private RenderizadorJuego renderizador;
    private ActualizadorJuego actualizador;
    private ManejadorColisiones manejadorColisiones;
    private GeneradorElementos generadorElementos;
    
    // Control del juego
    private Timer timer;
    private ControladorTeclado controlador;
    private int contadorDisparo = 0;
    
    public PanelJuego() {
        configurarPanel();
        inicializarComponentes();
        iniciarTimer();
    }
    
    private void configurarPanel() {
        setPreferredSize(new Dimension(400, 500));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void inicializarComponentes() {
        estadoJuego = new EstadoJuego();
        renderizador = new RenderizadorJuego(estadoJuego);
        actualizador = new ActualizadorJuego(estadoJuego);
        manejadorColisiones = new ManejadorColisiones(estadoJuego);
        generadorElementos = new GeneradorElementos(estadoJuego);
        controlador = new ControladorTeclado();
    }
    
    private void iniciarTimer() {
        timer = new Timer(20, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderizador.renderizar(g);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (estadoJuego.isJuegoTerminado()) {
            return;
        }
        
        // Verificar si el juego debe terminar
        if (estadoJuego.debeTerminarJuego()) {
            estadoJuego.setJuegoTerminado(true);
            return;
        }
        
        // Procesar entrada del usuario
        procesarEntradaUsuario();
        
        // Actualizar elementos del juego
        actualizador.actualizarTodosLosElementos();
        
        // Verificar colisiones
        manejadorColisiones.verificarTodasLasColisiones();
        
        // Generar nuevos elementos
        generadorElementos.generarElementos();
        
        // Repintar la pantalla
        repaint();
    }
    
    private void procesarEntradaUsuario() {
        Nave nave = estadoJuego.getNave();
        
        // Mover nave
        if (controlador.isIzquierda()) nave.moverIzquierda();
        if (controlador.isDerecha()) nave.moverDerecha();
        if (controlador.isArriba()) nave.moverArriba();
        if (controlador.isAbajo()) nave.moverAbajo();
        
        // Disparar
        int frecuenciaDisparo = nave.tieneDisparoRapido() ? 5 : 15;
        if (controlador.isDisparo() && contadorDisparo <= 0) {
            estadoJuego.getBalas().add(nave.disparar());
            contadorDisparo = frecuenciaDisparo;
        }
        if (contadorDisparo > 0) contadorDisparo--;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (estadoJuego.isJuegoTerminado() && e.getKeyCode() == KeyEvent.VK_R) {
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
    
    private void reiniciarJuego() {
        contadorDisparo = 0;
        estadoJuego.reiniciar();
    }
}