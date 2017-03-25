package com.example.devcamp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.devcamp.alarm.specialAlarm.SpecialAlarmService;

/**
 * Created by jiyoung on 2017-03-14.
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean[] week_clicked = {true, true, true, true, true, true, false};
        Intent broadIntent = new Intent(this, SpecialAlarmService.class);
        broadIntent.putExtra("week_clicked", week_clicked);
        startService(broadIntent);


        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 1000); // 3초 후에 hd Handler 실행
    }

    private class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
            SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }
}
