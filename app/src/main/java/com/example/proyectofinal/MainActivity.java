package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private LineChart EjemploG, LuzG;
    private View MostarM;
    private VideoView video;

ClassConnection connection  =new ClassConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EjemploG = findViewById(R.id.EjemploG);
        MostarM = findViewById(R.id.mostrar);
        LuzG = findViewById(R.id.GLuz);
        video=(VideoView) findViewById(R.id.video);

            //String path = connection.execute("http://134.209.4.168:80/video/916/1304/1/30").get();
           // JSONObject jsonObject = new JSONObject(path);

           // video.setVideoURI(Uri.parse(path));

    }

    public void menos(View view) {
        EjemploG.setVisibility(View.GONE);
    }
    public void menosL(View view) {
        LuzG.setVisibility(View.GONE);
    }

    public void mostrar(View view) {

        EjemploG.setVisibility(View.VISIBLE);
        EjemploG.setMinimumHeight(300);

        ManejadorCadenas manejadorCadenas = new ManejadorCadenas();

        //Esto se debe jalar desde un calendario
        long fechaInicio = 1569518417L;
        long fechaFin = 1569952981L;

        //Un trycatch gigante alv :v
        try {

            //Esta cosa jala los datos del servidor y los acomoda bien bonito
            //en una lista de objetos tipo Temperatura, necesita el rango de fechas
            //en tipo de dato long.
            ArrayList<Temperatura> valores
                    = manejadorCadenas.getArregloTemperaturas(fechaInicio, fechaFin);
            //Lista de entradas
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
            EjemploG.setBorderWidth(0.9f);
            //Se le pasan los datos al objeto de tipo gráfica
            EjemploG.setData(data);
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
            XAxis xAxis = EjemploG.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void mostrarL(View view){

        LuzG.setVisibility(View.VISIBLE);
        LuzG.setMinimumHeight(300);

        ManejadorCadenas manejadorCadenas = new ManejadorCadenas();

        //Esto se debe jalar desde un calendario
        long fechaInicio = 1571370539L;
        long fechaFin = 1571716139L;

        //Un trycatch gigante alv :v
        try {

            //Esta cosa jala los datos del servidor y los acomoda bien bonito
            //en una lista de objetos tipo Temperatura, necesita el rango de fechas
            //en tipo de dato long.
            ArrayList<Luminosidad> valores
                    = manejadorCadenas.getArregloLuminosidad(fechaInicio, fechaFin);
            //Lista de entradas
            List<Entry> lista = new ArrayList<>();
            //Crea entradas con el parámetro de temperatura de la lista de valores
            for (int i = 0; i < valores.size(); i++) {
                lista.add(new Entry((float) i, Float.parseFloat(valores.get(i).getLuminosidad())));
            }
            //Crea el conjunto de datos con la lista y le da un nombre
            LineDataSet datos = new LineDataSet(lista, "Datos de Luminosidad");
            //Pone los datos
            LineData data = new LineData(datos);
            //Color
            datos.setColors(Color.parseColor("#328B2D"));
            //Para que sea una curva chida
            datos.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //Creo que es el borde
            LuzG.setBorderWidth(0.9f);
            //Se le pasan los datos al objeto de tipo gráfica
            LuzG.setData(data);
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
            XAxis xAxis = LuzG.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
