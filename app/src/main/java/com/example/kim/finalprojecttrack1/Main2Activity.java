package com.example.kim.finalprojecttrack1;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kim.finalprojecttrack1.Fragment.ChatFragment;
import com.example.kim.finalprojecttrack1.Fragment.PeopleFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    AlertDialog.Builder alert_ex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        PeopleFragment fragment = PeopleFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        passPushTokenToServer();
        transaction.commit();
    }

    void passPushTokenToServer(){

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Map<String, Object> map = new HashMap<>();
            String token = task.getResult().getToken();
            map.put("pushToken", token);
            FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);


        });

    }

    @Override
    public void onBackPressed() {
        alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니다.");

        alert_ex.setPositiveButton("취소", (dialogInterface, i) -> {

        });
        alert_ex.setNegativeButton("종료", (dialogInterface, i) -> {
            FirebaseAuth.getInstance().signOut();
            finishAffinity();
        });
        AlertDialog alert = alert_ex.create();
        alert.show();
    }
}
