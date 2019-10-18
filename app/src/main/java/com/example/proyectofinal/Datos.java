package com.example.proyectofinal;

public class Datos {


    private double temperatura;
    private double luz;

    public Datos(double temperatura, double luz) {
        this.temperatura = temperatura;
        this.luz = luz;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getLuz() {
        return luz;
    }

    public void setLuz(double luz) {
        this.luz = luz;
    }
}
