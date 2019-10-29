package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtTemperaturaInicio, txtTemperaturaFinal, txtLuminosidadInicio,
    txtLuminosidadFinal, txtVideoInicio, txtVideoFinal;
    private Button btnTemperatura, btnLuminosidad, btnVideo;

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

        txtVideoInicio = findViewById(R.id.txtVideoInicio);
        txtVideoFinal = findViewById(R.id.txtVideoFinal);
        btnVideo = findViewById(R.id.btnVideo);

        btnTemperatura.setOnClickListener(this);
        btnLuminosidad.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
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

    public void videoInicioClick(View view) {
        if (view.getId() == R.id.txtVideoInicio) {
            datePicker("videoInicio");
        }
    }

    public void videoFinalClick(View view) {
        if (view.getId() == R.id.txtVideoFinal) {
            datePicker("videoFinal");
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
                        case "videoInicio":
                            txtVideoInicio.setText(date_time + " " + hourOfDay + ":" + minute);
                            break;
                        case "videoFinal":
                            txtVideoFinal.setText(date_time + " " + hourOfDay + ":" + minute);
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
                        if(hayInternet()){
                            ini = dateIni.getTime() / 1000;
                            fin = dateFin.getTime() / 1000;
                            Intent intent = new Intent(MainActivity.this, TemperaturaActivity.class);
                            intent.putExtra("fechaInicio", ini);
                            intent.putExtra("fechaFin", fin);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "No hay Internet, F", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, "Se necesitan los dos campos", Toast.LENGTH_LONG).show();
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
                        if(hayInternet()){
                            ini = dateIni.getTime() / 1000;
                            fin = dateFin.getTime() / 1000;
                            Intent intent = new Intent(MainActivity.this, LuminosidadActivity.class);
                            intent.putExtra("fechaInicio", ini);
                            intent.putExtra("fechaFin", fin);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "No hay Internet, F", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, "Se necesitan los dos campos", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btnVideo:
                if(txtVideoInicio.getText().toString() != "" &&
                        txtVideoFinal.getText().toString() != ""){
                    long ini, fin;
                    Date dateIni = null, dateFin = null;
                    try {
                        dateIni = format.parse(txtVideoInicio.getText().toString());
                        dateFin = format.parse(txtVideoFinal.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(dateIni != null && dateFin != null){
                        if(hayInternet()){
                            ini = dateIni.getTime() / 1000;
                            fin = dateFin.getTime() / 1000;
                            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                            intent.putExtra("fechaInicio", ini);
                            intent.putExtra("fechaFin", fin);
                            Toast.makeText(this, "El vídeo puede tardar MINUTOS en generarse", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "No hay Internet, F", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, "Se necesitan los dos campos", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean hayInternet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com.mx");
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
