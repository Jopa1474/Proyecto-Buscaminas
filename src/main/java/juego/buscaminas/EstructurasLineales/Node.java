package juego.buscaminas.EstructurasLineales;

/**
 * Clase que crea un nodo el cual va dentro de la clase ListaEnlazada
 */
public class Node<E>{
    private E data;
    private Node<E> next;

    /**
     * Metodo constructor para la clase Node
     * @param data
     */
    public Node(E data){
        this.next = null;
        this.data = data;
    }

    /**
     * Metodo para obtener la data del objeto que se encuentra en un determinado nodo
     * @return la data del objeto que se encuentra en el nodo
     */
    public E getData(){
        return this.data;
    }

    /**
     * Metodo para asignarle una data al nodo
     */
    public void setData(E data){
        this.data = data;
    }

    /**
     * Metodo para obtener el siguiente nodo en la lista enlazada
     * @return el siguiente nodo de la lista enlazada
     */
    public Node<E> getNext(){
        return this.next;
    }

    /**
     * Metodo para asignar un nodo como el siguiente en la lista enlazada
     * @param node el nodo que queremos asignar como siguiente
     */
    public  void setNext(Node<E> node){
        this.next = node;
    }
}
