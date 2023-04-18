package juego.buscaminas.EstructurasLineales;


/**
 * Clase que crea una lista enlazada generica la cual va a contener las casillas del tablero
 * @author https://www.youtube.com/watch?v=8xx6A8PIFyE&ab_channel=UniversitatPolit%C3%A8cnicadeVal%C3%A8ncia-UPV
 */
public class ListaEnlazada<E> {

    private Node<E> head;
    private int size;

    /**
     * Metodo constructor de la clase ListaEnlazada
     */
    public ListaEnlazada(){
        this.head = null;
        this.size = 0;
    }

    /**
     * Metodo para obtener la cabeza de la lista
     * @return la cabeza de la lista
     */
    public Node<E> getHead() {
        return head;
    }

    /**
     * Metodo para obtener el tamano de la lista
     * @return el tamano de la lista
     */
    public int size(){
        return this.size;
    }

    /**
     * Metodo para insertar un objeto al inicio de la lista
     * @param data el objeto que queremos insertar
     */
    public void insertFirst(E data){
        Node<E> newNode = new Node<E>(data);
        newNode.setNext(this.head);
        this.head = newNode;
        this.size++;
    }

    /**
     * Metodo para eliminar el primer elemento de la lista
     * @return
     */
    public Node<E> deleteFirst(){
        if (this.head != null){
            Node<E> temp = this.head;
            this.head = this.head.getNext();
            this.size--;
        }else {
            return null;
        }
        return null;
    }

    /**
     * Metodo para borrar un determinado objeto de la lista
     * @param searchValue el ojeto que queremos eliminar
     */
    public Node<E> delete(E searchValue) {
        Node<E> current = this.head;
        Node<E> previous = this.head;

        while (current != null){
            if (current.getData().equals(searchValue)){
                if (current == this.head){
                    this.head = this.head.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                return current;
            } else {
                previous = current;
                current = current.getNext();
            }
        }
        return null;
    }

    /**
     * Metodo para imprimir la lista en la terminal
     */
    public void displayList(){
        Node<E> current = this.head;
        while (current != null){
            System.out.print(current.getData().toString());
            current = current.getNext();
        }
    }

    /**
     * Metodo para encontrar un elemento en la lista
     * @param searchValue el elemento que deseamos encontrar
     * @return en caso de encontrarlo, devuelve el elemento buscado
     */
    public Node<E> find(E searchValue){
        Node<E> current = this.head;
        while (current != null){
            if (current.getData().equals(searchValue)){
                System.out.println("El coso si esta");
                return current;
            }else {
                current = current.getNext();
            }
        }
        return null;
    }


}
