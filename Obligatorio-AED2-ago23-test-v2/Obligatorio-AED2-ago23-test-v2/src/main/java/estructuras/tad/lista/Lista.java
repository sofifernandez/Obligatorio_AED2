package estructuras.tad.lista;

public interface Lista<T extends Comparable<T>> extends Iterable<T> {

    void insertar(T dato);
    public void insertarOrdenado(T valor);
    void borrar(T dato);
    int largo();
    boolean existe(T dato);
    T recuperar(T dato);
    boolean esVacia();
    boolean esLlena();
    void imprimirDatos();
    String obtenerTodosComoString();

    Object obtenerMenor();
    /**
     * Ejercicio 3 - Parte B
     */
    public void insertarFinal(T n);

    /**
     * Ejercicio 3 - Parte C
     */
    public boolean estaOrdenada();
}
