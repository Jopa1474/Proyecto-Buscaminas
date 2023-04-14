package juego.buscaminas.ObserverArduino;

public interface Sujeto {

    public void notificarIzquierda();

    public void notificarDerecha();

    public void notificarArriba(); //Presion boton arriba

    public void notificarAbajo();

    public void notificarSeleccionar();



}
