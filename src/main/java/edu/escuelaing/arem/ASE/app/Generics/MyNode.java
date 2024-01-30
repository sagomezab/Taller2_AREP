package edu.escuelaing.arem.ASE.app.Generics;

public class MyNode<E>{
    private E value = null;
    private MyNode<E> next = null;

    public MyNode(E value){
        this.value = value;
        this.next = null;
    }

    public E getValue(){
        return value;
    }

    public MyNode<E> next(){
        return next;
    }

    public void setNext(MyNode<E> next){
        this.next = next;
    }
}

