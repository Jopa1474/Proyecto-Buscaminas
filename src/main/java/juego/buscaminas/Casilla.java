package juego.buscaminas;

/**
 * Clase que se encarga de las casillas que componen al tablero del buscaminas y contiene todo lo necesario para jugar
 */
public class Casilla {

    /**
     * Aributos de la clase Casilla
     */
    int posicionFila;
    int posicionColumna;
    boolean mina;
    int numPista = 0;

    Casilla[][] casillas;

    boolean Abierta = false;

    /**
     * Metodo constructor para la clase Casilla
     *
     * @param posicionFila    fila en la que se posicionara la casilla
     * @param posicionColumna columna en la que se posicionara la casilla
     */
    public Casilla(int posicionFila, int posicionColumna) {
        this.posicionFila = posicionFila;
        this.posicionColumna = posicionColumna;
    }

    /**
     * Metodo get para la posicion de la fila en la que se encuentra la casilla
     * @return la posicion de la fila
     */
    public int getPosicionFila() {
        return posicionFila;
    }

    /**
     * Metodo set para la posicion de la fila de la casilla
     */
    public void setPosicionFila(int posicionFila) {
        this.posicionFila = posicionFila;
    }

    /**
     * Metodo get para la posicion de la columna en la que se encuentra la casilla
     * @return la posicion de la columna
     */
    public int getPosicionColumna() {
        return posicionColumna;
    }

    /**
     * Metodo set para la posicion de la columna de la casilla
     */
    public void setPosicionColumna(int posicionColumna) {
        this.posicionColumna = posicionColumna;
    }

    /**
     * Metodo boolean para determinar si en una casilla hay mina
     * @return si hay mina o no
     */
    public boolean isMina() {
        return mina;
    }

    /**
     * Metodo para poner una mina en la casilla
     * @param mina
     */
    public void setMina(boolean mina) {
        this.mina = mina;
    }

    /**
     * Metodo get para el numero de pista de la casilla
     * @return el numero de pista
     */
    public int getNumPista() {
        return numPista;
    }

    /**
     * Metodo set para el numero de pista de la casilla
     * @param numPista el numero de pista a asignar
     */
    public void setNumPista(int numPista) {
        this.numPista = numPista;
    }

    /**
     * Metodo boolean para saber si una casilla ya fue abierta
     * @return Si esta abierta o no
     */
    public boolean isAbierta() {
        return Abierta;
    }

    /**
     * Metodo set para poner una casilla como abierta
     * @param abierta para poner una casilla como abierta
     */
    public void setAbierta(boolean abierta) {
        Abierta = abierta;
    }

    /**
     * Para poder representar cada casilla con su posicion ne la fila y su columna
     * @return
     */
    @Override
    public String toString() {
        return "[" + posicionFila + "," + posicionColumna +"]";
    }

}
