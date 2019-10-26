package com.example.proyectofinal;

public class Luminosidad {

    //Atributos de luminosidad
    private String fecha;
    private String luminosidad;

    public Luminosidad(String fecha, String luminosidad) {
        this.fecha = fecha;
        this.luminosidad = luminosidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }
}
