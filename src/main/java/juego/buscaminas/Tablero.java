package juego.buscaminas;

public class Tablero {

    Casilla[][] casillas;

    int Filas = 8;
    int Columnas = 8;
    int Minas = 8;

    public Tablero(int Filas, int Columnas, int Minas) {
        this.Filas = Filas;
        this.Columnas = Columnas;
        this.Minas = Minas;
    }

    /**
     * Metodo para asignar una casilla a cada posicion del tablero
     */
    public void iniciarCasillas(){
        casillas = new Casilla[this.Filas][this.Columnas];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; casillas[i].length < 8; j++){
                casillas[i][j] = new Casilla(i, j);
            }
        }
        colocarMinas();
    }
    //https://es.stackoverflow.com/questions/219005/generar-numeros-para-una-matriz-en-java para colocar un numero en una posicion aleatoria de la matriz

}
