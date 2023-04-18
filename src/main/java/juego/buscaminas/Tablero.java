package juego.buscaminas;

import juego.buscaminas.EstructurasLineales.ListaEnlazada;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Clase Tablero, la cual sera la que tendra las minas y los numeros pistas para poder jugar al buscaminas
 */
public class Tablero  {

    Casilla[][] casillas;
    Random random = new Random();
    int Filas;
    int Columnas;
    int Minas;
    ListaEnlazada casillasNoSelec;
    boolean DummyMode = false;
    boolean AdvancedMode = false;
    boolean PartidaPerdida = false;
    Consumer<List<Casilla>> ePartidaPerdida;
    Consumer<Casilla> eCasillaAbierta;

    //ListaEnlazada listaGeneral = new ListaEnlazada();
    //ListaEnlazada listaIncertidumbre = new ListaEnlazada();
    //ListaEnlazada listaSegura = new ListaEnlazada();

    private ListaEnlazada<Casilla> listaGeneral = new ListaEnlazada<>(); // Lista de celdas disponibles
    private ListaEnlazada<Casilla> listaSegura = new ListaEnlazada<>();  // Lista de celdas sin minas
    private ListaEnlazada<Casilla> listaIncertidumbre = new ListaEnlazada<>(); // Lista de celdas con posible mina


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
        casillasNoSelec = new ListaEnlazada();
        casillas = new Casilla[this.Filas][this.Columnas];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas[i].length; j++){
                casillas[i][j] = new Casilla(i, j);
                //casillasNoSelec.insertFirst(casillas[i][j]);
                listaGeneral.insertFirst(casillas[i][j]);
            }
        }
        colocarMinas();
        colocarPistas();
        //casillasNoSelec.displayList();
        listaGeneral.displayList();

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

    /**
     * Metodo que realiza la accion de seleccionar la casilla a la hora de pesionarla y que dependiendo si tiene mina, o si su pista es igual a 0 o no, realiza acciones diferentes
     * @param posicionFila posicion de la fila de la casilla
     * @param posicionColumna posicion de la columna de la casilla
     * @author https://www.youtube.com/watch?v=JktYk991hEY&list=PLhbSLFs0SUZbafb6mA5JeRLqnl7S41wIj&index=4&ab_channel=BelisarioDeLaMata
     */
    public void selecCasilla(int posicionFila, int posicionColumna){
        eCasillaAbierta.accept(casillas[posicionFila][posicionColumna]); //Si se selecciona una casilla que solo contiene un numero de pista normal, solo se abre ella
        casillas[posicionFila][posicionColumna].setAbierta(true);
        listaGeneral.delete(casillas[posicionFila][posicionColumna]);

        //Si tiene mina, abre todas las casillas con minas
        if (this.casillas[posicionFila][posicionColumna].isMina()){
            List<Casilla> casillasConMinas = new LinkedList<>();
            for (int i = 0; i < casillas.length; i++){
                for (int j = 0; j < casillas[i].length; j++){
                    if (casillas[i][j].isMina()){
                        casillasConMinas.add(casillas[i][j]);
                        casillas[i][j].setAbierta(true);

                    }
                }
            }
            ePartidaPerdida.accept(casillasConMinas);
            JOptionPane.showMessageDialog(null,"Has perdido!");
            setPartidaPerdida(true);

        //Si el numero de pista es igual a 0, abre todas las casillas a su alrededor que sean 0 tambien hasta llegar a casillas con numero de pista mayor a 0
        }else if (this.casillas[posicionFila][posicionColumna].getNumPista() == 0){
            List<Casilla> casillassAlrededor = casillasAlrededor(posicionFila, posicionColumna);

            for (Casilla casilla: casillassAlrededor){
                if (!casilla.isAbierta()){
                    casilla.setAbierta(true);
                    selecCasilla(casilla.getPosicionFila(), casilla.getPosicionColumna());
                }
            }
        }
    }

    /**
     * Metodo para obtener de mejor manera las casillas alrededor de una
     * @param posicionFila posicion en la fila
     * @param posicionColumn posicion en la columna
     * @return las casillas que tiene alrededor
     */
    public List<Casilla> casillasAlrededor(int posicionFila, int posicionColumn){
        List<Casilla> casillasAlrededor = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            int tempPosFila = posicionFila;
            int tempPosColumn = posicionColumn;
            switch (i){
                case 0: tempPosFila --;break;
                case 1: tempPosFila --;tempPosColumn++;break;
                case 2: tempPosColumn ++;break;
                case 3: tempPosColumn++;tempPosFila++;break;
                case 4: tempPosFila ++;break;
                case 5: tempPosFila ++;tempPosColumn--;break;
                case 6: tempPosColumn --;break;
                case 7: tempPosFila --;tempPosColumn--;break;

            }

            if (tempPosFila>=0 && tempPosFila<this.casillas.length && tempPosColumn>=0 && tempPosColumn<this.casillas[0].length){
                casillasAlrededor.add(casillas[tempPosFila][tempPosColumn]);
            }
        }
        return casillasAlrededor;
    }


    public void setePartidaPerdida(Consumer<List<Casilla>> ePartidaPerdida) {
        this.ePartidaPerdida = ePartidaPerdida;
    }

    public void seteCasillaAbierta(Consumer<Casilla> eCasillaAbierta) {
        this.eCasillaAbierta = eCasillaAbierta;
    }

    /**
     * Metodo que hace que la compu elija una casilla al azar
     */
    public void DummyLevel(){
        if (DummyMode){
            int auxFila = random.nextInt(Filas); //Para colocar seleccionar una casilla de manera aleatoria
            int auxColum = random.nextInt(Columnas);

            if (!casillas[auxFila][auxColum].isAbierta()){
                selecCasilla(auxFila,auxColum);
            }else{
                DummyLevel();
            }
        }
    }

    public void AdvancedLevel(){
        ListaEnlazada listaGeneralActualizada = new ListaEnlazada();

    }

    public void Pistas(){

    }


    public void imprimirTablero(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()){
                    System.out.print("*");
                }else {
                    System.out.print(casillas[i][j].getNumPista());
                }
            }
            System.out.println("");
        }
    }



    public boolean isDummyMode() {
        return DummyMode;
    }

    public void setDummyMode(boolean dummyMode) {
        DummyMode = dummyMode;
    }

    public void setDummyLevel(){
        if (!DummyMode){
            setDummyMode(true);
        }else {
            setDummyMode(false);
        }
    }

    public boolean isPartidaPerdida() {
        return PartidaPerdida;
    }

    public void setPartidaPerdida(boolean partidaPerdida) {
        PartidaPerdida = partidaPerdida;
    }
}
