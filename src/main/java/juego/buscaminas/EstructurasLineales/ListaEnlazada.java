package juego.buscaminas.EstructurasLineales;

/**
 * Clase que crea una lista enlazada la cual va a contener las casillas del tablero
 */
public class ListaEnlazada {

    private Node head;
    private int size;

    /**
     * Metodo constructor de la clase ListaEnlazada
     */
    public ListaEnlazada(){
        this.head = null;
        this.size = 0;
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
    public void insertFirst(Object data){
        Node newNode = new Node(data);
        newNode.setNext(this.head);
        this.head = newNode;
        this.size++;
    }

    /**
     * Metodo para eliminar el primer elemento de la lista
     * @return
     */
    public Node deleteFirst(){
        if (this.head != null){
            Node temp = this.head;
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
    public Node delete(Object searchValue) {
        Node current = this.head;
        Node previous = this.head;

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
        Node curent = this.head;
        while (curent != null){
            System.out.println(curent.getData().toString());
            curent = curent.getNext();
        }
    }

    /**
     * Metodo para encontrar un elemento en la lista
     * @param searchValue el elemento que deseamos encontrar
     * @return en caso de encontrarlo, devuelve el elemento buscado
     */
    public Node find(Object searchValue){
        Node current = this.head;
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
