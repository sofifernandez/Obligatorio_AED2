package dominio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ciudad implements Comparable<Ciudad> {
    private String codigo;
    private String nombre;

    public Ciudad() {
    }

    ;

    public Ciudad(String codigo) {
        this.codigo = codigo;
        this.nombre = "";
    }

    public Ciudad(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean esValidoCodigo(String codigo) {
        String regex = "^[A-Z0-9]{5,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codigo);
        return matcher.matches();
    }

    @Override
    public int compareTo(Ciudad o) {
        return this.getCodigo().compareTo(o.getCodigo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Ciudad ciudad = (Ciudad) o;
        return ciudad.getCodigo().equals(this.codigo);
    }

    @Override
    public String toString() {
        return this.getCodigo() + ";" + this.getNombre();
    }
}
