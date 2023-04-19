package juego.buscaminas.EstructurasLineales;

/**
 * Clase que crea una Pila mediante el uso de una lista enlazada
 */
public class Stack {
    private ListaEnlazada stackList = new ListaEnlazada();

    /**
     * Metodo que agrega un elemento al inicio de la Pila
     * @param newElement el elemento que deseamos agregar
     */
    public void push(Object newElement){
        this.stackList.insertFirst(newElement);
    }

    /**
     * Metodo que elimina al primer elemento de la Pila
     * @return
     */
    public Object pop(){
        return this.stackList.deleteFirst();
    }

    /**
     * Metodo que obtiene el elemento de la primera posicion de la Pila
     * @return
     */
    public Object peek(){
        return this.stackList.getHead();
    }

    /**
     * Metodo que imprime en la consola el estado actual de la Pila
     */
    public void imprimirStack(){
        stackList.displayList();
    }

}
