package estructuras.grafo;

import estructuras.tad.lista.ListaDinamica;

public class Arista<T> {

    private boolean existe;
    private ListaDinamica lista;

    public Arista() {
        this.existe = false;
        this.lista = new ListaDinamica();
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public ListaDinamica getLista() {
        return lista;
    }

    public void setLista(ListaDinamica lista) {
        this.lista = lista;
    }

    public void agregarALista(Object obj){
        lista.insertar((Comparable) obj);
    }

    public boolean existeEnLista(Object obj){
        return lista.existe((Comparable) obj);
    }

    public Object datoEnLista(Object obj){
        return lista.recuperar((Comparable) obj);
    }

    public T menorDeLaLista(){
         return (T) this.lista.obtenerMenor();
    }
}
