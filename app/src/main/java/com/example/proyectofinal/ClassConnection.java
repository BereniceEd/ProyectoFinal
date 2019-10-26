package com.example.proyectofinal;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;

import com.android.volley.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassConnection extends AsyncTask<String, String, String> {
    Context con;

    @Override

    protected String doInBackground(String... strings) {


        HttpURLConnection httpURLConnection = null;
        URL url = null;

        try {
            url = new URL(strings[0]);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            //Crear la conexión para la url (para HTTPS usaríamos HttpsURLConnection)
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(10000);

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("User-Agent", "cliente Android 1.0");
            httpURLConnection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString(("joe" + ":" + "pino").getBytes(), Base64.NO_WRAP));
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();

            if (code == httpURLConnection.HTTP_OK) {

                InputStream inputStreamResponse = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamResponse, "UTF-8"));
                String linea = null;
                StringBuffer respuestaCadena = new StringBuffer();

                while ((linea = bufferedReader.readLine()) != null) {
                    respuestaCadena.append(linea);
                }
                if (inputStreamResponse != null) {
                    try {
                        inputStreamResponse.close();
                    } catch (IOException ex) {
                        Log.d(this.getClass().toString(), "Error cerrando InputStream", ex);
                    }
                }
                return respuestaCadena.toString();
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
