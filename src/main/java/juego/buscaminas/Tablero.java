package juego.buscaminas;

import java.util.Random;

public class Tablero {

    Casilla[][] casillas;

    Random random = new Random();
    int Filas;
    int Columnas;
    int Minas;

    public Tablero(int Filas, int Columnas, int Minas) {
        this.Filas = Filas;
        this.Columnas = Columnas;
        this.Minas = Minas;
        iniciarCasillas();
    }

    /**
     * Metodo para asignar una casilla a cada posicion del tablero
     */
    public void iniciarCasillas(){
        casillas = new Casilla[this.Filas][this.Columnas];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas[i].length; j++){
                casillas[i][j] = new Casilla(i, j);
            }
        }
        colocarMinas();
    }
    //https://es.stackoverflow.com/questions/219005/generar-numeros-para-una-matriz-en-java para colocar un numero en una posicion aleatoria de la matriz
    public void colocarMinas(){
        int minasColocadas = 0;
        while (minasColocadas != Minas){
            int auxFila = random.nextInt(Filas);
            int auxColum = random.nextInt(Columnas);
            if (!casillas[auxFila][auxColum].isMina()){
                casillas[auxFila][auxColum].setMina(true);
                minasColocadas++;
            }
        }
    }
    public void imprimirTablero(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()){
                    System.out.println("*");
                }else {
                    System.out.println("0");
                }
                //System.out.println(casillas[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }


}
