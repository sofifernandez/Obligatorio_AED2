package sistema.auxiliares;

import dominio.Conexion;
import interfaz.TipoConexion;

public class TestConexionDato {
    private TestCiudadDato ciudadOrigen;
    private TestCiudadDato ciudadDestino;
    private int identificadorConexion;
    private double costo;
    private double tiempo;
    private TipoConexion tipo;

    public TestConexionDato(TestCiudadDato ciudadOrigen, TestCiudadDato ciudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.identificadorConexion = identificadorConexion;
        this.costo = costo;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    public TestCiudadDato getCiudadOrigen() {
        return ciudadOrigen;
    }

    public TestCiudadDato getCiudadDestino() {
        return ciudadDestino;
    }

    public int getIdentificadorConexion() {
        return identificadorConexion;
    }

    public double getCosto() {
        return costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public TipoConexion getTipo() {
        return tipo;
    }

    public TestConexionDato invertir() {
        return new TestConexionDato(ciudadDestino, ciudadOrigen, identificadorConexion, costo, tiempo, tipo);
    }

    public TestConexionDato withTipo(TipoConexion tipo) {

        return new TestConexionDato(ciudadOrigen, ciudadDestino, identificadorConexion, costo, tiempo, tipo);
    }
    public TestConexionDato withTiempo(double tiempo) {
        return new TestConexionDato(ciudadOrigen, ciudadDestino, identificadorConexion, costo, tiempo, tipo);
    }

//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TestConexionDato conexion = (TestConexionDato) o;
//        return identificadorConexion == conexion.identificadorConexion;
//    }


    @Override
    public String toString() {
        return "TestConexionDato{" +
                "ciudadOrigen=" + ciudadOrigen +
                ", ciudadDestino=" + ciudadDestino +
                ", identificadorConexion=" + identificadorConexion +
                ", costo=" + costo +
                ", tiempo=" + tiempo +
                ", tipo=" + tipo +
                '}';
    }
}
