package com.example.kim.finalprojecttrack1.Fragment;

import android.app.AlertDialog;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kim.finalprojecttrack1.MainActivity;
import com.example.kim.finalprojecttrack1.R;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.LOCATION_SERVICE;

public class SettingFragment extends Fragment {

    AlertDialog.Builder alert_ex;
    LocationManager locationManager;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        //locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        String[] menuItem = {"공지 사항", "메인화면", "119신고 앱 종료", "앱 사용 방법", "버전 정보 4.1. 0", "GPS 위치 확인", "약관 이용"};
        ListView listView = (ListView) view.findViewById(R.id.mainMenu);

        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItem
        );

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            if (position == 0) {
                Toast.makeText(getActivity(), "공지 사항", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SettingFragment2.class);
                startActivity(intent);
            } else if (position == 1) {
                Toast.makeText(getActivity(), "메인화면", Toast.LENGTH_SHORT).show();
                alert_ex = new AlertDialog.Builder(getActivity());
                alert_ex.setMessage("메인화면");
                alert_ex.setPositiveButton("확인", (dialogInterface, i) -> {

                });
                AlertDialog alert = alert_ex.create();
                alert.show();
            } else if (position == 2) {
                Toast.makeText(getActivity(), "119신고 앱 종료", Toast.LENGTH_SHORT).show();

            } else if (position == 3) {
                Toast.makeText(getActivity(), "앱 사용 방법", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Instruction.class);
                startActivity(intent);
            } else if (position == 4) {
                Toast.makeText(getActivity(), "버전 정보 4.1. 0", Toast.LENGTH_SHORT).show();
                alert_ex = new AlertDialog.Builder(getActivity());
                alert_ex.setMessage("버전 정보 4.1. 0");

                alert_ex.setPositiveButton("확인", (dialogInterface, i) -> {

                });
                AlertDialog alert = alert_ex.create();
                alert.show();
            } else if (position == 5) {
                Toast.makeText(getActivity(), "GPS 위치 확인", Toast.LENGTH_SHORT).show();

                if (!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
                    //GPS 설정화면으로 이동
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(getContext(), "이미켜져있습니다.", Toast.LENGTH_SHORT).show();
                }

            } else if (position == 6) {
                Toast.makeText(getActivity(), "약관 이용", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), provision.class);
                startActivity(intent);
            }


        });

        return view;

    }
}