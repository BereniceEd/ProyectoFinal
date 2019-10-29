package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;


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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LineChart EjemploG, LuzG;
    private View MostarM;
    private VideoView video;

    private EditText txtTemperaturaInicio, txtTemperaturaFinal;
    private Button btnTemperatura;

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    final Calendar c = Calendar.getInstance();

    DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    ClassConnection connection = new ClassConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EjemploG = findViewById(R.id.EjemploG);
//        MostarM = findViewById(R.id.mostrar);
//        LuzG = findViewById(R.id.GLuz);
//        video = (VideoView) findViewById(R.id.video);

        txtTemperaturaInicio = findViewById(R.id.txtTemperaturaInicio);
        txtTemperaturaFinal = findViewById(R.id.txtTemperaturaFinal);
        btnTemperatura = findViewById(R.id.btnTemperatura);

        btnTemperatura.setOnClickListener(this);
//        txtTemperaturaInicio.setOnClickListener(this);
//        txtTemperaturaFinal.setOnClickListener(this);
        //String path = connection.execute("http://134.209.4.168:80/video/916/1304/1/30").get();
        // JSONObject jsonObject = new JSONObject(path);

        // video.setVideoURI(Uri.parse(path));

    }

    public void tempInicioClick(View view) {
        if (view.getId() == R.id.txtTemperaturaInicio) {

            datePicker("tempInicio");


//            Calendar c = Calendar.getInstance();
//            final EditText eto = (EditText) view;
//            DatePickerDialog mdiDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    String sf = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear, dayOfMonth);
//                    eto.setText(sf);
//                }
//            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
//            mdiDialog.show();
            Toast.makeText(this, "Click inicio", Toast.LENGTH_SHORT).show();
        }
    }

    public void tempFinalClick(View view) {
        if (view.getId() == R.id.txtTemperaturaFinal) {

            datePicker("tempFinal");
//            Calendar c = Calendar.getInstance();
//            final EditText eto = (EditText) view;
//            DatePickerDialog mdiDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    String sf = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear, dayOfMonth);
//                    eto.setText(sf);
//                }
//            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
//            mdiDialog.show();
            Toast.makeText(this, "Click final", Toast.LENGTH_SHORT).show();
        }
    }


    private void datePicker(final String campo) {
        //final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        timePicker(campo);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(final String campo) {
        //final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;

                    switch (campo){
                        case "tempInicio":
                            txtTemperaturaInicio.setText(date_time + " " + hourOfDay + ":" + minute);
                            break;
                        case "tempFinal":
                            txtTemperaturaFinal.setText(date_time + " " + hourOfDay + ":" + minute);
                            break;
                    }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTemperatura:
                if(txtTemperaturaInicio.getText().toString() != "" &&
                txtTemperaturaFinal.getText().toString() != ""){
                    long ini, fin;
                    Date dateIni = null, dateFin = null;
                    try {
                        dateIni = format.parse(txtTemperaturaInicio.getText().toString());
                        dateFin = format.parse(txtTemperaturaFinal.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(dateIni != null && dateFin != null){
                        ini = dateIni.getTime() / 1000;
                        fin = dateFin.getTime() / 1000;
                        Intent intent = new Intent(MainActivity.this, TemperaturaActivity.class);
                        intent.putExtra("fechaInicio", ini);
                        intent.putExtra("fechaFin", fin);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Algo salió mal :c", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
//            case R.id.txtTemperaturaInicio:
//                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.txtTemperaturaFinal:
//                Toast.makeText(this, "Final", Toast.LENGTH_SHORT).show();
//                break;
        }
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

    public void mostrarL(View view) {

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
