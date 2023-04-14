package juego.buscaminas.ControlArduino;

import juego.buscaminas.ObserverArduino.Observador;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Clase para mover casilla que implementa la interfaz Observador para saber cuando se realiza una accion en la clase ControlArduino
 */
public class MoverCasilla implements Observador {

    /**
     * Metodo update para moverse hacia la izquierda que se activa cuando el control le notifica
     */
    @Override
    public void updateIzquierda() {
        System.out.println("Se presiono el boton de Izquierda");
        Robot robot = null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_LEFT);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo update para moverse hacia la derecha que se activa cuando el control le notifica
     */
    @Override
    public void updateDerecha() {
        System.out.println("Se presiono el boton de Derecha");
        Robot robot = null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_RIGHT);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo update para moverse hacia arriba que se activa cuando el control le notifica
     */
    @Override
    public void updateArriba() {
        System.out.println("Se presiono el boton de Arriba");
        Robot robot = null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_UP);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo update para moverse hacia abajo que se activa cuando el control le notifica
     */
    @Override
    public void updateAbajo() {
        System.out.println("Se presiono el boton de Abajo");
        Robot robot = null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_DOWN);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo update para seleccionar una casilla que se activa cuando el control le notifica
     */
    @Override
    public void updateSeleccionar() {
        System.out.println("Se presiono el boton de Seleccionar");
        Robot robot = null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
