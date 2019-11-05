package com.example.proyectofinal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ManejadorCadenas {

    //Formato de fechas
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //Este método jala todas las temperaturas en un rango
    ArrayList<Temperatura> getArregloTemperaturas(long fechaInicio, long fechaFin, boolean filtro, int numeroFiltro) throws ExecutionException, InterruptedException {
        //Clase del conector
        ClassConnection conector = new ClassConnection();
        //Creamos la URL (PARA TEMPERATURA)
        String URL = "";
        if(filtro){
            URL = "http://134.209.4.168:80/sensores/temperatura/" + fechaInicio
                    + "/" + fechaFin + "/" + numeroFiltro;
        }else{
            URL = "http://134.209.4.168:80/sensores/temperatura/" + fechaInicio
                    + "/" + fechaFin;
        }
        //Obtenemos el Json
        String jsonFeo = conector.execute(URL).get();
        //Quitamos extremos
        String cortada = jsonFeo.substring(2, jsonFeo.length() - 1);
        //Primer split
        String[] primerCorte = cortada.split(", \"");
        //ArrayList de objetos tipo temperatura
        ArrayList<Temperatura> datosTemperatura = new ArrayList<>();
        //Llenamos el arreglo
        for (String elemento : primerCorte) {
            //Segundo split individual
            String[] temporal = elemento.split("\":", 2);
            //A la fecha la pasamos por un conversor String - Long - String (fechador)
            datosTemperatura.add(new Temperatura(fechador(temporal[0]), temporal[1]));
        }

        return datosTemperatura;
    }

    ArrayList<Luminosidad> getArregloLuminosidad(long fechaInicio, long fechaFin, boolean filtro, int numeroFiltro) throws ExecutionException, InterruptedException {
        //Clase del conector
        ClassConnection conector = new ClassConnection();
        //Creamos la URL (PARA TEMPERATURA)
        String URL = "";
        if(filtro){
            URL = "http://134.209.4.168:80/sensores/luminosidad/" + fechaInicio
                    + "/" + fechaFin + "/" + numeroFiltro;
        }else{
            URL = "http://134.209.4.168:80/sensores/luminosidad/" + fechaInicio
                    + "/" + fechaFin;
        }
        //Obtenemos el Json
        String jsonFeo = conector.execute(URL).get();
        //Quitamos extremos
        String cortada = jsonFeo.substring(2, jsonFeo.length() - 1);
        //Primer split
        String[] primerCorte = cortada.split(", \"");
        //ArrayList de objetos tipo temperatura
        ArrayList<Luminosidad> datosLuminosidad = new ArrayList<>();
        //Llenamos el arreglo
        for (String elemento : primerCorte) {
            //Segundo split individual
            String[] temporal = elemento.split("\":", 2);
            //A la fecha la pasamos por un conversor String - Long - String (fechador)
            datosLuminosidad.add(new Luminosidad(fechador(temporal[0]), temporal[1]));
        }

        return datosLuminosidad;
    }

    String fechador(String fechaEpoch){
        //Esos tres ceros hacen que funcione ._.
        Date date = new Date(Long.parseLong(fechaEpoch + "000"));
        //Le damos formaro a nuestra fecha
        String dateString = formato.format(date);
        return dateString;
    }

}
