package edu.escuelaing.arem.ASE.app.Generics;

import java.util.Iterator;

public class MyIterator<E> implements Iterator<E>{

    private MyNode<E> next = null;

    public MyIterator(MyNode<E> next){
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public E next() {
        MyNode<E> currentNext = next;
        if(next != null){
            next = next.next();
        }
        return currentNext.getValue();
    }

}
