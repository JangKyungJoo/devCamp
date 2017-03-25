package com.example.devcamp.alarm.specialAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;


public class SpecialAlarmService extends Service {
    AlarmManager alarmManager;
    boolean[] week_clicked = {};
    private static final long A_WEEK = 1000 * 60 * 60 * 24 * 7;     // 7일
    long intervalTime = 24 * 60 * 60 * 1000;// 24시간

    long triggerTime = 0;

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

        return START_REDELIVER_INTENT;    //이전에 전달했던 Intent 가 그대로 전달된다.
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
//        cancelAlarm();

        // 알람 진행 요일 확인
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

        // 진행되는 알람 요일에 맞춰 알람 저장
        if(week_repeat[0])
        {
            PendingIntent pending = getPendingIntent();
            triggerTime = setTriggerTime();

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalTime, pending);
        }

    }

    // 알람의 설정 시각에 발생하는 pendingIntent 작성
    private PendingIntent getPendingIntent()
    {
        // 알람 등록
        Intent intent = new Intent(this, SpecialAlarmReceiver.class);

        // 일단 broadcastId는 0으로 진행
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }


    // SUNDAY : 1 ~ SATURDAY : 7
    private long setTriggerTime() {
        // current Time
        long atime = System.currentTimeMillis();

        // timepicker
        Calendar curTime = Calendar.getInstance();

        curTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);   // 요일
        curTime.set(Calendar.HOUR_OF_DAY, 16);                  // 시간
        curTime.set(Calendar.MINUTE, 45);                       // 분
        curTime.set(Calendar.SECOND, 0);                        // 초
        curTime.set(Calendar.MILLISECOND, 0);                   // 세부 초

        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;

        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;

    }

//    private void cancelAlarm()
//    {
//        Intent intent = new Intent(this, SpecialAlarmReceiver.class);
//        PendingIntent pending = getPendingIntent(intent);
//        this.alarmManager.cancel(pending);
//    }

}
