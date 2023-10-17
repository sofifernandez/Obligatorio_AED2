package estructuras.tad.lista;






import java.util.Iterator;

public class ListaEnterosDinamica implements ListaEnteros {


    protected NodoEnteros inicio;
    protected int largo;
    private final int tope;

    public ListaEnterosDinamica() {
        this.inicio = null;
        this.largo = 0;
        this.tope = -1;
    }

    public ListaEnterosDinamica(int tope) {
        this.inicio = null;
        this.largo = 0;
        this.tope = tope;
    }

    @Override
    public void insertar(int dato) {
        inicio = new NodoEnteros(dato, inicio);
        largo++;
    }

    @Override
    public void borrar(int dato) {
        if (inicio.getDato() ==dato) {
            inicio = inicio.getSig();
            largo--;
        } else {
            NodoEnteros aux = inicio;
            while (aux.getSig().getDato() != dato) {
                aux = aux.getSig();
            }
            aux.setSig(aux.getSig().getSig());
            largo--;
        }
    }

    @Override
    public int largo() {
        return largo;
    }

    @Override
    public boolean existe(int dato) {
        NodoEnteros aux = inicio;
        while (aux != null) {
            if (aux.getDato() == dato) {
                return true;
            }
            aux = aux.getSig();
        }
        return false;
    }

    @Override
    public int recuperar(int dato) {
        NodoEnteros aux = inicio;
        while (aux != null) {
            if (aux.getDato() == dato) {
                return aux.getDato();
            }
            aux = aux.getSig();
        }
        return 0;
    }

    @Override
    public boolean esVacia() {
        // Puede ser largo = 0;
        return inicio == null;
    }

    @Override
    public boolean esLlena() {
        return false;
    }

    @Override
    public void imprimirDatos() {
        NodoEnteros aux = inicio;
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

    /**
     * Ejercicio 3 - Parte B
     */
    @Override
    public void insertarFinal(int n) {
        if (inicio == null) {
            inicio = new NodoEnteros(n);
        } else {
            insertarFinal(inicio, n);
        }

    }

    /**
     * Ejercicio 3 - Parte B
     */
    private void insertarFinal(NodoEnteros unNodoLista, int unEntero) {
        if (unNodoLista.getSig() == null) {
            unNodoLista.setSig(new NodoEnteros(unEntero));
        }else {
            insertarFinal(unNodoLista.getSig(), unEntero);
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
            return estaOrdenada(inicio.getSig());
        }
    }

    /**
     * Ejercicio 3 - Parte C
     */
    private boolean estaOrdenada(NodoEnteros unNodo){
        //System.out.println("Estoy llamando a estaOrdenada");
        if(unNodo.getSig()==null){
            return true;
        }else{
            return unNodo.getDato() < unNodo.getSig().getDato() && estaOrdenada(unNodo.getSig());
        }
    }


    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            private NodoEnteros aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public Integer next() {
                int dato = aux.getDato();
                aux = aux.getSig();
                return dato;
            }

            @Override
            public void remove() {
            }

        };
    }



}
