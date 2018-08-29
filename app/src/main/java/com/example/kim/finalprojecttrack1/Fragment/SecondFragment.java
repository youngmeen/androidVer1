package com.example.kim.finalprojecttrack1.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kim.finalprojecttrack1.Data.MyItem1;
import com.example.kim.finalprojecttrack1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//약국 페이지
public class SecondFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView = null;
    private GoogleMap mMap;
    Geocoder geocoder;
    private Button button;
    private EditText editText;
    MarkerOptions mOptions2;
    //추가
    private ClusterManager<MyItem1> mClusterManager;// MyItem을 관리하는 ClusterManager

    public SecondFragment() {
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("test1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_second, container, false);

        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        editText = (EditText) layout.findViewById(R.id.editText);
        button = (Button) layout.findViewById(R.id.button);

        mapView.getMapAsync(this);


        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        geocoder = new Geocoder(getActivity());


        button.setOnClickListener(v -> {
            mMap.clear();
            String str = editText.getText().toString();
            List<Address> addressList = null;
            try {
                // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                addressList = geocoder.getFromLocationName(
                        str, // 주소
                        10); // 최대 검색 결과 개수
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(addressList.get(0).toString());
            // 콤마를 기준으로 split
            String[] splitStr = addressList.get(0).toString().split(",");
            String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
            System.out.println(address);

            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
            System.out.println(latitude);
            System.out.println(longitude);

            // 좌표(위도, 경도) 생성
            LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            // 마커 생성
            mOptions2 = new MarkerOptions();
            mOptions2.title("검색한곳");
            mOptions2.snippet(address);
            mOptions2.position(point);
            mOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            // 마커 추가
            mMap.addMarker(mOptions2);
            // 해당 좌표로 화면 줌
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
        });

        LatLng SEOUL = new LatLng(37.56, 126.97);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 12));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);


        JSONArray obj = null;
        String title = null;
        double x = 0;
        double y = 0;
        String telephone = null;


        try {
            obj = new JSONObject(loadJSONFromAsset()).getJSONArray("DATA");
        } catch (JSONException je) {
            je.printStackTrace();
        }

        mClusterManager = new ClusterManager<MyItem1>(getContext().getApplicationContext(), mMap);

        for (int i = 0; i < obj.length(); i++) {

            try {
                title = obj.getJSONObject(i).getString("nm");
                x = obj.getJSONObject(i).getDouble("xcode");
                y = obj.getJSONObject(i).getDouble("ycode");
                telephone = obj.getJSONObject(i).getString("tel");
            } catch (JSONException je) {
                je.printStackTrace();
            }
            MyItem1 offsetItem = new MyItem1(x, y, title, telephone);
            mClusterManager.addItem(offsetItem);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(offsetItem.getPosition()));
            mMap.setOnInfoWindowClickListener(marker -> {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + marker.getSnippet()));
                startActivity(intent);
            });
        }

        //추가
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
    }
}
