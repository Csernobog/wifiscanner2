package com.example.mano.wifiscanner;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class wifidata_adapter extends ArrayAdapter<wifidata> {
    private static class ViewHolder{
        TextView txtSSID;
        TextView txtBSSID;
        TextView txtFreq;
        TextView txtLevel;
        ProgressBar txtProg;
    }

    public wifidata_adapter(Context context, ArrayList<wifidata> wdata){
        super(context,R.layout.wifirow_layout, wdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        wifidata wd = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.wifirow_layout,parent,false);
            viewHolder.txtSSID = (TextView) convertView.findViewById(R.id.txSSID);
            viewHolder.txtBSSID = (TextView) convertView.findViewById(R.id.txMAC);
            viewHolder.txtFreq = (TextView) convertView.findViewById(R.id.txFREQ);
            viewHolder.txtLevel = (TextView) convertView.findViewById(R.id.txLEVEL);
            viewHolder.txtProg = (ProgressBar) convertView.findViewById(R.id.txProg);
            convertView.setTag(viewHolder);
        }else {
          viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtSSID.setText("SSID: "+wd.getwSSID());
        viewHolder.txtBSSID.setText("MAC: "+wd.getwBSSID());
        viewHolder.txtFreq.setText("FREQ: "+String.valueOf(wd.getWfreq())+" MHz");
        viewHolder.txtLevel.setText(String.valueOf(wd.getwLEVEL())+ " dBm");
        viewHolder.txtProg.setMax(100);
        viewHolder.txtProg.setProgress(100+wd.getwLEVEL());
        /*
        Excellent >-50 dBm  Zöld
        Good -50 to -60 dBm kék
        Fair -60 to -70 dBm sárga
        Weak < -70 dBm      piros
        */
        if (wd.getwLEVEL()> -50) viewHolder.txtProg.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        if (wd.getwLEVEL()<= -50 && wd.getwLEVEL()>-60) viewHolder.txtProg.setProgressTintList(ColorStateList.valueOf(Color.BLUE));

        if (wd.getwLEVEL()<= -60 && wd.getwLEVEL()>-70) viewHolder.txtProg.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));

        if (wd.getwLEVEL()<-70 ) viewHolder.txtProg.setProgressTintList(ColorStateList.valueOf(Color.RED));


        return convertView;
        }

    }