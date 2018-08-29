package com.example.kim.finalprojecttrack1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kim.finalprojecttrack1.Adapter.ViewPagerAdapter;
import com.example.kim.finalprojecttrack1.MainActivity;
import com.example.kim.finalprojecttrack1.MenuActivity;
import com.example.kim.finalprojecttrack1.R;
import com.example.kim.finalprojecttrack1.StartActivity;

import java.util.ArrayList;
import java.util.List;

import steelkiwi.com.library.view.IndicatorView;

public class Instruction extends AppCompatActivity {
    List<Integer> lstImages=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        lstImages.add(R.drawable.arrow);
        lstImages.add(R.drawable.arrow);
        lstImages.add(R.drawable.arrow);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(lstImages,this);
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        IndicatorView indicatorView=(IndicatorView)findViewById(R.id.indicatorView);
        indicatorView.attachViewPager(viewPager);




    }

    public void finish(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

        startActivity(intent);
        finish();
    }
}