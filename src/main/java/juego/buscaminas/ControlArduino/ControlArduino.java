package juego.buscaminas.ControlArduino;

import com.fazecast.jSerialComm.SerialPort;
import juego.buscaminas.Observer.Observador;
import juego.buscaminas.Observer.Sujeto;

import java.util.ArrayList;

public class ControlArduino implements Sujeto {

    private ArrayList<Observador> observadores;

    MoverCasilla moverCasilla = new MoverCasilla();

    public ControlArduino() {
        observadores = new ArrayList<>();
        enlazarObservador(moverCasilla);
        SerialPort serialPort = SerialPort.getCommPort("COM7"); // Puerto al cual se encuentra conectado el arduino
        serialPort.setBaudRate(9600);
        serialPort.openPort();
        byte[] buffer = new byte[1024];
        String inputLine = "";
        while (serialPort.isOpen()) {
            int numRead = serialPort.readBytes(buffer, buffer.length);

            if (numRead > 0) {
                inputLine += new String(buffer, 0, numRead);
                if (inputLine.contains("izquierda")) {
                    //System.out.println("Se ha presionado el boton izquierdo");
                    notificarIzquierda();

                    inputLine = "";
                }if (inputLine.contains("derecha")) {
                    //System.out.println("Se ha presionado el boton derecho");
                    notificarDerecha();

                    inputLine = "";
                }if (inputLine.contains("arriba")) {
                    //System.out.println("Se ha presionado el boton de arriba");
                    //notificarArriba();
                    notificarSeleccionar();

                    inputLine = "";
                }if (inputLine.contains("abajo")) {
                    //System.out.println("Se ha presionado el boton de abajo");
                    notificarArriba();

                    inputLine = "";
                }if (inputLine.contains("seleccionar")) {
                    //System.out.println("Se ha presionado el boton de seleccionar");
                    notificarSeleccionar();

                    inputLine = "";
                }
            }
        }
    }
    public void enlazarObservador(Observador o){
        observadores.add(o);
    }

    @Override
    public void notificarIzquierda() {
        for (Observador o: observadores) {
            o.updateIzquierda();
        }
    }

    @Override
    public void notificarDerecha() {
        for (Observador o: observadores) {
            o.updateDerecha();
        }
    }

    @Override
    public void notificarArriba() {
        for (Observador o: observadores) {
            o.updateArriba();
        }
    }

    @Override
    public void notificarAbajo() {
        for (Observador o: observadores) {
            o.updateAbajo();
        }
    }

    @Override
    public void notificarSeleccionar() {
        for (Observador o: observadores) {
            o.updateSeleccionar();
        }
    }
}
