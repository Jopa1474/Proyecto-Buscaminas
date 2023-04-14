package juego.buscaminas.ObserverArduino;

public interface Observador {

    public void updateIzquierda();

    public void updateDerecha();

    public void updateArriba(); //actualizar cuando el sujeto lo notifique o dispare un evento

    public void updateAbajo();

    public void updateSeleccionar();


}
