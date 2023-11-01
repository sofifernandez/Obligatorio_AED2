package estructuras.arbol;

public interface ABB<T> {
    void insertar(T x);

    boolean pertenece(T x);

    String listarAscendente();

    String listarDescendente();

    T borrarMinimo();

    Object[] obtener(T dato);
}
