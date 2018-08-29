package com.example.kim.finalprojecttrack1.Data;

import com.google.gson.annotations.SerializedName;

public class locationData {
    @SerializedName("instt_nm")
    private String instt_nm;//명칭
    @SerializedName("wgs84_l")
    private double wgs84_la;//위도
    @SerializedName("wgs84_lo")
    private double wgs84_lo;//경도

    public locationData(String instt_nm, double wgs84_la, double wgs84_lo) {
        this.instt_nm = instt_nm;
        this.wgs84_la = wgs84_la;
        this.wgs84_lo = wgs84_lo;
    }


    public void setInstt_nm(String instt_nm) {
        this.instt_nm = instt_nm;
    }

    public void setWgs84_la(double wgs84_la) {
        this.wgs84_la = wgs84_la;
    }

    public void setWgs84_lo(double wgs84_lo) {
        this.wgs84_lo = wgs84_lo;
    }

    public String getInstt_nm() {
        return instt_nm;
    }

    public double getWgs84_la() {
        return wgs84_la;
    }

    public double getWgs84_lo() {
        return wgs84_lo;
    }


}
