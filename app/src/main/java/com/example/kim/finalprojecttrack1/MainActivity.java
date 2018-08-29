package com.example.kim.finalprojecttrack1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kim.finalprojecttrack1.Fragment.Instruction;
import com.example.kim.finalprojecttrack1.chat.MessageActivity;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    public static final String[] MANDATORY_PERMISSIONS = {
            "android.permission.CAMERA",
            "android.permission.CHANGE_NETWORK_STATE",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET",
            "android.permission.BLUETOOTH",
            "android.permission.BLUETOOTH_ADMIN",
            "android.permission.BROADCAST_STICKY",
            "android.permission.READ_PHONE_STATE",
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 24) {

            checkPermission(MANDATORY_PERMISSIONS);
        }//권한 주기
    }


    @TargetApi(24)
    private void checkPermission(String[] permissions) {
        requestPermissions(permissions, 100);
    }


    public void f1(View view) {
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("number", "1");
        startActivity(intent);
        finish();
    }

    public void f2(View view) {
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("number", "2");
        startActivity(intent);
        finish();
    }

    public void f3(View view) {
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("number", "3");
        startActivity(intent);
        finish();
    }

    public void f4(View view) {
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("number", "4");
        startActivity(intent);
        finish();
    }

    public void f5(View view) {
        show();
    }

    void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("긴급모드 입니다. 누르시겠습니까?");
        builder.setMessage("누르심?");
        builder.setPositiveButton("예",
                (dialog, which) -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        builder.setNegativeButton("아니오",
                (dialog, which) -> {
                    Toast.makeText(getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
                    return;
                });
        builder.show();
    }


}
