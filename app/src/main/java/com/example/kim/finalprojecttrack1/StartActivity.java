package com.example.kim.finalprojecttrack1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.kim.finalprojecttrack1.Fragment.Instruction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class StartActivity extends AppCompatActivity {

    FirebaseRemoteConfig mFirebaseRemoteConfig;
    FirebaseRemoteConfigSettings configSettings;
    Intent intent;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);

        boolean first = pref.getBoolean("isFirst", false);
        if(first==false){
            Log.d("Is first Time?", "first");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
            //앱 최초 실행시 하고 싶은 작업
            startActivity(new Intent(StartActivity.this, Instruction.class));

        }else{
            Log.d("Is first Time?", "not first");
        }

        출처: http://liveonthekeyboard.tistory.com/1 [키위남]

        intent = new Intent(this, MainActivity.class);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.default_config);
        mFirebaseRemoteConfig.fetch(600)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        mFirebaseRemoteConfig.activateFetched();
                    } else {
                        Toast.makeText(StartActivity.this, "Fetch Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                    displayWelcomeMessage();
                });
    }

    private void displayWelcomeMessage() {
        String message = mFirebaseRemoteConfig.getString("welcome_message");
        boolean caps = mFirebaseRemoteConfig.getBoolean("welcome_message_caps");

        if (caps) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message).setPositiveButton("확인", (dialogInterface, i) -> finish());
            builder.create().show();
        } else {
            startActivity(intent);
        }
    }

}
