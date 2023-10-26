package estructuras.grafo;

import dominio.Ciudad;
import estructuras.tad.lista.ListaDinamica;

import java.util.ArrayList;

public class GrafoComplejo {
    private int tope;
    private int cantidad;
    private Object[] vertices; //ESTO NO SÉ SI VA ASÍ O CON <T>
    private ListaDinamica verticesAdy;
    private AristaCompleja[][] matAdy;



    public GrafoComplejo(int unTope) {
        this.tope = unTope;
        this.cantidad = 0;
        this.vertices = new Object[this.tope];
        this.verticesAdy = new ListaDinamica();
        this.matAdy = new AristaCompleja[this.tope][this.tope];
        for (int i = 0; i < this.tope; i++) {
            for (int j = 0; j < this.tope; j++) {
                this.matAdy[i][j] = new AristaCompleja();
            }
        }
        //System.out.println(printVertices());
        //System.out.println(printmatrizAdy());
    }

    public ListaDinamica getVerticesAdy() {
        return verticesAdy;
    }

    public GrafoComplejo(int unTope, boolean esDirigido) {
        this.tope = unTope;
        this.cantidad = 0;
        this.vertices = new Object[this.tope];
        this.verticesAdy = new ListaDinamica();
        this.matAdy = new AristaCompleja[this.tope][this.tope];
        if (esDirigido) {
            for (int i = 0; i < this.tope; i++) {
                for (int j = 0; j < this.tope; j++) {
                    this.matAdy[i][j] = new AristaCompleja();
                }

            }
        } else {
            for (int i = 0; i < this.tope; i++) {
                for (int j = i; j < this.tope; j++) {
                    this.matAdy[i][j] = new AristaCompleja();
                    this.matAdy[j][i] = this.matAdy[i][j];
                }
            }
        }
    }

    public boolean esLleno() {
        return this.cantidad == this.tope;
    }

    public boolean esVacio() {
        return this.cantidad == 0;
    }

    // PRE: !esLleno()
    private int obtenerPosLibre() {
        for (int i = 0; i < this.tope; i++) {
            if (this.vertices[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(Object vert) {
        for (int i = 0; i < this.tope; i++) {
            if (this.vertices[i]!=null && this.vertices[i].equals(vert)) {
                return i;
            }
        }
        return -1;
    }

    public Object obtenerVertice(Object vert){
        for (int i = 0; i < this.tope; i++) {
            if (this.vertices[i]!=null && this.vertices[i].equals(vert)) {
                return this.vertices[i];
            }
        }
        return null;
    }
    // PRE: !esLleno && !existeVertice
    public void agregarVertice(Object vert) {
        int posLibre = obtenerPosLibre();
        System.out.println(posLibre);
        this.vertices[posLibre] = vert;
        this.cantidad++;
    }

    public Object obtenerDatoEnArista(Object origen, Object destino, Object dato) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].datoEnLista(dato);
    }

    public boolean existeDatoEnArista(Object origen, Object destino, Object dato) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].existeEnLista(dato);
    }

   public ListaDinamica dfs(Object obj,int cantidad){
        boolean[] visitados = new boolean[this.cantidad];
        int posOrigen = obtenerPos(obj);
        ListaDinamica verticesRecorridos = new ListaDinamica();
        dfs(posOrigen,visitados, cantidad, verticesRecorridos);
        return verticesRecorridos;
   }
   private void dfs(int pos, boolean[] visitados, int cantidad, ListaDinamica verticesRecorridos) {
       Object obj = this.vertices[pos];
       verticesRecorridos.insertarOrdenado((Comparable) obj);
       visitados[pos] = true;
       ListaDinamica<Ciudad> vertAdyacentes = this.verticesAdyacentes(obj);
       for (Object o : vertAdyacentes) {
           int posAdy = this.obtenerPos(o);
           if (visitados[posAdy] && cantidad > 0) {
               dfs(posAdy, visitados, cantidad - 1, verticesRecorridos);
           }
       }
   }




    // PRE: existeVertice
//    public void borrarVertice(String vert) {
//        int posVert = obtenerPos(vert);
//        this.vertices[posVert] = null;
//        this.cantidad--;
//        for (int i = 0; i < this.tope; i++) {
//            this.matAdy[posVert][i] = new AristaCompleja();
//            this.matAdy[i][posVert] = new AristaCompleja();
//        }
//    }

    public boolean existeVertice(Object vert) {
        return obtenerPos(vert) != -1;
    }

    // existeVertice(origen) && existeVertice(destino) && !existeArista
    public void agregarArista(Object origen, Object destino, Object dato) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest].setExiste(true);
        this.matAdy[posOrig][posDest].agregarALista(dato);
    }

    // existeVertice(origen) && existeVertice(destino)
    public boolean existeArista(String origen, String destino) {

        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].isExiste();
    }

    // existeVertice(origen) && existeVertice(destino) && existeArista
    public void borrarArista(String origen, String destino) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest] = new AristaCompleja();
    }

    public ListaDinamica verticesAdyacentes(Object vert) {
        ListaDinamica retorno = new ListaDinamica();
        int posVert = obtenerPos(vert);
        for (int i = 0; i < this.tope; i++) {
            if (this.matAdy[posVert][i].isExiste()) {
                retorno.insertar((Comparable) this.vertices[i]);
            }
        }

        return retorno;
    }

    // Pre: existeVertice(vert)

//    public Lista<String> verticesIncidentes(String vert) {
//        Lista<String> retorno = new ListaImp<>();
//        int posVert = obtenerPos(vert);
//        for (int i = 0; i < this.tope; i++) {
//            if (this.matAdy[i][posVert].isExiste()) {
//                retorno.insertar(this.vertices[i]);
//            }
//        }
//        //Implementar...
//        return retorno;
//    }



    public int getTope() {
        return tope;
    }

    public Object[] getVertices() {
        return vertices;
    }

    public AristaCompleja[][] getMatAdy() {
        return matAdy;
    }

    ///////////////////////////////////////////////////////////////////
    //FUNCIONES AUXILIARES

    private String printVertices(){
        String output="";
        for (int i = 0; i < this.tope; i++){
            output+= this.vertices[i];
        }
        return output;
    }

    private String printmatrizAdy(){
        String linea = "";
        for (int i = 0; i < this.tope; i++) {
            for (int j = 0; j < this.tope; j++) {
                linea += " " + this.matAdy[i][j].isExiste();
            }
            linea += "\n";
        }
        return linea;
    }
}
