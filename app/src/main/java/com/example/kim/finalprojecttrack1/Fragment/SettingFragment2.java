package com.example.kim.finalprojecttrack1.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.kim.finalprojecttrack1.Data.SettingData;
import com.example.kim.finalprojecttrack1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingFragment2 extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_fragment2);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new SettingData(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(" 새 버전 업데이트");
        listDataHeader.add(" 이벤트");
        listDataHeader.add("상반기 우수 소방사");
        listDataHeader.add("하반기 우수 소방사");
        listDataHeader.add(" 소방 본부");


        // Adding child data
        List<String> new1 = new ArrayList<String>();
        new1.add("The Shawshank Redemption");
        new1.add("The Godfather");
        new1.add("The Godfather: Part II");
        new1.add("Pulp Fiction");
        new1.add("The Good, the Bad and the Ugly");
        new1.add("The Dark Knight");
        new1.add("12 Angry Men");

        List<String> event = new ArrayList<String>();
        event.add("The Conjuring");
        event.add("Despicable Me 2");
        event.add("Turbo");
        event.add("Grown Ups 2");
        event.add("Red 2");
        event.add("The Wolverine");

        List<String> up = new ArrayList<String>();
        up.add("2 Guns");
        up.add("The Smurfs 2");
        up.add("The Spectacular Now");
        up.add("The Canyons");
        up.add("Europa Report");

        List<String> down = new ArrayList<String>();
        down.add("2 Guns");
        down.add("The Smurfs 2");
        down.add("The Spectacular Now");
        down.add("The Canyons");
        down.add("Europa Report");

        List<String> headquarters = new ArrayList<>();
        headquarters.add("2 Guns");
        headquarters.add("The Smurfs 2");
        headquarters.add("The Spectacular Now");
        headquarters.add("The Canyons");
        headquarters.add("Europa Report");


        listDataChild.put(listDataHeader.get(0), new1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), event);
        listDataChild.put(listDataHeader.get(2), up);
        listDataChild.put(listDataHeader.get(3), down);
        listDataChild.put(listDataHeader.get(4), headquarters);
    }


}
