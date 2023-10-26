package dominio;

import interfaz.TipoConexion;

import java.util.Objects;

public class Conexion implements Comparable<Conexion> {
    private int idConexion;
    private double costo;
    private double tiempo;
    private TipoConexion tipo;


    public Conexion(){};
    public Conexion(int id,double costo, double tiempo, TipoConexion tipo) {
        this.idConexion= id;
        this.costo = costo;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public TipoConexion getTipo() {
        return tipo;
    }

    public void setTipo(TipoConexion tipo) {
        this.tipo = tipo;
    }

    public int getIdConexion() {
        return idConexion;
    }

    /*public void setIdConexion(int idConexion) { Asi no se puede cambiar el ID, solo cuando se lo asigna en el constructor
        this.idConexion = idConexion;
    }*/

    public void editar(double costo, double tiempo, TipoConexion tipo){
        this.setCosto(costo);
        this.setTiempo(tiempo);
        this.setTipo(tipo);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conexion conexion = (Conexion) o;
        return idConexion == conexion.idConexion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConexion);
    }

    @Override
    public int compareTo(Conexion o) {
        return 0;
    }
}
