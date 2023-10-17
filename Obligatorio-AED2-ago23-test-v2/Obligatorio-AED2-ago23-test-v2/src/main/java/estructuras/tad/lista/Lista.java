package estructuras.tad.lista;

public interface Lista<T extends Comparable<T>> extends Iterable<T> {

    void insertar(T dato);
    void borrar(T dato);
    int largo();
    boolean existe(T dato);
    T recuperar(T dato);
    boolean esVacia();
    boolean esLlena();
    void imprimirDatos();

    /**
     * Ejercicio 3 - Parte B
     */
    public void insertarFinal(T n);

    /**
     * Ejercicio 3 - Parte C
     */
    public boolean estaOrdenada();
}
