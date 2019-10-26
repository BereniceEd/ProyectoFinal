package com.example.proyectofinal;

public class Temperatura {

    //Atributos de temperatura
    private String fecha;
    private String temperatura;

    public Temperatura(String fecha, String temperatura) {
        this.fecha = fecha;
        this.temperatura = temperatura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}
