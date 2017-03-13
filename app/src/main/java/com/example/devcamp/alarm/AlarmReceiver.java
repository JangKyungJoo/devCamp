package com.example.devcamp.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.devcamp.MainActivity;
import com.example.devcamp.R;

/**
 * Created by jiyoung on 2016-11-10.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm!!!", Toast.LENGTH_SHORT).show();
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        String uri = intent.getStringExtra("url");
        String memo = intent.getStringExtra("memo");
        Uri alarmSound;
//        Toast.makeText(context, uri, Toast.LENGTH_SHORT).show();
        Log.d("url" ,"url : "+ uri);
        Log.d("memo" ,"memo : "+ uri);
        if(uri == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }else{
            alarmSound = Uri.parse(uri);
        }
        //추가 점수1 - 커스텀 notification 사용
//        RemoteViews customView  = new RemoteViews("com.example.devcamp.alarm", R.layout.customnotiview);
        Notification noti = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("씻고눕자")
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
//                .setContent(customView)
                .setContentText(memo)
                .build();

        notificationmanager.notify(1, noti);
    }
}