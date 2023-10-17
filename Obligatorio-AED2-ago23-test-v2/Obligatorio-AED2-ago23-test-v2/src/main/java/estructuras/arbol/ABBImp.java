package estructuras.arbol;


import estructuras.tad.lista.ListaEnterosDinamica;

import java.util.LinkedList;
import java.util.Queue;

public class ABBImp <T extends Comparable<T>> implements ABB<T> {
    private NodoABB<T> raiz;

    public ABBImp(NodoABB<T> raiz) {
        this.raiz = raiz;
    }

    public ABBImp() {
        this.raiz = null;
    }


    @Override
    public void insertar(T dato) {
        if (this.raiz == null) {
            this.raiz = new NodoABB(dato);
        } else {
            insertar(this.raiz, dato);
        }
    }

    private void insertar(NodoABB<T> nodo, T dato) {
        if (nodo.getDato().compareTo(dato) > 0) {
            if (nodo.getIzq() == null) {
                nodo.setIzq(new NodoABB<T>(dato));
            } else {
                insertar(nodo.getIzq(), dato);
            }
        } else {
            if (nodo.getDer() == null) {
                nodo.setDer(new NodoABB<T>(dato));
            } else {
                insertar(nodo.getDer(), dato);
            }
        }
    }

    @Override
    public boolean pertenece(T dato) {
        return pertenece(this.raiz, dato);
    }

    private boolean pertenece(NodoABB<T> nodo, T dato) {

        if (nodo == null) {
            return false;
        }
        if (dato.compareTo(nodo.getDato()) == 0) {
            return true;
        }
        if (dato.compareTo(nodo.getDato()) > 0) {
            return pertenece(nodo.getDer(), dato);
        } else {
            return pertenece(nodo.getIzq(), dato);
        }
    }

    @Override
    public String listarAscendente() {
        if (this.raiz != null) {
            return listarAscendente(this.raiz);
        } else {
           return "";
        }
    }

    private String listarAscendente(NodoABB<T> nodo) {
        if(nodo!=null){
            return listarAscendente(nodo.getIzq())  + nodo.getDato() + "|" + listarAscendente(nodo.getDer());
        }
        return "";

    }


    @Override
    public String listarDescendente() {
        if (this.raiz != null) {
            return listarDescendente(this.raiz);
        } else {
           return "";
        }
    }

    private String listarDescendente(NodoABB<T> nodo) {
        if (nodo == null) {
            return "";
        }
        return listarDescendente(nodo.getDer()) + "|" + nodo.getDato().toString() +  listarDescendente(nodo.getIzq());
    }

    @Override
    public T borrarMinimo() {
        return null;
    }

    @Override
    public Object[] obtener(T dato) {
        return  obtener(this.raiz,dato, 0);
    }

    private Object[] obtener(NodoABB<T> nodo, T dato, int pasos){
        if (nodo != null) {
            if (nodo.getDato().equals(dato)) {
                Object[] result = new Object[] {nodo.getDato(), pasos};
                return result;
            }
            if (dato.compareTo(nodo.getDato()) > 0) {
                return obtener(nodo.getDer(), dato, pasos+1);
            }
            return obtener(nodo.getIzq(), dato, pasos+1);
        }
        return null;
    }









    //FUNCION PARA IMPRIMIR ARBOL CON FORMA --> chatgpt :)
    public void printTreeABB() {
        int treeHeight = getHeight(this.raiz);
        int maxWidth = (int) Math.pow(2, treeHeight) - 1;

        Queue<NodoABB> queue = new LinkedList<>();
        queue.offer(this.raiz);

        for (int i = 0; i < treeHeight; i++) {
            int levelSize = queue.size();
            int levelSpacing = maxWidth / (levelSize + 1);

            for (int j = 0; j < levelSize; j++) {
                NodoABB node = queue.poll();
                if (node != null) {
                    printSpaces(levelSpacing);
                    System.out.print(node.getDato());
                    printSpaces(levelSpacing);
                    queue.offer(node.getIzq());
                    queue.offer(node.getDer());
                } else {
                    printSpaces(2 * levelSpacing + 1);
                }
            }
            System.out.println();
        }
    }

    private static void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private static int getHeight(NodoABB node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeight(node.getIzq());
        int rightHeight = getHeight(node.getDer());
        return Math.max(leftHeight, rightHeight) + 1;
    }




}
