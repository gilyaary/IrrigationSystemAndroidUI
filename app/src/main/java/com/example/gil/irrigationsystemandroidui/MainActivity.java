package com.example.gil.irrigationsystemandroidui;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.gil.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private WebSettings settings;
    private WebView wv;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreateCalled");
        super.onCreate(savedInstanceState);
        //Context ctx = this.getApplicationContext();
        setContentView(R.layout.activity_main);

        wv = (WebView) findViewById(R.id.webview_1);
        wv.setClickable(true);
        settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setAllowFileAccessFromFileURLs(true); //Maybe you don't need this rule
        //settings.setAllowUniversalAccessFromFileURLs(true);
        

        WebClientClass webViewClient = new WebClientClass(MainActivity.this);
        wv.setWebViewClient(webViewClient);
        WebChromeClient webChromeClient = new WebChromeClient();
        wv.setWebChromeClient(webChromeClient);


        Object jsInterface = new GilJavaScriptInterface(this);
        wv.addJavascriptInterface(jsInterface, "Android");
        //wv.loadUrl("file:///android_asset/test.html");
        //wv.loadUrl("file:///android_asset/login.html");
        wv.loadUrl("file:///android_asset/all_schedules.html");
        //wv.loadUrl("file:///android_asset/create_schedule.html");
    }






}
