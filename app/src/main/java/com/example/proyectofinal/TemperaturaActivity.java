package com.example.proyectofinal;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TemperaturaActivity extends AppCompatActivity {

    private LineChart graficaTemperatura;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        graficaTemperatura = findViewById(R.id.graficaTemperatura);

        ManejadorCadenas manejadorCadenas = new ManejadorCadenas();

        long fechaInicio = getIntent().getExtras().getLong("fechaInicio");
        long fechaFin = getIntent().getExtras().getLong("fechaFin");;

        try {

            //Esta cosa jala los datos del servidor y los acomoda bien bonito
            //en una lista de objetos tipo Temperatura, necesita el rango de fechas
            //en tipo de dato long.
            ArrayList<Temperatura> valores
                    = manejadorCadenas.getArregloTemperaturas(fechaInicio, fechaFin, false, 0);
            //Lista de entradas
            double cantidad = valores.size();
            if(cantidad > 200.0){
                int filtro = valores.size() / 100;
                valores.clear();
                valores = manejadorCadenas.getArregloTemperaturas(fechaInicio, fechaFin, true, filtro);
            }
            List<Entry> lista = new ArrayList<>();
            //Crea entradas con el parámetro de temperatura de la lista de valores
            for (int i = 0; i < valores.size(); i++) {
                lista.add(new Entry((float) i, Float.parseFloat(valores.get(i).getTemperatura())));
            }
            //Crea el conjunto de datos con la lista y le da un nombre
            LineDataSet datos = new LineDataSet(lista, "Datos de temperatura");
            //Pone los datos
            LineData data = new LineData(datos);
            //Color
            datos.setColors(Color.parseColor("#328B2D"));
            //Para que sea una curva chida
            datos.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //Creo que es el borde
            graficaTemperatura.setBorderWidth(0.9f);
            //Se le pasan los datos al objeto de tipo gráfica
            graficaTemperatura.setData(data);
            //Encabezados de fecha
            final String[] encabezadosFechas = new String[valores.size()];
            //Llena la lista de encabezados con el parámetro de fecha de la lista de valores
            for (int i = 0; i < valores.size(); i++) {
                encabezadosFechas[i] = valores.get(i).getFecha();
            }
            //Pone los encabezados
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return encabezadosFechas[(int) value];
                }
            };
            XAxis xAxis = graficaTemperatura.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
