package estructuras.tad.lista;

public class NodoEnteros {

        private int dato;
        private NodoEnteros sig;

        public NodoEnteros(int dato) {
            this.dato = dato;
            this.sig = null;
        }

        public NodoEnteros(int dato, NodoEnteros sig) {
            this.dato = dato;
            this.sig = sig;
        }

        public int getDato() {
            return dato;
        }

        public void setDato(int dato) {
            this.dato = dato;
        }

        public NodoEnteros getSig() {
            return sig;
        }

        public void setSig(NodoEnteros sig) {
            this.sig = sig;
        }

        @Override
        public String toString() {
            return dato + "";
        }
    }
