package com.example.kim.finalprojecttrack1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kim.finalprojecttrack1.R;

public class provision extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provision);
    }

    public void back(View view) {
        finish();
    }
}
