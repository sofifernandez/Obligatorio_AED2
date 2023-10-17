package estructuras.tad.cola;

public interface Cola<T> {
    void encolar(T dato);
    T desencolar();
    boolean esVacia();
    boolean estaLlena();
    int cantElementos();
    T frente();
    void imprimirDatos();
}
