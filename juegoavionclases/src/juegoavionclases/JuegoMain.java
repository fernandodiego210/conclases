package juegoavionclases;

import javax.swing.*;

public class JuegoMain {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Nave vs Enemigos - Mejorado");
        PanelJuego juego = new PanelJuego();
        
        ventana.add(juego);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}