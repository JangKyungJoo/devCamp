package com.example.devcamp.alarm.specialAlarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class SpecialAlarmReceiver extends BroadcastReceiver {
    private NotificationManager nm = null;
    public SpecialAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        SpecialToast toast = new SpecialToast(context);
        toast.showToast("메인제목","서브제목", Toast.LENGTH_SHORT);

        nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

    }
}
