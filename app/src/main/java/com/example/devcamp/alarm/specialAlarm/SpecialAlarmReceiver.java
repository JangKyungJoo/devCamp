package com.example.devcamp.alarm.specialAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SpecialAlarmReceiver extends BroadcastReceiver {
    public SpecialAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        SpecialToast toast = new SpecialToast(context);
        toast.showToast("메인제목","서브제목", Toast.LENGTH_SHORT);

        throw new UnsupportedOperationException("Not yet implemented");




    }
}
