package sistema;

import dominio.Viajero;
import estructuras.arbol.ABBImp;
import interfaz.*;

import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplementacionSistema implements Sistema {

    private ABBImp arbolViajeros=new ABBImp();
    private ABBImp arbolPremium=new ABBImp();
    private ABBImp arbolEstandar=new ABBImp();
    private ABBImp arbolCasual=new ABBImp();

    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        if(maxCiudades>5){
            arbolViajeros=new ABBImp();
            arbolPremium=new ABBImp();
            arbolEstandar=new ABBImp();
            arbolCasual=new ABBImp();
            return Retorno.ok();
        }
        return Retorno.error1("El sistema debe tener más de 5 ciudades");
    }

    private boolean esVacioONulo(String texto){
        return texto==null || texto.isBlank() || texto.isEmpty();
    }

    @Override
    public Retorno registrarViajero(String cedula, String nombre, int edad, TipoViajero tipo) {
        if(esVacioONulo(cedula) || esVacioONulo(nombre) || edad<0 || tipo==null) return Retorno.error1("");
        if(!esValidoFormatoCedula(cedula)) return Retorno.error2("");
        if(arbolViajeros.pertenece(new Viajero(cedula, nombre, edad, tipo))) return Retorno.error3("YA EXISTE");

        Viajero viajero=new Viajero(cedula, nombre, edad, tipo);
        arbolViajeros.insertar(viajero);

        int tipoViajero=viajero.getTipo().getIndice();

        switch (tipoViajero) {
            case 0:
                arbolPremium.insertar(viajero);
                break;
            case 1:
                arbolEstandar.insertar(viajero);
                break;
            case 2:
                arbolCasual.insertar(viajero);
                break;
            default:
                System.out.println("El número no coincide con ningún caso.");
        }
        return Retorno.ok();
    }

    @Override
    public Retorno buscarViajero(String cedula) {
        if(!esValidoFormatoCedula(cedula)){
            return Retorno.error1("");
        }
        Viajero buscado=new Viajero(cedula);
        Object[] encontrado= arbolViajeros.obtener(buscado);
        if(encontrado!=null){
            buscado=(Viajero) encontrado[0];
            int pasos= (int) encontrado[1];
            return Retorno.ok(pasos, buscado.toString());
        }
        return Retorno.error2("NO ENCONTRADO");
    }

    @Override
    public Retorno listarViajerosAscendente() {
        String lista=arbolViajeros.listarAscendente();
        if(lista.length()>0){
            lista= lista.substring(0, lista.length()-1);
        }
        return Retorno.ok(lista);

    }

    @Override
    public Retorno listarViajerosDescendente() {
        String lista=arbolViajeros.listarDescendente();
       if(lista.length()>0){
           lista= lista.substring(1, lista.length());
       }
        return Retorno.ok(lista);
    }

    @Override
    public Retorno listarViajerosPorTipo(TipoViajero tipo) {
        if(tipo ==null) return Retorno.error1("");
        int indice= tipo.getIndice();
        String lista="";
        switch (indice) {
            case 0:
                lista=arbolPremium.listarAscendente();
                return Retorno.ok(lista);

            case 1:
                lista=arbolEstandar.listarAscendente();
                return Retorno.ok(lista);

            case 2:
                lista= arbolCasual.listarAscendente();
                return Retorno.ok(lista);
            default:
                System.out.println("El número no coincide con ningún caso.");
        }
        return Retorno.error1(lista);
    }

    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno actualizarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listadoCiudadesCantTrasbordos(String codigo, int cantidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimo(String codigoCiudadOrigen, String codigoCiudadDestino) {
        return Retorno.noImplementada();
    }

    /////////////////////////////////////////////////////////////
    //FUNCIONES AUXILIARES//

    private boolean esValidoFormatoCedula(String cedula){
        if(cedula==null){
            return false;
        }
        String regex1 = "\\d|[1-9]\\.\\d{3}\\.\\d{3}-\\d";
        String regex2 = "\\d|[1-9]\\d{2}\\.\\d{3}-\\d";

        // Crea un patrón combinando las dos expresiones regulares con el operador OR (|)
        Pattern pattern = Pattern.compile(regex1 + "|" + regex2);

        // Compara la cédula con el patrón
        Matcher matcher = pattern.matcher(cedula);

        //System.out.println("*******************************************************************************");
        //System.out.println(cedula);
        //System.out.println(matcher.matches());
        // Devuelve true si la cédula coincide con uno de los formatos
        return matcher.matches();
    }

    private boolean esValidoFormatoCedulaDOS(String cedula){
        String regex1 = "^[1-9]\\d\\.\\d{3}\\.\\d{3}-\\d$";
        String regex2 = "^\\d{3}\\.\\d{3}-\\d$";
        System.out.println("*******************************************************************************");
        System.out.println(cedula);
        boolean res=cedula.matches(regex1) || cedula.matches(regex2);
        System.out.println(res);
        return res;
    }
}
