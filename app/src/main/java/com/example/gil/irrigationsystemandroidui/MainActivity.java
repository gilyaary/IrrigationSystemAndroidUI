package com.example.gil.irrigationsystemandroidui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.gil.myapplication.R;

import java.util.List;

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

        //step 1: detect wifi networks for sprinkler system and display them in a list
        //step 2: ask user to select one system
        //Optional: If there is only one system then simply connect to it and bypass the above
        setWebView();
    }

    private void setWebView() {
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

        wv.loadUrl("file:///android_asset/all_wifi.html");
        //wv.loadUrl("file:///android_asset/all_schedules.html");

        //wv.loadUrl("file:///android_asset/test.html");
        //wv.loadUrl("file:///android_asset/login.html");
        //wv.loadUrl("file:///android_asset/create_schedule.html");
    }


}
