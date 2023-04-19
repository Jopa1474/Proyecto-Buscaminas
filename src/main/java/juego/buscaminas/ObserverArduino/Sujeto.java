package juego.buscaminas.ObserverArduino;

/**
 * Interface Sujeto la cual es el sujeto que esta siendo observado por el observador en busca de algun cambio
 */
public interface Sujeto {

    /**
     * Metodo para notificar Izquierda
     */
    public void notificarIzquierda();

    /**
     * Metodo para notificar Derecha
     */
    public void notificarDerecha();

    /**
     * Metodo para notificar Arriba
     */
    public void notificarArriba(); //Presion boton arriba

    /**
     * Metodo para notificar Abajo
     */
    public void notificarAbajo();

    /**
     * Metodo para notificar Seleccionar
     */
    public void notificarSeleccionar();

}
