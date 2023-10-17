package estructuras.arbol;

import estructuras.tad.lista.ListaEnterosDinamica;

public interface ABB<T> {
    void insertar(T x);

    boolean pertenece(T x);

    String listarAscendente();

    String listarDescendente();

    T borrarMinimo();

    Object[] obtener(T dato);
}
