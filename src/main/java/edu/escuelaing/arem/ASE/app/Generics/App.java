package edu.escuelaing.arem.ASE.app.Generics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Integer> intList = new LinkedList<Integer>();
        intList.add (0);
        Integer x = intList.iterator().next();

        String [] arrayStr = {"2", "Hola"};
        List<String> llistStr = new LinkedList<String>();
        fromArrayToCollection(arrayStr, llistStr);
    }

    /**
     * static void printCollection(Collection c){
        Iterator i = c.iterator();
        for(int k = 0; k < c.size(); k++){
            System.out.println(i.next());
        }
    }
     * 
     */
    

    static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
        for (T o : a) {
            c.add(o); // Correct
        }
    }

    
}
