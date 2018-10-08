package com.example.lilach.irrigationcontrolapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;

/*
IrrigationControlApplication/app/src/main/AndroidManifest.xml:    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
IrrigationControlApplication/app/src/main/AndroidManifest.xml:    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        populateListWithNetworks(wifiManager);

        String networkSSID = "esp8266_00000222";
        String networkPass = "password";

        //first remove any existing config
        List<WifiConfiguration> prelist = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : prelist ) {
            if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.removeNetwork(i.networkId);
            }
        }
        //now create a new config
        WifiConfiguration conf = new WifiConfiguration();
        createConfig(wifiManager, networkSSID, networkPass, conf);
        enableNetwork(wifiManager, networkSSID);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.4.1/wifi.html"));
        startActivity(browserIntent);
    }

    private void populateListWithNetworks(WifiManager wifiManager) {
        List<ScanResult> apList = wifiManager.getScanResults();
        String[] values = new String[apList.size()];
        for(int i=0; i<apList.size(); i++){
            values[i] = apList.get(i).SSID;
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    private void enableNetwork(WifiManager wifiManager, String networkSSID) {
        List<WifiConfiguration> postlist = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : postlist ) {
            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.disconnect();
                Toast.makeText(getApplicationContext(),"Connecting to network: " + i.networkId, Toast.LENGTH_LONG).show();
                boolean connected = wifiManager.enableNetwork(i.networkId, true);
                wifiManager.saveConfiguration();
                Toast.makeText(getApplicationContext(),"Connected: " + connected, Toast.LENGTH_LONG);
                break;
            }
        }
    }

    private void createConfig(WifiManager wifiManager, String networkSSID, String networkPass, WifiConfiguration conf) {
        conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain ssid in quotes
        //Then, for WEP network you need to do this:
        conf.preSharedKey =  "\"" + networkPass + "\"";
        conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        conf.status = WifiConfiguration.Status.ENABLED;
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        conf.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.TKIP);
        conf.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.CCMP);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        //conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE); //for open network
        wifiManager.addNetwork(conf);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onButtonClicked(View view) {
        //R.id.listViewWifi

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        String keys = "";
        for( WifiConfiguration i : list ) {
            keys += "\n" + i.SSID + "->" + i.preSharedKey + ",";
        }
        Toast.makeText(getApplicationContext(),"Keys: " + keys, Toast.LENGTH_LONG).show();


    }
}
