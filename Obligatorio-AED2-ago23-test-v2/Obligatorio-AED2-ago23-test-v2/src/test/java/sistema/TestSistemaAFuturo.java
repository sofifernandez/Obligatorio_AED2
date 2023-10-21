package sistema;

import dominio.Ciudad;
import dominio.Conexion;
import interfaz.Retorno;
import interfaz.Sistema;
import interfaz.TipoConexion;
import interfaz.TipoViajero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sistema.auxiliares.TestViajeroDato;

import static java.lang.String.format;
import static sistema.AccionesPasajero.agregoElViajero;
import static sistema.AccionesSistema.tengoUnSistemaValido;
import static sistema.AuxAsserciones.*;

public class TestSistemaAFuturo {
    Sistema s = new ImplementacionSistema();

    @Test
    public void testAFuturo() {

        Assertions.assertEquals(Retorno.ok(), s.inicializarSistema(500));
    }


    private static final Ciudad[] CIUDADES_VALIDAS_ORDENADAS = new Ciudad[]{
            new Ciudad("AMSTR8", "Ámsterdam"),
            new Ciudad("ATHNS9", "Atenas"),
            new Ciudad("BERLN3", "Berlín"),
            new Ciudad("DUBLI7", "Dublín"),
            new Ciudad("LOND02", "Londres"),
            new Ciudad("MADR4", "Madrid"),
            new Ciudad("PAR322", "París"),

            new Ciudad("ROMA01", "Roma"),
            new Ciudad("VIENN6", "Viena")
    };


    private static final Conexion[] CONEXIONES_VALIDAD= new Conexion[]{
            new Conexion(1,1000,5, TipoConexion.RUTA_AEREA),
            new Conexion(2,500,5, TipoConexion.RUTA_FERROVIARIA),
            new Conexion(3,200,5, TipoConexion.RUTA_NACIONAL),
            new Conexion(4,100,5, TipoConexion.RUTA_NACIONAL),
            new Conexion(5,2000,5, TipoConexion.RUTA_AEREA),
            new Conexion(6,800,5, TipoConexion.RUTA_MARITIMA),
    };

    @Test
    public void testAgregarOk() {
        //dado que
        s.inicializarSistema(10);
        Sistema s = tengoUnSistemaValido();
        for (int i = 0; i < CIUDADES_VALIDAS_ORDENADAS.length; i++) {
            Ciudad ciudad = CIUDADES_VALIDAS_ORDENADAS[i];
            System.out.println(ciudad);
            Retorno resultado = s.registrarCiudad(ciudad.getCodigo(), ciudad.getNombre());
            checkearOk(resultado, format("La ciudad '%s' deberia haberse agregado correctamente", ciudad));
        }
    }

    @Test
    public void testAgregarError1() {

    }

    @Test
    public void testAgregarError2() {

    }

    @Test
    public void testAgregarError3() {
        testAgregarOk();
        checkearError3(s.registrarCiudad("PRGUE5oo","Praga"),"Deberia haber dado error");

    }

    public void testAgregarConexion(){

    }

}
