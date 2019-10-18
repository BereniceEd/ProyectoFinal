package com.example.proyectofinal;

import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ConectorHttpJSON {
    private String url;

    public ConectorHttpJSON(String url) {
        this.url = url;
    }

    public JSONObject execute() throws ClientProtocolException, IOException,
            IllegalStateException, JSONException {

        /* Creamos el objeto cliente que realiza la petición al servidor */
        HttpClient cliente = new DefaultHttpClient();
        HttpHost httpHost = new HttpHost("localhost",80,"http");
        /* Definimos la ruta al servidor. */
        HttpPost post = new HttpPost(url);

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                // AuthScope can be configured more extensively to restrict
                // for which host/port/scheme/etc the credentials will be used.
                new AuthScope("somehost", AuthScope.ANY_PORT),
                new UsernamePasswordCredentials("joe", "pino"));

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("joe", "pino".toCharArray());
            }
        });

        //builder = HttpClientBuilder.create();

        /* Ejecuto la petición, y guardo la respuesta */
        HttpResponse respuesta = cliente.execute(post);

        // Recogemos el String que devuelve el servidor.
        String flickrFeed = inputStreamToString(respuesta.getEntity().getContent());

        // Hacemos un split para eliminar el comienzo de la cadena de texto,
        // para poder extraer únicamente el documento JSON. Aún contendrá un
        // paréntesis al final de la cadena.
        String jsonWithWrongEnd = flickrFeed.split("jsonFlickrFeed\\(")[1];

        // Recogemos el documento JSON a partir de jsonWithWrongEnd, eliminando
        // el último caracter.
        JSONObject object = new JSONObject(jsonWithWrongEnd.substring(0,
                jsonWithWrongEnd.length() - 1));

        return object;
    }

    private String inputStreamToString(InputStream is)
            throws UnsupportedEncodingException {
        String line = "";
        StringBuilder sb = new StringBuilder();
        // Guardamos la dirección en un buffer de lectura
        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                "utf-8"), 8);

        // Y la leemos toda hasta el final
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        } catch (Exception ex) {
            Log.w("Aviso", ex.toString());
        }

        // Devolvemos todo lo leido
        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}