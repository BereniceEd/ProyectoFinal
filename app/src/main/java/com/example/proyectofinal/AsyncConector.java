package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class AsyncConector extends AsyncTask<Void, Void, Void> {
    private ArrayList<String> data;
    private ArrayAdapter adapter;
    private String url;
    private ProgressDialog pd;
    private Context context;

    public AsyncConector(Context context, ArrayAdapter adapter,
                         String url) {
        this.adapter = adapter;
        this.url = url;
        pd = new ProgressDialog(context);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // Configuramos el ProgressDialog para mostrar mensaje de que se está
        // cargando el contenido.
        pd.setIndeterminate(true);
        pd.setMessage(context.getResources().getString(
                R.string.MessageProgressDialog));
        pd.setTitle(R.string.TitleProgressDialog);
        // Mostramos el ProgressDialog.
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Creamos un objeto de la clase que se encargará de conectar a la URL y
        // analizar su contenido. Esta clase la creamos en el siguiente paso.
        ConectorHttpJSON conector = new ConectorHttpJSON(url);

        try {
            // Recogemos el documento JSON de Internet.
            JSONObject obj = conector.execute();

            // Analizamos el documento JSON y recogemos todos los links a las
            // fotos.
            data = new JSONToStringCollection(obj).getArrayList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Añadimos todos los links al Adapter.
        for (String tmp : data)
            adapter.add(tmp);

        // Indicamos al Adapter que ha cambiado su contenido, para que actualice
        // a su vez los datos mostrados en el ListView.
        adapter.notifyDataSetChanged();

        // Eliminamos el ProgressDialog.
        pd.dismiss();
        super.onPostExecute(result);
    }
}