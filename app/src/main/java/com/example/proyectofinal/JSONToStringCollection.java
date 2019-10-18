package com.example.proyectofinal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONToStringCollection {
    JSONObject object;

    public JSONToStringCollection(JSONObject object) {
        this.object = object;
    }

    /**
     * Analiza el objeto JSON y extrae la colección de datos.
     *
     * @return ArrayList
     * @throws JSONException
     */
    public ArrayList<String> getArrayList() throws JSONException {
        ArrayList<String> data = new ArrayList<String>();

        if (!object.equals(new JSONObject())) {
            // Del documento JSON extraemos el array "items", que contiene una
            // colección de publicaciones.
            JSONArray array = object.getJSONArray("items");

            // Recorremos el array para analizar todos los documentos que
            // contiene.
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                // Si miramos el documento JSON vemos que la URL de la foto es
                // el valor de la clave "m", que a su vez se encuentra dentro de
                // un documento JSON, cuya clave es "media". Por lo tanto
                // extraemos el documento que se encuentra dentro de "media", y
                // posteriormente el valor de "m".
                data.add(obj.getJSONObject("media").getString("m"));
            }
        }

        return data;
    }
}