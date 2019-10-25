package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
private BarChart EjemploG;
private View MostarM;
private String[] min = {};
private  int[] grados = {-4,-2,0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EjemploG = findViewById(R.id.EjemploG);
        MostarM = findViewById(R.id.mostrar);

        ClassConnection connection = new ClassConnection();

        try {
            StringBuffer respuesta= connection.execute("http://134.209.4.168:80/sensores/temperatura/1567306525/1571108125").get();
            Toast toast = Toast.makeText( this, respuesta, Toast.LENGTH_SHORT);
            toast.show();
            //JSONArray jsonArray = new JSONArray(respuesta);

            //meterlo en un while o for
            //JSONObject jsonObject = jsonArray.getJSONObject(0);
            //String ob = jsonObject.getString("temperatura");


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void menos(View view){
        EjemploG.setVisibility(View.GONE);
    }
    public void mostrar(View view){
        EjemploG.setVisibility(View.VISIBLE);
        EjemploG.setMinimumHeight(300);
       // EjemploG.setLayoutParams(Wid);

        //Valores de entrada
        List<BarEntry> lista = new ArrayList<>();
        lista.add(new BarEntry(0f, 2));
        lista.add(new BarEntry(1f,4));
        lista.add(new BarEntry(2f, 34));
        lista.add(new BarEntry(3f,5));
        lista.add(new BarEntry(4f, 35));
        lista.add(new BarEntry(5f,12));
        lista.add(new BarEntry(6f, 23));
        lista.add(new BarEntry(7f,1));

        //Envio de datos a la grafica
        BarDataSet datos = new BarDataSet(lista, "Gráfica de barras");
        BarData data = new BarData(datos);

        //Personalizacion de la grafica
        datos.setColors(ColorTemplate.COLORFUL_COLORS);
        //Separacion
        data.setBarWidth(0.9f);
        //Sele pasan los datos al objeto de tipo grafica barras
        EjemploG.setData(data);
        //Centra las barras
        EjemploG.setFitBars(true);

    }

    //class GetContacts extends AsyncTask<String,int ,List<Datos>> {
     //   ListView list;
      //  private ProgressDialog pDialog;
        //CONSTRUCTOR
       // public GetContacts(ListView listaa) {
          //  this.list=listaa;
        //}

        //@Override
        //protected void onPreExecute() {
          //  super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            //pDialog.setMessage("Getting Data ...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        //}

        //@Override
        //protected Void doInBackground(Void... arg0) {
            // CREAMOS LA INSTANCIA DE LA CLASE
          //  JSONParser sh = new JSONParser();
            //String jsonStr = sh.makeServiceCall(URL, JSONParser.GET);

            //if (jsonStr != null) {
              //  try {
                //    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                  //  pers = jsonObj.getJSONArray("Personajes");

                    // looping through All Equipos
                    //for (int i = 0; i < pers.length(); i++) {
                      //  JSONObject c = pers.getJSONObject(i);
                        //RECOJEMOS DATOS EN VARIABLES
                        //String equipo = c.getString("Equipo");
                        //String name = c.getString("name");
                        //String especialidad = c.getString("Especialidad");
                        //String imagen = c.getString("image");

                        //SUBITEM CON LAS HABILIDADES
                        //JSONObject habilidades = c.getJSONObject("Habilidades");
                        //String fuerza = habilidades.getString("Fuerza");
                        //String espiritu = habilidades.getString("Espiritu");
                        //String fortaleza = habilidades.getString("Fortaleza");

                        //CREAMOS OBJETO Y LO LLENAMOS
                        //Personajes e=new Personajes();
                        //e.setURLimagen(imagen);
                        //e.setNombre(name);
                        //e.setEquipo(equipo);
                        //e.setProfesion(especialidad);
                        //e.setEspiritu(espiritu);
                        //e.setFortaleza(fortaleza);
                        //e.setFuerza(fuerza);
                        // adding contact to contact list
                        //lista.add(e);
                    //}
                //} catch (JSONException e) {
                  //  e.printStackTrace();
                //}
            //} else {
              //  Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
            //}

            //return null;
        //}

        //@Override
        //protected void onPostExecute(Void result) {
          //  super.onPostExecute(result);
            // Dismiss the progress dialog
            //if (pDialog.isShowing()){
              //  pDialog.dismiss();
            //}
            //new CargarListTask().execute();
        //}
//HILO PARA CARGAR LOS DATOS EN EL LISTVIEW
        //class CargarListTask extends AsyncTask<void dapter="" tring="">{
        //@Override
        //protected void onPreExecute() {
          //  // TODO Auto-generated method stub
            //super.onPreExecute();
        //}
        //protected Adapter doInBackground(Void... arg0) {
          //  // TODO Auto-generated method stub
           // try{
            //}catch(Exception ex){
              //  ex.printStackTrace();
            //}

            //Adapter adaptador = new Adapter(a,lista);
            //return adaptador;
        //}
        //@Override
        //protected void onPostExecute(Adapter result) {
            // TODO Auto-generated method stub
          //  super.onPostExecute(result);
            //listaa.setAdapter(result);
        //}
 //}
   // }



}
