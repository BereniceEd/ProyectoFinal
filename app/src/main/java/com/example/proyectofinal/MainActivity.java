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

    private EditText txtTemperaturaInicio, txtTemperaturaFinal, txtLuminosidadInicio,
    txtLuminosidadFinal;
    private Button btnTemperatura, btnLuminosidad;

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

        txtTemperaturaInicio = findViewById(R.id.txtTemperaturaInicio);
        txtTemperaturaFinal = findViewById(R.id.txtTemperaturaFinal);
        btnTemperatura = findViewById(R.id.btnTemperatura);

        txtLuminosidadInicio = findViewById(R.id.txtLuminosidadInicio);
        txtLuminosidadFinal = findViewById(R.id.txtLuminosidadFinal);
        btnLuminosidad = findViewById(R.id.btnLuminosidad);

        btnTemperatura.setOnClickListener(this);
        btnLuminosidad.setOnClickListener(this);
    }

    public void tempInicioClick(View view) {
        if (view.getId() == R.id.txtTemperaturaInicio) {
            datePicker("tempInicio");
        }
    }

    public void tempFinalClick(View view) {
        if (view.getId() == R.id.txtTemperaturaFinal) {
            datePicker("tempFinal");
        }
    }

    public void lumInicioClick(View view) {
        if (view.getId() == R.id.txtLuminosidadInicio) {
            datePicker("lumInicio");
        }
    }

    public void lumFinalClick(View view) {
        if (view.getId() == R.id.txtLuminosidadFinal) {
            datePicker("lumFinal");
        }
    }

    private void datePicker(final String campo) {
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
                        case "lumInicio":
                            txtLuminosidadInicio.setText(date_time + " " + hourOfDay + ":" + minute);
                            break;
                        case "lumFinal":
                            txtLuminosidadFinal.setText(date_time + " " + hourOfDay + ":" + minute);
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
            case R.id.btnLuminosidad:
                if(txtLuminosidadInicio.getText().toString() != "" &&
                        txtLuminosidadFinal.getText().toString() != ""){
                    long ini, fin;
                    Date dateIni = null, dateFin = null;
                    try {
                        dateIni = format.parse(txtLuminosidadInicio.getText().toString());
                        dateFin = format.parse(txtLuminosidadFinal.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(dateIni != null && dateFin != null){
                        ini = dateIni.getTime() / 1000;
                        fin = dateFin.getTime() / 1000;
                        Intent intent = new Intent(MainActivity.this, LuminosidadActivity.class);
                        intent.putExtra("fechaInicio", ini);
                        intent.putExtra("fechaFin", fin);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Algo salió mal :c", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }



}
