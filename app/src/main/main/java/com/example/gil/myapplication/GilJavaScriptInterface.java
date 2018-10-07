package com.example.gil.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;


public class GilJavaScriptInterface {
    private Context con;

    public GilJavaScriptInterface(Context con) {
        this.con = con;
    }


    @JavascriptInterface
    public String showToast(String mssg) {
        System.out.println("showToastCalled");
        return "Answer from Android Java";
    }
}
