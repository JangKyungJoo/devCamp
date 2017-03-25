package com.example.devcamp.alarm.specialAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.IBinder;

import static android.os.Build.VERSION.SDK;


public class SpecialAlarmService extends Service {
    AlarmManager alarmManager;
    boolean[] week_clicked = {};
    private static final long A_WEEK = 1000 * 60 * 60 * 24 * 7;

    public SpecialAlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // 다른 컴포넌트가 bindService()를 호출해서 서비스와 연결을 시도하면 이 메소드가 호출됩니다.
        // 이 메소드에서 IBinder를 반환해서 서비스와 컴포넌트가 통신하는데 사용하는 인터페이스를 제공해야 합니다.
        // 만약 시작 타입의 서비스를 구현한다면 null을 반환하면 됩니다.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 다른 컴포넌트가 startService()를 호출해서 서비스가 시작되면 이 메소드가 호출됩니다.
        // 만약 연결된 타입의 서비스를 구현한다면 이 메소드는 재정의 할 필요가 없습니다.
        week_clicked = intent.getBooleanArrayExtra("week_clicked");
        setAlarm(this, week_clicked);
        sendBroadcast(new Intent("arabiannight.tistory.com.sendreciver.gogogo"));

    }


    @Override
    public void onCreate() {
        // 서비스가 처음으로 생성되면 호출됩니다.
        // 이 메소드 안에서 초기의 설정 작업을 하면되고
        // 서비스가 이미 실행중이면 이 메소드는 호출되지 않습니다.
    }

    @Override
    public void onDestroy() {
        // 서비스가 소멸되는 도중에 이 메소드가 호출되며 주로 Thread, Listener, BroadcastReceiver와 같은 자원들을 정리하는데 사용하면 됩니다.
        // TaskKiller에 의해 서비스가 강제종료될 경우에는 이 메소드가 호출되지 않는다는 점 !! ㅜㅜ
    }

    // 알람 등록
    private void setAlarm(Context context, boolean[] week) {        // week : 요일 선택 boolean Array (일~토)

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        cancelAlarm();

        boolean[] week_repeat = {};

        int len = week.length;
        for (int i = 0; i < len; i++)
        {
            if (week[i])
            {
                week_repeat[i] = true;
                break;
            }
        }

        // 알람 등록
        Intent intent = new Intent(this, SpecialAlarmReceiver.class);

        long triggerTime = 0;
        long intervalTime = 24 * 60 * 60 * 1000;// 24시간
        if(week_repeat[0])
        {
            intent.putExtra("one_time", false);
            intent.putExtra("day_of_week", week);
            PendingIntent pending = getPendingIntent(intent);

            triggerTime = setTriggerTime();

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalTime, pending);
        }
        else
        {
            intent.putExtra("one_time", true);
            PendingIntent pending = getPendingIntent(intent);

            triggerTime = setTriggerTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
        }
    }


    private PendingIntent getPendingIntent(Intent intent)
    {
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }


    // SUNDAY : 1 ~ SATURDAY : 7
    private long setTriggerTime()
    {

        if()
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, 12);
        curTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        curTime.set(Calendar.MINUTE, 36);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

    private void cancelAlarm()
    {
        Intent intent = new Intent(this, SpecialAlarmReceiver.class);
        PendingIntent pending = getPendingIntent(intent);
        this.alarmManager.cancel(pending);
    }

}
