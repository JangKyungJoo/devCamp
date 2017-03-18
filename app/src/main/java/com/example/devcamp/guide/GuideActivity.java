package com.example.devcamp.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.devcamp.R;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GuideActivity extends AppCompatActivity{
    ImageView themeBtn, arrowBtn;
    FrameLayout gun, ji, complex, hard;
    boolean isExpanded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guide);

        isExpanded = true;
        themeBtn = (ImageView) findViewById(R.id.themeBtn);
        arrowBtn = (ImageView) findViewById(R.id.skinBtn);
        gun = (FrameLayout) findViewById(R.id.gun);
        ji = (FrameLayout) findViewById(R.id.ji);
        complex = (FrameLayout) findViewById(R.id.complex);
        hard = (FrameLayout) findViewById(R.id.hard);

        themeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "준비중입니다.." , Toast.LENGTH_SHORT).show();
            }
        });

        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpanded){
                    arrowBtn.setImageResource(R.mipmap.icon_setting_arrow_up);
                    gun.setVisibility(View.GONE);
                    ji.setVisibility(View.GONE);
                    complex.setVisibility(View.GONE);
                    hard.setVisibility(View.GONE);
                }else{
                    arrowBtn.setImageResource(R.mipmap.icon_setting_arrow_down);
                    gun.setVisibility(View.VISIBLE);
                    ji.setVisibility(View.VISIBLE);
                    complex.setVisibility(View.VISIBLE);
                    hard.setVisibility(View.VISIBLE);
                }
                isExpanded = !isExpanded;
            }
        });

        complex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuideDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}