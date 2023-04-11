package juego.buscaminas.ControlArduino;

import juego.buscaminas.Observer.Observador;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MoverCasilla implements Observador {


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
