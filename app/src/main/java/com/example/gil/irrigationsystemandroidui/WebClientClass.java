package com.example.gil.irrigationsystemandroidui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebClientClass extends WebViewClient {
    ProgressDialog pd = null;
    private Context ctx;

    public WebClientClass(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        pd = new ProgressDialog(ctx);
        pd.setTitle("Please wait");
        pd.setMessage("Page is loading..");
        pd.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        pd.dismiss();
    }
}

