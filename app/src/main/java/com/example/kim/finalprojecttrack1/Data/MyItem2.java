package com.example.kim.finalprojecttrack1.Data;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem2 implements ClusterItem{
    private final LatLng mPosition;// 포지션을 만들 고 리턴으로 넘겨준다
    private final String title;
    private final String telephone;


    public MyItem2(double lat, double lng, String title, String telephone) {// 위도 경도 던져준다
        mPosition = new LatLng(lat, lng);
        this.title = title;
        this.telephone = telephone;

    }
    @Override
    public LatLng getPosition() {
        // 위도 경도 개체를 돌려 주면 된다
        return mPosition;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return telephone;
    }
}
