package com.example.mano.wifiscanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class wifi_scanner extends Activity implements View.OnClickListener{


    WifiManager wifi;
    ListView lv;
    Button buttonScan;
    int size = 0;
    List<ScanResult> results;

    String ITEM_KEY = "key";

    ArrayList<wifidata> wlist = new ArrayList();  //uj array definicio
    wifidata_adapter wifidataAdapter;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Widhwan Setup Wizard");

        setContentView(R.layout.activity_wifi_scanner);

        buttonScan = (Button) findViewById(R.id.scan);
        buttonScan.setOnClickListener(this);
        lv = (ListView)findViewById(R.id.wifilist);


        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false)
        {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }

        //ArrayList<wifidata> wlist = new ArrayList<wifidata>();  //uj array definicio

        this.wifidataAdapter = new wifidata_adapter(wifi_scanner.this, wlist); //uj adapter definicio

        ListView listView = (ListView) findViewById(R.id.wifilist);

        listView.setAdapter(this.wifidataAdapter);

        /*Log.d("WifScanner", "ADD");
        wlist.add(new wifidata("SSID","MAC",10,20));
        Log.d("WifScanner", "NOTIFY");
        wifidataAdapter.notifyDataSetChanged();
        Log.d("WifScanner", "EXIT");*/
        scanWifiNetworks();
        wifidataAdapter.notifyDataSetChanged();
    }

    public void onClick(View view)
    {
        scanWifiNetworks();
    }

    private void scanWifiNetworks(){

        wlist.clear();
        registerReceiver(wifi_receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifi.startScan();

        Log.d("WifScanner", "scanWifiNetworks");

        Toast.makeText(this, "Scanning....", Toast.LENGTH_SHORT).show();

    }

    BroadcastReceiver wifi_receiver;

    {
        wifi_receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context c, Intent intent) {
                Log.d("WifScanner", "onReceive");

                results = wifi.getScanResults();
                size = results.size();
                Log.d("WifiScanner", "size "+size);
                unregisterReceiver(this);

                try {
                    while (size >= 0) {
                        size--;
                        Log.d("WifScanner", "additem"+results.get(size).SSID);

                        wifidata newdata = new wifidata("","",0,0);

                        newdata.setwSSID(results.get(size).SSID);
                        newdata.setwBSSID(results.get(size).BSSID);
                        newdata.setWlevel(results.get(size).level);
                        newdata.setWfreq(results.get(size).frequency);

                        Log.d("WifScanner", newdata.getwSSID());

                        wlist.add(newdata);
                        // wifilista rendezese jelszint alapján - elöl a legjobb

                        Collections.sort(wlist, new Comparator<wifidata>(){
                            public int compare(wifidata obj1, wifidata obj2){
                                return (obj1.getwLEVEL() > obj2.getwLEVEL()) ? -1: (obj1.getwLEVEL() > obj2.getwLEVEL()) ? 1:0 ;
                            }
                        });

                        wifidataAdapter.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                    Log.w("WifScanner", "Exception: " + e);

                }



            }
        };
    }

}