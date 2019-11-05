package com.example.proyectofinal;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    WebView visorWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        long fechaInicio = getIntent().getExtras().getLong("fechaInicio");
        long fechaFin = getIntent().getExtras().getLong("fechaFin");

        visorWeb = findViewById(R.id.visorWeb);
        WebSettings webSettings = visorWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);//916 y 1304
        visorWeb.loadUrl("http://joe:pino@134.209.4.168/video/" + fechaInicio + "/" + fechaFin + "/1/30");
        visorWeb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }
}
