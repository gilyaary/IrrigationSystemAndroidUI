package com.example.gil.irrigationsystemandroidui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.webkit.JavascriptInterface;

import com.example.gil.irrigationsystemandroidui.http.HttpHelper;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class GilJavaScriptInterface {

    private Context con;
    private static final String PROTOCOL = "http://";
    private static final String HOST_IP = "192.168.1.12";
    private static final String SESSION_TOKEN = "ESPSESSIONID=1";
    private static final HttpHelper httpHelper = new HttpHelper();

    public GilJavaScriptInterface(Context con) {
        this.con = con;
    }


    @JavascriptInterface
    public String showToast(String mssg) {
        System.out.println("showToastCalled");
        return "Answer from Android Java";
    }

    @JavascriptInterface
    public String getZones() throws Exception {

        String zones = httpHelper.sendGet( PROTOCOL + HOST_IP + "/api/zones?" + SESSION_TOKEN);
        //String zones = "[{\"id\":\"0\",\"name\":\"Back yard, right side\"},{\"id\":\"1\",\"name\":\"Back yard, left side\"},{\"id\":\"2\",\"name\":\"Zone 3\"},{\"id\":\"3\",\"name\":\"Zone 4\"},{\"id\":\"4\",\"name\":\"Zone 5\"},{\"id\":\"5\",\"name\":\"Zone 6\"},{\"id\":\"6\",\"name\":\"Zone 7\"},{\"id\":\"7\",\"name\":\"Zone 8\"}]";
        return zones;
    }

    @JavascriptInterface
    public String getSchedules()throws Exception {
        //String schedules= "{\"programIds\":[\"1\",\"2\"],\"programs\":[{\"id\":\"1\",\"name\":\"Evening right side\",\"startTime\":\"19:07\",\"duration\":\"6\",\"days\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"0\"],\"outputs\":[\"0\"]},{\"id\":\"2\",\"name\":\"Evening left side\",\"startTime\":\"19:00\",\"duration\":\"6\",\"days\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"0\"],\"outputs\":[\"1\"]}]}";
        String schedules = httpHelper.sendGet( PROTOCOL + HOST_IP + "/api/programs?" + SESSION_TOKEN);
        return schedules;
    }

    @JavascriptInterface
    public String getSchedule(String scheduleId)throws Exception {
        //String schedules= "{\"programIds\":[\"1\",\"2\"],\"programs\":[{\"id\":\"1\",\"name\":\"Evening right side\",\"startTime\":\"19:07\",\"duration\":\"6\",\"days\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"0\"],\"outputs\":[\"0\"]},{\"id\":\"2\",\"name\":\"Evening left side\",\"startTime\":\"19:00\",\"duration\":\"6\",\"days\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"0\"],\"outputs\":[\"1\"]}]}";
        String schedule = httpHelper.sendGet( PROTOCOL + HOST_IP + "/api/programs/" + scheduleId + "?" + SESSION_TOKEN);
        return schedule;
    }
    @JavascriptInterface
    public String deleteSchedule(String scheduleId)throws Exception {
        String schedule = httpHelper.sendDelete( PROTOCOL + HOST_IP + "/api/programs/" + scheduleId + "?" + SESSION_TOKEN);
        return schedule;
    }

    @JavascriptInterface
    public String getStatus()throws Exception {
        String status = httpHelper.sendGet( PROTOCOL + HOST_IP + "/status?" + SESSION_TOKEN);
        return status;
    }

    @JavascriptInterface
    public String getTime()throws Exception {
        String status = httpHelper.sendGet( PROTOCOL + HOST_IP + "/time?" + SESSION_TOKEN);
        return status;
    }

    //api/programs/" + scheduleId;
    //Android.updateSchedule(scheduleId, JSON.toString($scope.schedule));
    @JavascriptInterface
    public void createSchedule(String scheduleJsonString)throws Exception {
        System.out.printf("Create Schedule, JSON: %s%n", scheduleJsonString);
        String status = httpHelper.sendPost( PROTOCOL + HOST_IP + "/api/programs/-1?" + SESSION_TOKEN, scheduleJsonString);
        //return status;
    }

    @JavascriptInterface
    public void updateSchedule(String scheduleId, String scheduleJsonString)throws Exception {
        System.out.printf("     ^^Update Schedule, ID: %s, JSON: %s%n", scheduleId, scheduleJsonString);
        String status = httpHelper.sendPut( PROTOCOL + HOST_IP + "/api/programs/" + scheduleId + "?" + SESSION_TOKEN, scheduleJsonString);
        //return status;
    }

    @JavascriptInterface
    public void updateZones(String zonesJsonString)throws Exception {
        System.out.printf("     ^^Update Zones, JSON: %s%n", zonesJsonString);
        String status = httpHelper.sendPut( PROTOCOL + HOST_IP + "/api/zones?" + SESSION_TOKEN, zonesJsonString);
        //return status;
    }
}
