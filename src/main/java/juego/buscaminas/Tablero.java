package juego.buscaminas;

import java.util.Random;

/**
 * Clase Tablero, la cual sera la que tendra las minas y los numeros pistas para poder jugar al buscaminas
 */
public class Tablero {

    Casilla[][] casillas;

    Random random = new Random();
    int Filas;
    int Columnas;
    int Minas;

    /**
     * Metodo constructor del tablero mediante el cual estaran las minas y los numeros pistas para jugar
     * @param Filas Cantidad de filas del tablero
     * @param Columnas Cantidad de columnas del tablero
     * @param Minas Cantidad de minas que contiene el tablero
     */
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
        colocarPistas();
    }

    /**
     * Metodo para colocar minas de manera aleatoria en el tablero
     * @author https://es.stackoverflow.com/questions/219005/generar-numeros-para-una-matriz-en-java para colocar un numero en una posicion aleatoria de la matriz
     */
    public void colocarMinas(){
        int minasColocadas = 0;
        while (minasColocadas != Minas){ //Para evitar colocar minas en lugares en las que ya hay minas
            int auxFila = random.nextInt(Filas); //Para colocar las minas de manera aleatoria
            int auxColum = random.nextInt(Columnas);
            if (!casillas[auxFila][auxColum].isMina()){
                casillas[auxFila][auxColum].setMina(true);
                minasColocadas++;
            }
        }
    }

    /**
     * Metodo para colocar pistas sobre cuantas minas tiene una casilla a su alrededor
     */
    public void colocarPistas (){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()){ //Si la casilla ya tien una mina, no se agregan digitos
                    continue;
                }

                int minasAlrededor = 0;

                for (int iAlrededor = i - 1; iAlrededor < i + 2; iAlrededor++) { //iAlrededor son las filas alrededor de la casilla en la cual queremos poner el numero
                    for (int jAlrededor = j - 1; jAlrededor < j + 2; jAlrededor++) { //jAlrededor recorre las columnas alrededor de la casilla en la cual queremos poner el numero
                        if (iAlrededor < 0 || iAlrededor >= casillas.length || jAlrededor < 0 || jAlrededor >= casillas[i].length) {
                            continue; // La casilla alrededor está fuera de los límites de la matriz no se cuenta
                        }
                        if (casillas[iAlrededor][jAlrededor].isMina()){
                            minasAlrededor++;
                            //System.out.println(minasAlrededor);
                        }
                    }
                }
                casillas[i][j].setNumPista(minasAlrededor);

            }
        }
    }

    public void imprimirTablero(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()){
                    System.out.println("*");
                }else {
                    System.out.println(casillas[i][j].getNumPista());
                }
                //System.out.println(casillas[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }
}
