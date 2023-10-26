package estructuras.tad.lista;

import java.util.Iterator;

public class ListaDinamica<T extends Comparable<T>> implements Lista<T> {

    protected NodoLista<T> inicio;
    protected int largo;

    public ListaDinamica() {
        this.inicio = null;
        this.largo = 0;
    }

    @Override
    public void insertar(T dato) {
        inicio = new NodoLista<T>(dato, inicio);
        largo++;
    }

    public void insertarOrdenado(T valor) {
        NodoLista<T> nuevoNodo = new NodoLista<>(valor);

        if (inicio == null || valor.compareTo(inicio.getDato()) < 0) {
            nuevoNodo.setSig(inicio);
            inicio = nuevoNodo;
        } else {
            NodoLista<T> actual = inicio;
            while (actual.getSig() != null && valor.compareTo(actual.getSig().getDato()) >= 0) {
                actual = actual.getSig();
            }
            nuevoNodo.setSig(actual.getSig());
            actual.setSig(nuevoNodo);
        }
    }

    @Override
    public void borrar(T dato) {
        if (inicio.getDato().equals(dato)) {
            inicio = inicio.getSig();
        } else {
            NodoLista<T> aux = inicio;
            while (!aux.getSig().getDato().equals(dato)) {
                aux = aux.getSig();
            }
            aux.setSig(aux.getSig().getSig());
        }
        largo--;
    }

    @Override
    public int largo() {
        return largo;
    }

    @Override
    public boolean existe(T dato) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return true;
            }
            aux = aux.getSig();
        }
        return false;
    }

    @Override
    public T recuperar(T dato) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSig();
        }
        return null;
    }

    @Override
    public boolean esVacia() {
        return largo == 0;
    }

    @Override
    public boolean esLlena() {
        return false;
    }

    @Override
    public void imprimirDatos() {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.getSig() != null){
                System.out.print(aux.getDato() + " -> ");
            }else{
                System.out.print(aux.getDato());
            }
            aux = aux.getSig();
        }
        System.out.println();
    }

    @Override
    public String obtenerTodosComoString() {
        String resultado = "";
        NodoLista<T> actual = inicio;

        while (actual != null) {
            resultado = resultado + (actual.getDato().toString()) + "|";
            actual = actual.getSig();
        }

        return resultado.substring(0,resultado.length()-1);
    }

    /**
     * Ejercicio 3 - Parte B
     */
    @Override
    public void insertarFinal(T n) {
        if (inicio == null) {
            inicio = new NodoLista<T>(n);
        } else {
            insertarFinal(inicio, n);
        }

    }

    /**
     * Ejercicio 3 - Parte B
     */
    private void insertarFinal(NodoLista<T> unNodoLista, T unN) {
        if (unNodoLista.getSig() == null) {
            unNodoLista.setSig(new NodoLista<T>(unN));
        }else {
            insertarFinal(unNodoLista.getSig(), unN);
        }
    }

    /**
     * Ejercicio 3 - Parte C
     */
    @Override
    public boolean estaOrdenada() {
        if(inicio==null){
            return true;
        }else {
            return estaOrdenada(inicio);
        }
    }

    /**
     * Ejercicio 3 - Parte C
     */
    private boolean estaOrdenada(NodoLista<T> unNodo){
        if(unNodo.getSig()==null){
            return true;
        }else{
            return (unNodo.getDato().compareTo(unNodo.getSig().getDato()) < 0)  && estaOrdenada(unNodo.getSig());
        }

    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private NodoLista<T> aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.getDato();
                aux = aux.getSig();
                return dato;
            }

            @Override
            public void remove() {
            }

        };
    }

    public T get(int index) {
        if (index < 0 || index >= largo) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        NodoLista<T> current = inicio;
        for (int i = 0; i < index; i++) {
            current = current.getSig();
        }

        return current.getDato();
    }



}
