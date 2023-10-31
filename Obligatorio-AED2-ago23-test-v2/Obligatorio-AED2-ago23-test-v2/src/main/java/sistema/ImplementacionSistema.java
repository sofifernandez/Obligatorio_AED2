package sistema;

import dominio.Ciudad;
import dominio.Conexion;
import dominio.Viajero;
import estructuras.arbol.ABBImp;
import estructuras.grafo.GrafoComplejo;
import interfaz.*;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplementacionSistema implements Sistema {

    private ABBImp arbolViajeros;
    private ABBImp arbolPremium;
    private ABBImp arbolEstandar;
    private ABBImp arbolCasual;
    private GrafoComplejo grafoCiudades;

    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        if(maxCiudades>5){
            arbolViajeros=new ABBImp();
            arbolPremium=new ABBImp();
            arbolEstandar=new ABBImp();
            arbolCasual=new ABBImp();
            grafoCiudades= new GrafoComplejo(maxCiudades, true);
            return Retorno.ok();
        }
        return Retorno.error1("El sistema debe tener más de 5 ciudades");
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
                if(lista.length()>0){
                    lista= lista.substring(0, lista.length()-1);
                }
                return Retorno.ok(lista);

            case 1:
                lista=arbolEstandar.listarAscendente();
                if(lista.length()>0){
                    lista= lista.substring(0, lista.length()-1);
                }
                return Retorno.ok(lista);

            case 2:
                lista= arbolCasual.listarAscendente();
                if(lista.length()>0){
                    lista= lista.substring(0, lista.length()-1);
                }
                return Retorno.ok(lista);
            default:
                System.out.println("El número no coincide con ningún caso.");
        }
        return Retorno.error1(lista);
    }


    //---------------------------GRAFOS------------------------------------------------------------------
    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        if(grafoCiudades.esLleno()) return Retorno.error1("Ha llegado al máximo de ciudades posibles");
        if(esVacioONulo(codigo) || esVacioONulo(nombre)) return Retorno.error2("Ingrese todos los datos");
        Ciudad nuevaCiudad=new Ciudad(codigo,nombre);
        if(!nuevaCiudad.esValidoCodigo(codigo)) return Retorno.error3("El código es inválido");
        if(grafoCiudades.existeVertice(nuevaCiudad)) return Retorno.error4("Ese código ya existe");
        grafoCiudades.agregarVertice(nuevaCiudad);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        if(costo<=0 || tiempo<=0) return Retorno.error1("Completar costo y tiempo de la conexión");
        if(esVacioONulo(codigoCiudadOrigen) || esVacioONulo(codigoCiudadDestino) || tipo==null) return Retorno.error2("Los campos son obligatorios");
        Ciudad origen = new Ciudad(codigoCiudadOrigen);
        Ciudad destino =new Ciudad(codigoCiudadDestino);
        if(!origen.esValidoCodigo(codigoCiudadOrigen) || !destino.esValidoCodigo(codigoCiudadDestino)) return Retorno.error3("Los códigos de la ciudades no tienen el formato adecuado");
        origen =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigoCiudadOrigen));
        destino =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigoCiudadDestino));
        if(origen==null) return Retorno.error4("La ciudad de origen no existe en el sistema");
        if(destino==null) return Retorno.error5("La ciudad de destino no existe en el sistema");
        //if(!origen.esValidoCodigo(codigoCiudadOrigen) || !destino.esValidoCodigo(codigoCiudadDestino)) return Retorno.error3("Los códigos de la ciudades no tienen el formato adecuado");
        Conexion nuevaConexion = new Conexion(identificadorConexion,costo, tiempo, tipo);
        if(grafoCiudades.existeDatoEnArista(origen,destino,nuevaConexion)) return Retorno.error6("Ya existe conexión con ese identificador");
        grafoCiudades.agregarArista(origen,destino,nuevaConexion);
        return Retorno.ok();
    }

    @Override
    public Retorno actualizarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        if(costo<=0 || tiempo<=0) return Retorno.error1("Completar costo y tiempo de la conexión");
        if(esVacioONulo(codigoCiudadOrigen) || esVacioONulo(codigoCiudadDestino) || tipo==null) return Retorno.error2("Los campos son obligatorios");
        Ciudad origen = new Ciudad(codigoCiudadOrigen);
        Ciudad destino =new Ciudad(codigoCiudadDestino);
        if(!origen.esValidoCodigo(codigoCiudadOrigen) || !destino.esValidoCodigo(codigoCiudadDestino)) return Retorno.error3("Los códigos de la ciudades no tienen el formato adecuado");
        origen =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigoCiudadOrigen));
        destino =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigoCiudadDestino));
        if(origen==null) return Retorno.error4("La ciudad de origen no existe en el sistema");
        if(destino==null) return Retorno.error5("La ciudad de destino no existe en el sistema");
        //if(!origen.esValidoCodigo(codigoCiudadOrigen) || !destino.esValidoCodigo(codigoCiudadDestino)) return Retorno.error3("Los códigos de la ciudades no tienen el formato adecuado");
        Conexion nuevaConexion = (Conexion) grafoCiudades.obtenerDatoEnArista(origen,destino,new Conexion(identificadorConexion,costo, tiempo, tipo));
        if(nuevaConexion==null) return Retorno.error6("No existe conexión con ese identificador");
        nuevaConexion.editar(costo, tiempo, tipo);
        //grafoCiudades.agregarArista(origen,destino,nuevaConexion);
        return Retorno.ok();
    }

    @Override
    public Retorno listadoCiudadesCantTrasbordos(String codigo, int cantidad) {
        if(cantidad < 0) return Retorno.error1("La cantidad de transbordos no puede ser menor que cero");
        if (esVacioONulo(codigo)) return Retorno.error2("Los campos son obligatorios");
        Ciudad ciudad = new Ciudad(codigo);
        if (!ciudad.esValidoCodigo(codigo)) return Retorno.error3("Codigo de la ciudad invalido");
        ciudad =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigo));
        if(ciudad==null) return Retorno.error4("La ciudad no existe en el sistema");
        return Retorno.ok(grafoCiudades.dfs(ciudad,cantidad).obtenerTodosComoString());
    }

    @Override
    public Retorno viajeCostoMinimo(String codigoCiudadOrigen, String codigoCiudadDestino) {
        if(esVacioONulo(codigoCiudadOrigen) || esVacioONulo(codigoCiudadDestino)) return Retorno.error1("Los campos son obligatorios");
        Ciudad origen = new Ciudad(codigoCiudadOrigen);
        Ciudad destino =new Ciudad(codigoCiudadDestino);
        if(!origen.esValidoCodigo(codigoCiudadOrigen) || !destino.esValidoCodigo(codigoCiudadDestino)) return Retorno.error2("Los códigos de la ciudades no tienen el formato adecuado");
        origen =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigoCiudadOrigen));
        destino =(Ciudad) grafoCiudades.obtenerVertice(new Ciudad(codigoCiudadDestino));
        if(origen==null) return Retorno.error4("La ciudad de origen no existe en el sistema");
        if(destino==null) return Retorno.error5("La ciudad de destino no existe en el sistema");
        if (!grafoCiudades.existeCamino(origen,destino)) return Retorno.error3("No existe ruta para conectar las ciudades");

        //Por lo que dan los test, el valorEntero lo devuelve bien, siempre coincide con el resultado del test
        //Lo que falta es el valorString, por eso los muestra con error.
        Function<Conexion, Double> costExtractor = conexion -> Double.valueOf(conexion.getTiempo());
        Object[] resultado = grafoCiudades.dijkstra(origen,destino,costExtractor);
        double resultadDouble= (double) resultado[0];
        int resultadoInt= (int) resultadDouble;
        String resultadoString= (String) resultado[1];
        //grafoCiudades.obtenerArista(origen,destino).imprimirDatos();

        return Retorno.ok(resultadoInt,resultadoString);

//        Object[] resultado = grafoCiudades.dijkstraConCaminoYCosto(origen, destino);
//        int resultadoInt = (int) resultado[0] ;
//        String resultadoString= (String) resultado[1];
//        return Retorno.ok(resultadoInt,resultadoString);
    }

    /////////////////////////////////////////////////////////////
    //FUNCIONES AUXILIARES//

    private boolean esVacioONulo(String texto){
        return texto==null || texto.isBlank() || texto.isEmpty();
    }

    private boolean esValidoFormatoCedula(String cedula){
        if(cedula==null){
            return false;
        }
        String regex1 = "\\d|[1-9]\\.\\d{3}\\.\\d{3}-\\d";
        String regex2 = "\\d|[1-9]\\d{2}\\.\\d{3}-\\d";

        // Crea un patrón combinando las dos expresiones regulares con el operador OR (|)
        Pattern pattern = Pattern.compile(regex1 + "|" + regex2);
        Matcher matcher = pattern.matcher(cedula);
        return matcher.matches();
    }



}
