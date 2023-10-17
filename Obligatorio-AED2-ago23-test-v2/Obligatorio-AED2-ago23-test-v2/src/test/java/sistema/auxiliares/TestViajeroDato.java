package sistema.auxiliares;

import dominio.Viajero;
import interfaz.TipoViajero;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestViajeroDato implements Comparable<TestViajeroDato> {
    private String cedula;
    private String nombre;
    private int edad;
    private TipoViajero tipoViajero;

    public TestViajeroDato(String cedula, String nombre, int edad, TipoViajero tipoViajero) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.tipoViajero = tipoViajero;
    }



    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public TipoViajero getTipoViajero() {
        return tipoViajero;
    }

    @Override
    public String toString() {
        return "{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", tipoViajero=" + tipoViajero +
                '}';
    }

    public static TestViajeroDato fromString(String resultado){
        try {
            String[] partes = resultado.split(";");
            return new TestViajeroDato(partes[0],partes[1],Integer.parseInt(partes[2]),TipoViajero.valueOf(partes[3]));
        }catch (Exception e){
            System.out.println(e.toString());
            Logger.getLogger(TestViajeroDato.class.getCanonicalName()).warning("No pude leer el viajero: '"+resultado+"'");
            return null;
        }
    }

    public TestViajeroDato copia() {
        return new TestViajeroDato(new String(cedula),new String(nombre),edad,tipoViajero);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //if (o == null || getClass() != o.getClass()) return false;
        TestViajeroDato that = (TestViajeroDato) o;
        //return edad == that.edad && Objects.equals(cedula, that.cedula) && Objects.equals(nombre, that.nombre) && tipoViajero == that.tipoViajero;
        return edad == that.edad && Objects.equals(cedula, that.cedula) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula, nombre, edad, tipoViajero);
    }

    @Override
    public int compareTo(TestViajeroDato o) {
        return this.cedula.compareTo(o.getCedula());
    }
}
