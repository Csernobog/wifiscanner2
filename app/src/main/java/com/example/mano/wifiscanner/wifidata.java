package com.example.mano.wifiscanner;

/**
 * Created by MANO on 2018.01.19..
 */

public class wifidata {
    private String wSSID;
    private String wBSSID;
    private int wFREQ;
    private int wLEVEL;

    public wifidata(String wSSID, String wBSSID, int wFREQ, int wLEVEL)
    {
        this.wSSID = wSSID;
        this.wBSSID = wBSSID;
        this.wFREQ = wFREQ;
        this.wLEVEL = wLEVEL;
    }
    public String getwSSID(){
        return wSSID;
    }

    public void setwSSID(String wSSID){
        this.wSSID = wSSID;
    }

    public  String getwBSSID(){
        return wBSSID;
    }

    public void setwBSSID(String wBSSID){
        this.wBSSID = wBSSID;
    }

    public  int getWfreq(){ return wFREQ;
    }

    public void setWfreq(int wfreq){
        this.wFREQ=wfreq;
    }

    public int getwLEVEL(){
        return wLEVEL;
    }

    public void setWlevel(int wlevel){
        this.wLEVEL=wlevel;
    }
}


