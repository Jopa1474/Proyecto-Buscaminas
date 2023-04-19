package juego.buscaminas;

import juego.buscaminas.EstructurasLineales.ListaEnlazada;
import juego.buscaminas.EstructurasLineales.Stack;
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
    boolean DummyMode = true;
    boolean AdvancedMode = false;

    Consumer<List<Casilla>> ePartidaPerdida;
    Consumer<Casilla> eCasillaAbierta;

    ListaEnlazada listaCasillasSinMina = new ListaEnlazada();// Lista de celdas disponibles

    LinkedList<Casilla> listaGeneral = new LinkedList<>();// Lista de celdas disponibles
    LinkedList<Casilla> listaIncertidumbre = new LinkedList<>();// Lista de celdas sin minas
    LinkedList<Casilla> listaSegura = new LinkedList<>();// Lista de celdas con posible mina

    Stack stackListPistas = new Stack();

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
                listaCasillasSinMina.insertFirst(casillas[i][j]);
                listaGeneral.add(casillas[i][j]);
            }
        }
        colocarMinas();
        colocarPistas();
        listaCasillasSinMina.displayList();
        inicializarStackList();
        Pistas();
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
        listaGeneral.removeFirstOccurrence(casillas[posicionFila][posicionColumna]);

        //En caso de que la lista de casillas este vacia, se gana el juego
        if (listaCasillasSinMina == null) {
            JOptionPane.showMessageDialog(null, "Felicidades, has ganado!");
        }else {
            listaCasillasSinMina.delete(casillas[posicionFila][posicionColumna]);
        }

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
            //setPartidaPerdida(true);
            //System.exit(0);

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

    /**
     * Metodo que selecciona una posicion aleatoria de este caso la lista general
     * @return
     */
    private Casilla seleccionarPosicionAleatoria() {
        Random random = new Random();
        int indice = random.nextInt(listaGeneral.size());
        Casilla posicion = listaGeneral.get(indice);
        if (!posicion.isMina()){
            selecCasilla(posicion.getPosicionFila(), posicion.getPosicionColumna());
        }
        return posicion;
    }

    /**
     * Metodo para actualizar las listas y eliminar una celda de la lista general
     * @param posicion posicion de la lista general
     */
    private void actualizarListas(Casilla posicion) {
        listaGeneral.removeFirstOccurrence(posicion);
        if (posicion.isMina()) {
            listaIncertidumbre.add(posicion);
        } else {
            listaSegura.add(posicion);
        }
        System.out.println("Lista general: " + listaGeneral);
        System.out.println("Lista segura: " + listaSegura);
        System.out.println("Lista incertidumbre: " + listaIncertidumbre);
    }

    /**
     * Método para que la compu seleccione una casilla en base a las listas predeterminadas
     */
    public void AdvancedLevel() {
        if (isAdvancedMode()){
            // Crear lista general actualizada con las celdas disponibles
            LinkedList<Casilla> listaGeneralActualizada = new LinkedList<Casilla>(listaGeneral);
            for (Casilla posicion : listaSegura) {
                listaGeneralActualizada.removeFirstOccurrence(posicion);
            }
            for (Casilla posicion : listaIncertidumbre) {
                listaGeneralActualizada.removeFirstOccurrence(posicion);
            }
            // Selecciona una posición aleatoria de la lista general actualizada
            Casilla posicion = seleccionarPosicionAleatoria();
            // Actualizar las listas
            actualizarListas(posicion);
        }
    }

    /**
     * Metodo para anadir todas las casillas sin minas a la Pila
     */
    public void inicializarStackList(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (!casillas[i][j].isMina()) {
                    stackListPistas.push(casillas[i][j]);
                }
            }
        }
    }

    /**
     * Metodo encargado de proporcionar una pista al jugador
     */
    public void Pistas() {
        //Casilla casilla = (Casilla) stackListPistas.peek();
        //selecCasilla(casilla[casilla.getPosicionFila()], casilla.getPosicionColumna());
    }

    /**
     * Metodo que imprime en la consola el tablero del buscaminas con sus pistas y minas
     */
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

    /**
     * Metodo para saber si DummyMode es true o false
     * @return true o false
     */
    public boolean isDummyMode() {
        return DummyMode;
    }

    /**
     * Metodo para setear a DummyMode como true o false
     * @param dummyMode
     */
    public void setDummyMode(boolean dummyMode) {
        DummyMode = dummyMode;
    }

    /**
     * Metodo para cuando DummyMode sea true, AdvancedMode sea false
     */
    public void setDummyLevel(){
        if (!DummyMode){
            setDummyMode(true);
            setAdvancedMode(false);
        }
    }

    /**
     * Metodo para cuando AdvancedMode sea true, DummyMode sea false
     */
    public void setAdvancedLevel(){
        if(!AdvancedMode){
            setDummyMode(false);
            setAdvancedMode(true);
        }
    }

    /**
     * Metodo para saber si AdvancedMode es true o false
     * @return true o false
     */
    public boolean isAdvancedMode() {
        return AdvancedMode;
    }

    /**
     * Metodo para setear a AdvancedMode como true o false
     * @param advancedMode
     */
    public void setAdvancedMode(boolean advancedMode) {
        AdvancedMode = advancedMode;
    }
}
