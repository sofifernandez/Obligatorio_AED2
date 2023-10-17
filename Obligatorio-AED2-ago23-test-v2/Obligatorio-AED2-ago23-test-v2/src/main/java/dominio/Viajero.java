package dominio;

import interfaz.TipoViajero;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Viajero implements Comparable<Viajero>{
    private String cedula;
    private String nombre;
    private int edad;
    private TipoViajero tipo;
    private int cedulaNumerica;

    public Viajero(){};

    public Viajero(String cedula){
        this.cedula=cedula;
        this.cedulaNumerica=obtenerNumeroCedula();
    }

    public Viajero(String cedula, String nombre, int edad, TipoViajero tipo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
        this.cedulaNumerica=obtenerNumeroCedula();
    }

    private int obtenerNumeroCedula() {
        String cedulaSinGuion = cedula.replaceAll("-.*", "");
        String[] partes = cedulaSinGuion.split("\\.");
        String cedulaNumerica = String.join("", partes);

        // Convierte la cédula normalizada a un número int
        try {

            return Integer.parseInt(cedulaNumerica);
        } catch (NumberFormatException e) {
            // Manejo de error si la cédula no es un número válido
            return 0; // O puedes lanzar una excepción, según tus necesidades.
        }
    }

    public boolean esValido(){
        return !cedula.isBlank() &&
                !nombre.isBlank() &&
                edad<=0 &&
                tipo!=null;
    }


    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public TipoViajero getTipo() {
        return tipo;
    }

    public void setTipo(TipoViajero tipo) {
        this.tipo = tipo;
    }

    public int getCedulaNumerica() {
        return cedulaNumerica;
    }

    @Override
    public int compareTo(Viajero o) {
        return Integer.compare(this.cedulaNumerica, o.getCedulaNumerica());
        //return this.cedula.compareTo(o.getCedula());
    }

    @Override
    public String toString() {
        return this.cedula + ";" + this.nombre + ";" + this.edad+ ";" + this.getTipo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Viajero persona = (Viajero) o;
        return persona.cedula.equals(this.cedula);
    }

}
