package estructuras.tad.lista;

public interface ListaEnteros extends Iterable<Integer> {

    void insertar(int dato);
    void borrar(int dato);
    int largo();
    boolean existe(int dato);
    int recuperar(int dato);
    boolean esVacia();
    boolean esLlena();
    void imprimirDatos();

    /**
     * Ejercicio 3 - Parte B
     */
    public void insertarFinal(int n);

    /**
     * Ejercicio 3 - Parte C
     */
    public boolean estaOrdenada();
}
