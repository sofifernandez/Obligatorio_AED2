package estructuras.grafo;



import dominio.Conexion;
import estructuras.tad.lista.ListaDinamica;

import java.util.function.Function;

public class Grafo<T> {
    private int tope;
    private int cantidad;
    private Object[] vertices;
    private ListaDinamica verticesAdy;
    private Arista[][] matAdy;

    public Grafo(int unTope, boolean esDirigido) {
        this.tope = unTope;
        this.cantidad = 0;
        this.vertices = new Object[this.tope];
        this.verticesAdy = new ListaDinamica();
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

    public Grafo(int unTope) { //CONSTRUCTOR DE GRAFO NO DIRIGIDO
        this.tope = unTope;
        this.cantidad = 0;
        this.vertices = new Object[this.tope];
        this.verticesAdy = new ListaDinamica();
        this.matAdy = new Arista[this.tope][this.tope];
        for (int i = 0; i < this.tope; i++) {
            for (int j = 0; j < this.tope; j++) {
                this.matAdy[i][j] = new Arista();
            }
        }
    }

    public boolean esLleno() {
        return this.cantidad == this.tope;
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
       ListaDinamica vertAdyacentes = this.verticesAdyacentes(obj);
       for (Object o : vertAdyacentes) {
           int posAdy = this.obtenerPos(o);
           if (!visitados[posAdy] && cantidad > 0) {
               dfs (posAdy, visitados, cantidad - 1, verticesRecorridos);
           }
       }
   }
    public boolean existeCamino(Object inicio, Object destino) {
        boolean[] visitados = new boolean[tope];
        int posI = this.obtenerPos(inicio);
        int posD = this.obtenerPos(destino);
        if (posI >= 0 && posD >= 0) return existeCaminoDFS(this.obtenerPos(inicio), this.obtenerPos(destino), visitados);
        return false;
    }
    private boolean existeCaminoDFS(int pos, int destino, boolean[] visitados) {
        visitados[pos] = true;
        if (pos == destino) {
            return true;
        }
        for (int i = 0; i < this.tope; i++) {
            if (!visitados[i] && matAdy[pos][i].isExiste()) {
                if (existeCaminoDFS(i, destino, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object[] dijkstra(Object vOrigen, Object vDest, Function<T, Double>costExtractor) {
        Object[] result = new Object[2];
        int posOrigen = this.obtenerPos(vOrigen);
        int posDest = this.obtenerPos(vDest);

        boolean[] visitados = new boolean[this.cantidad];
        Object[] anteriores = new Object[this.cantidad];
        double[] costos = new double[this.cantidad];
        Object[] aristasMinimas=new Object[this.cantidad];


        //Inicializar las estructuras
        for (int i = 0; i < this.cantidad; i++) {
            costos[i] = Double.MAX_VALUE;
            anteriores[i] = "**";
        }
        costos[posOrigen] = 0;
        //recorriendo vertices no visitados y obteniendo el menor
        for (int v = 0; v < this.cantidad; v++) {
            int pos = this.obtenerPosMenorVerticeNoVisitado(costos, visitados);
            if (pos > -1) {
                visitados[pos] = true;
                //Evaluar adyacentes y ver si actualizabamos costos
                for (int j = 0; j < this.cantidad; j++) {
                    if (matAdy[pos][j].isExiste() && !visitados[j]) {
                        //DE ESTA FORMA NO ES GENÉRICO
                        //Conexion conexionMinima = (Conexion) matAdy[pos][j].getLista().obtenerMenor(); //Aca me deberia quedar con la conexion minima de la arista
                        //int costoNuevo = costos[pos] + (int)conexionMinima.getTiempo();

                        //DE ESTA FORMA ES GENÉRICO, PERO HUBO QUE AGREGARLE <T>
                        T aristaMenor= (T) matAdy[pos][j].menorDeLaLista();
                        double costoNuevo = costos[pos] + costExtractor.apply(aristaMenor);
                        if (costoNuevo < costos[j]) {
                            costos[j] = costoNuevo;
                            anteriores[j] = vertices[pos];
                            aristasMinimas[j]=aristaMenor;
                        }
                    }
                }
            }
        }

        //GENERAR EL STRING
        int posAct=posDest;
        String camino="";

        while(posAct!=posOrigen && posAct>-1){
            camino= aristasMinimas[posAct] + "|" + vertices[posAct] + "|" +camino;
            posAct = this.obtenerPos(anteriores[posAct]);
        }
        camino= vertices[posOrigen] +"|" +camino;
        camino= camino.substring(0,camino.length()-1);
        result[0]=costos[posDest];
        result[1]=camino;

        return result;
    }


    private int obtenerPosMenorVerticeNoVisitado(double[] costos, boolean[] visitados) {
        int posMin = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < this.cantidad; i++) {
            if (!visitados[i] && costos[i] < min) {
                min = costos[i];
                posMin = i;
            }
        }
        return posMin;
    }


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

    public ListaDinamica verticesAdyacentes(Object vert) {
        ListaDinamica retorno = new ListaDinamica();
        int posVert = obtenerPos(vert);
        for (int i = 0; i < this.cantidad; i++) {
            if (this.matAdy[posVert][i].isExiste()) {
                retorno.insertar((Comparable) this.vertices[i]);
            }
        }

        return retorno;
    }





///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FUNCIONES DE GRAFOS NO UTILIZADAS EN ESTE TRABAJO PERO PODRÍAN SER ÚTILES A FUTURO

    public ListaDinamica getVerticesAdy() {
        return verticesAdy;
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

    // existeVertice(origen) && existeVertice(destino) && existeArista
    public void borrarArista(String origen, String destino) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest] = new Arista();
    }

    // existeVertice(origen) && existeVertice(destino)
    public boolean existeArista(Object origen, Object destino) {

        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].isExiste();
    }

    public ListaDinamica<Conexion> obtenerArista(Object origen, Object destino) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].getLista() ;
    }

    public boolean esVacio() {
        return this.cantidad == 0;
    }

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
