package juego.buscaminas.EstructurasLineales;

public class Stack {
    private ListaEnlazada stackList;

    public void push(Object newElement){
        this.stackList.insertFirst(newElement);
    }

    public Object pop(){
        return this.stackList.deleteFirst();
    }

    public Object peek(){
        return this.stackList.getHead();
    }
}
