package juego.buscaminas;

public class Casilla {

    int posicionFila;
    int posicionColumna;
    boolean mina;
    int numPista = 0;

    Casilla[][] casillas;

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

    public int getPosicionFila() {
        return posicionFila;
    }

    public void setPosicionFila(int posicionFila) {
        this.posicionFila = posicionFila;
    }

    public int getPosicionColumna() {
        return posicionColumna;
    }

    public void setPosicionColumna(int posicionColumna) {
        this.posicionColumna = posicionColumna;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getNumPista() {
        return numPista;
    }

    public void setNumPista(int numPista) {
        this.numPista = numPista;
    }



    //public void getCasilla(int posicionFila, int posicionColumna){
        //return Casilla[posicionFila][posicionFila];
    //}
}
//Hacer un get casilla