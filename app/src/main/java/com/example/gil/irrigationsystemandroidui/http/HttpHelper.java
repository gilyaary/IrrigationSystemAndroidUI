package com.example.gil.irrigationsystemandroidui.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {


    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        try {
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        //print result
        //System.out.println(response.toString());
        return response.toString();
    }

    // HTTP GET request
    public String sendDelete(String url) throws Exception {

        URL obj = new URL(url);
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        try {
            con.setRequestMethod("DELETE");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'DELETE' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        //print result
        //System.out.println(response.toString());
        return response.toString();
    }


    public String sendPut(String url, String data) throws Exception {
        return send(url, data, "PUT");
    }
    public String sendPost(String url, String data) throws Exception {
        return send(url, data, "POST");
    }
    // HTTP POST request
    public String send(String url, String data, String method) throws Exception {

        StringBuffer response = new StringBuffer();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        try {

            //add reuqest header
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending '" + method + "' request to URL : " + url);
            System.out.println("Post parameters : " + data);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return response.toString();
    }

}
