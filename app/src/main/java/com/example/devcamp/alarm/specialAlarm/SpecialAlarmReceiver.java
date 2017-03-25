package com.example.devcamp.alarm.specialAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SpecialAlarmReceiver extends BroadcastReceiver {
    public SpecialAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        if(intent.getAction().equals("com.example.devcamp.alarm.SpecialAlarmReceiver")){
            Intent serviceIntent = new Intent(context, SpecialAlarmService.class);
            context.startService(serviceIntent);
        }

        throw new UnsupportedOperationException("Not yet implemented");




    }
}
