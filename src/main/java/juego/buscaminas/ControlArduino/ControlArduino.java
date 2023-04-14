package juego.buscaminas.ControlArduino;

import com.fazecast.jSerialComm.SerialPort;
import juego.buscaminas.ObserverArduino.Observador;
import juego.buscaminas.ObserverArduino.Sujeto;

import java.util.ArrayList;

/**
 * Clase que inicia la conexion entre java y arduino mediante jserialcomm
 * @author https://www.tabnine.com/code/java/classes/com.fazecast.jSerialComm.SerialPort
 */
public class ControlArduino implements Sujeto {

    private ArrayList<Observador> observadores;

    MoverCasilla moverCasilla = new MoverCasilla();

    boolean led = false;
    boolean buzzerCasilla = false;
    boolean buzzerMina = false;

    /**
     * Metodo constructor de la clase ControlArduino
     */
    public ControlArduino() {
        observadores = new ArrayList<>();
        enlazarObservador(moverCasilla);
        SerialPort serialPort = SerialPort.getCommPort("COM7"); // Puerto al cual se encuentra conectado el arduino
        serialPort.setBaudRate(9600);
        serialPort.openPort();
        byte[] buffer = new byte[1024];
        String inputLine = "";
        while (serialPort.isOpen()) {
            //Lee los bytes que recibe desde el puerto serial
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
                    notificarArriba();

                    inputLine = "";
                }if (inputLine.contains("abajo")) {
                    //System.out.println("Se ha presionado el boton de abajo");
                    notificarAbajo();

                    inputLine = "";
                }if (inputLine.contains("seleccionar")) {
                    //System.out.println("Se ha presionado el boton de seleccionar");
                    notificarSeleccionar();

                    inputLine = "";
                }
            }
            if (isLed()){
                // Escribir datos al puerto serial
                String outputLine = "10";
                serialPort.writeBytes(outputLine.getBytes(), outputLine.length());

                // Esperar un tiempo antes de volver a leer/escribir el puerto serial
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setLed(false);
            }
            if (isBuzzerCasilla()){
                // Escribir datos al puerto serial
                String outputLine = "20";
                serialPort.writeBytes(outputLine.getBytes(), outputLine.length());

                // Esperar un tiempo antes de volver a leer/escribir el puerto serial
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBuzzerCasilla(false);
            }
            if (isBuzzerMina()){
                // Escribir datos al puerto serial
                String outputLine = "30";
                serialPort.writeBytes(outputLine.getBytes(), outputLine.length());

                // Esperar un tiempo antes de volver a leer/escribir el puerto serial
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBuzzerMina(false);
            }
        }
    }

    /**
     * Metodo para enlazar la clase que observara a la clase ControlArduino
     * @param o
     */
    public void enlazarObservador(Observador o){
        observadores.add(o);
    }

    /**
     * Metodo para notificar a la clase MoverCasilla que se mueva a la izquierda
     */
    @Override
    public void notificarIzquierda() {
        for (Observador o: observadores) {
            o.updateIzquierda();
        }
    }

    /**
     * Metodo para notificar a la clase MoverCasilla que se mueva a la derecha
     */
    @Override
    public void notificarDerecha() {
        for (Observador o: observadores) {
            o.updateDerecha();
        }
    }

    /**
     * Metodo para notificar a la clase MoverCasilla que se mueva hacia arriba
     */
    @Override
    public void notificarArriba() {
        for (Observador o: observadores) {
            o.updateArriba();
        }
    }

    /**
     * Metodo para notificar a la clase MoverCasilla que se mueva hacia abajo
     */
    @Override
    public void notificarAbajo() {
        for (Observador o: observadores) {
            o.updateAbajo();
        }
    }

    /**
     * Metodo para notificar a la clase MoverCasilla que seleccione una casilla
     */
    @Override
    public void notificarSeleccionar() {
        for (Observador o: observadores) {
            o.updateSeleccionar();
        }
    }

    /**
     * Metodo booleano para decir si el atributo led es o no true
     * @return si es true o no
     */
    public boolean isLed() {
        return led;
    }

    /**
     * Metodo para setear el atributo led como true o false
     * @param led
     */
    public void setLed(boolean led) {
        this.led = led;
    }

    /**
     * Metodo booleano para decir si el atributo buzzerCasilla es o no true
     * @return si es true o no
     */
    public boolean isBuzzerCasilla() {
        return buzzerCasilla;
    }

    /**
     * Metodo para setear el atributo buzzerCasilla como true o false
     * @param buzzerCasilla true o false
     */
    public void setBuzzerCasilla(boolean buzzerCasilla) {
        this.buzzerCasilla = buzzerCasilla;
    }

    /**
     * Metodo booleano para decir si el atributo buzzerMina es o no true
     * @return si es true o no
     */
    public boolean isBuzzerMina() {
        return buzzerMina;
    }

    /**
     * Metodo para setear el atributo buzzerMina como true o false
     * @param buzzerMina true o false
     */
    public void setBuzzerMina(boolean buzzerMina) {
        this.buzzerMina = buzzerMina;
    }
}
