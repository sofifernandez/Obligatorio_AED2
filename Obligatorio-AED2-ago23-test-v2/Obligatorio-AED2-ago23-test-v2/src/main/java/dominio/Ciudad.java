package dominio;

public class Ciudad {
    private String codigo;
    private String nombre;

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

    public boolean validarCodigo(String codigo){ //Esto se puede hacer con exp Regulares pero no las manejo bien
        if (codigo.length()< 5){
            return false;
        }
        for (char c: codigo.toCharArray()){
            if (!Character.isUpperCase(c) && !Character.isDigit(c)) return false;
        }
        return true;
    }


}
