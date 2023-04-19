package juego.buscaminas.ObserverArduino;

/**
 * Interface Observador que se encarda de observar a alguna clase en busca de algun cambio para notificarselo a la clase observadora
 */
public interface Observador {

    /**
     * Metodo para actualizar Izquierda
     */
    public void updateIzquierda();

    /**
     * Metodo para actualizar Derecha
     */
    public void updateDerecha();

    /**
     * Metodo para actualizar Arriba
     */
    public void updateArriba(); //actualizar cuando el sujeto lo notifique o dispare un evento

    /**
     * Metodo para actualizar Abajo
     */
    public void updateAbajo();

    /**
     * Metodo para actualizar Seleccionar
     */
    public void updateSeleccionar();


}
