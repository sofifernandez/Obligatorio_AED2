package estructuras.grafo;

import dominio.Ciudad;
import estructuras.tad.lista.Lista;
import estructuras.tad.lista.ListaDinamica;

import java.sql.SQLOutput;

public class Grafo<T> {
    private int tope;
    private int cantidad;
    private Object[] vertices; //ESTO NO SÉ SI VA ASÍ O CON <T>
    private Arista[][] matAdy;

    public Grafo(int unTope) {
        this.tope = unTope;
        this.cantidad = 0;
        this.vertices = new Object[this.tope];
        this.matAdy = new Arista[this.tope][this.tope];
        for (int i = 0; i < this.tope; i++) {
            for (int j = 0; j < this.tope; j++) {
                this.matAdy[i][j] = new Arista();
            }
        }
        //System.out.println(printVertices());
        //System.out.println(printmatrizAdy());
    }

    public Grafo(int unTope, boolean esDirigido) {
        this.tope = unTope;
        this.cantidad = 0;
        this.vertices = new Object[this.tope];
        this.matAdy = new Arista[this.tope][this.tope];
        if (esDirigido) {
            for (int i = 0; i < this.tope; i++) {
                for (int j = 0; j < this.tope; j++) {
                    this.matAdy[i][j] = new Arista();
                }

            }
        } else {
            for (int i = 0; i < this.tope; i++) {
                for (int j = i; j < this.tope; j++) {
                    this.matAdy[i][j] = new Arista();
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




    // PRE: !esLleno && !existeVertice
    public void agregarVertice(Object vert) {
        int posLibre = obtenerPosLibre();
        System.out.println(posLibre);
        this.vertices[posLibre] = vert;
        this.cantidad++;
    }

    //TODO: esto se puede hacer así? o hay que pasarle el objeto?

    // PRE: existeVertice
    public void borrarVertice(String vert) {
        int posVert = obtenerPos(vert);
        this.vertices[posVert] = null;
        this.cantidad--;
        for (int i = 0; i < this.tope; i++) {
            this.matAdy[posVert][i] = new Arista();
            this.matAdy[i][posVert] = new Arista();
        }
    }

    public boolean existeVertice(Object vert) {
        return obtenerPos(vert) != -1;
    }

    // existeVertice(origen) && existeVertice(destino) && !existeArista
    public void agregarArista(String origen, String destino, int peso) {
        //Implementar...
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest].setExiste(true);
        this.matAdy[posOrig][posDest].setPeso(peso);
    }

    // existeVertice(origen) && existeVertice(destino)
    public boolean existerArista(String origen, String destino) {
        //Implementar...
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].isExiste();
    }

    // existeVertice(origen) && existeVertice(destino) && existeArista
    public void borrarArista(String origen, String destino) {
        //Implementar...
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest] = new Arista();
    }

//    public Lista<String> verticesAdyacentes(String vert) {
//        Lista<String> retorno = new ListaImp<>();
//        int posVert = obtenerPos(vert);
//        for (int i = 0; i < this.tope; i++) {
//            if (this.matAdy[posVert][i].isExiste()) {
//                retorno.insertar(this.vertices[i]);
//            }
//        }
//        //Implementar...
//        return retorno;
//    }

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

    private void imprimirMatrizAdy(){
        for (int i = 0; i < this.tope; i++) {
            String linea = "";
            for (int j = 0; j < this.tope; j++) {
                linea += " " + matAdy[i][j].getPeso();
            }
            System.out.println(" " + linea);
        }
    }


    public int getTope() {
        return tope;
    }

    public Object[] getVertices() {
        return vertices;
    }

    public Arista[][] getMatAdy() {
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
                linea += " " + this.matAdy[i][j].getPeso();
            }
            linea += "\n";
        }
        return linea;
    }
}
