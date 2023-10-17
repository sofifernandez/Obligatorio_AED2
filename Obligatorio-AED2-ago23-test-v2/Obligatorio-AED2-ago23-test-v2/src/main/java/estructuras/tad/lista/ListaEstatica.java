package estructuras.tad.lista;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ListaEstatica<T extends Comparable<T>> implements Lista<T > {

    private final T[] array;
    private int cantElementos;
    private final int tamanio;
    private int indice;

    public ListaEstatica(Class<T> clase, int tamanio) {
        array = (T[]) Array.newInstance(clase, tamanio);
        for (int i = 0; i < tamanio; i++) {
            array[i] = null;
        }

        this.tamanio = tamanio;
        this.cantElementos = 0;
        this.indice = tamanio - 1;
    }

    /**
     * PRE: No debe estar lleno
     */
    @Override
    public void insertar(T dato) {
        array[indice] = dato;
        indice--;
        cantElementos++;

    }

    /**
     * PRE: Debe existir el elemento a borrar
     */
    @Override
    public void borrar(T dato) {
        System.out.println(dato);
        int posBorrado = -1;
        for (int i = 0; i < tamanio; i++) {
            if (array[i] == dato) {
                array[i] = null;
                posBorrado = i;
                cantElementos--;
            }
        }

        // Muevo los elementos
        if (posBorrado != -1) {
            boolean encontreNull = false;
            for (int i = posBorrado; i > 0; i--) {
                if (!encontreNull) {
                    if (array[i-1] != null) {
                        array[i] = array[i - 1];
                    } else {
                        encontreNull = true;
                        array[i]=null;
                        indice = i;
                    }
                }
            }
        }
    }

    @Override
    public int largo() {
        return cantElementos;
    }

    @Override
    public boolean existe(T dato) {
        boolean encontreElem = false;
        for (int i = 0; i < tamanio; i++) {
            if (array[i] == dato && !encontreElem) {
                encontreElem = true;
            }
        }
        return encontreElem;
    }

    @Override
    public T recuperar(T dato) {
        boolean encontreElem = false;
        T datoADevolver=null;
        for (int i = 0; i < tamanio; i++) {
            if (array[i] == dato && !encontreElem) {
                encontreElem = true;
                datoADevolver=array[i];
            }
        }
        return datoADevolver;
    }

    @Override
    public boolean esVacia() {
        return cantElementos==0;
    }

    @Override
    public boolean esLlena() {
        return cantElementos==tamanio;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public void imprimirDatos(){
        for (int i = 0; i < tamanio; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    @Override
    public void insertarFinal(T n) {

    }

    @Override
    public boolean estaOrdenada() {
        return false;
    }
}
