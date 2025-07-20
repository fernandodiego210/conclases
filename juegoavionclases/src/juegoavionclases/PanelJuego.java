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
    
    // Para entrada de texto
    private String nombreTemporal = "";

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
        switch (estadoJuego.getEstadoActual()) {
            case MENU_INICIAL:
                // Solo repintar en el menú
                repaint();
                break;
                
            case JUGANDO:
                // Lógica principal del juego
                ejecutarLogicaJuego();
                break;
                
            case GAME_OVER:
                // Solo repintar en game over
                repaint();
                break;
        }
    }
    
    private void ejecutarLogicaJuego() {
        // Verificar si el juego debe terminar
        if (estadoJuego.debeTerminarJuego()) {
            estadoJuego.terminarJuego();
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
        switch (estadoJuego.getEstadoActual()) {
            case MENU_INICIAL:
                manejarEntradaMenuInicial(e);
                break;
                
            case JUGANDO:
                controlador.keyPressed(e);
                break;
                
            case GAME_OVER:
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    reiniciarJuego();
                }
                break;
        }
    }
    
    private void manejarEntradaMenuInicial(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!nombreTemporal.trim().isEmpty()) {
                estadoJuego.empezarJuego(nombreTemporal.trim());
                nombreTemporal = "";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (nombreTemporal.length() > 0) {
                nombreTemporal = nombreTemporal.substring(0, nombreTemporal.length() - 1);
                estadoJuego.setNombreJugador(nombreTemporal);
            }
        } else if (Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == ' ') {
            if (nombreTemporal.length() < 15) { // Límite de caracteres
                nombreTemporal += e.getKeyChar();
                estadoJuego.setNombreJugador(nombreTemporal);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (estadoJuego.getEstadoActual() == EstadoJuego.EstadoJuegoEnum.JUGANDO) {
            controlador.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void reiniciarJuego() {
        contadorDisparo = 0;
        nombreTemporal = "";
        estadoJuego.reiniciar();
    }
}