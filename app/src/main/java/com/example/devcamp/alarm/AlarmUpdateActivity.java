package com.example.devcamp.alarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.devcamp.R;
import com.example.devcamp.util.Alarm;

import java.util.Calendar;

public class AlarmUpdateActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 5000;
    AlarmManager alarmManager;
    TimePicker mTime;
    int hour, minute; //timepicker로 받아온 시,분을 저장
    Uri uri;

    SharedPreferences alarmDay;
    SharedPreferences.Editor editor;

    TextView tvSunday;
    TextView tvMonday;
    TextView tvTuesday;
    TextView tvWednesday;
    TextView tvThursday;
    TextView tvFriday;
    TextView tvSaturday;

    ImageButton alarmWayBtn;
    TextView alarmWay;

    SeekBar alarmVol;

    EditText alarmMemo;
    Button btnAlarm;

    Calendar calSet;
    boolean monday = false;
    boolean tuesday = false;
    boolean wednesday = false;
    boolean thursday = false;
    boolean friday = false;
    boolean saturday = false;
    boolean sunday = false;
    TextView textNoti;

    String ringtoneString;

    AlarmDBHelper alarmDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        Intent intent = getIntent();

        intent = getIntent();
        
        textNoti = (TextView)findViewById(R.id.notiAlarm);

        if(intent.getStringExtra("alarm").equals("sleep")){
            textNoti.setText("취침 전 알림");
        }else{
            textNoti.setText("세안 전 알림");
        }

        Alarm data;

        alarmDBHelper = new AlarmDBHelper(this);
        tvSunday = (TextView)findViewById(R.id.txSunday);
        tvMonday = (TextView)findViewById(R.id.txMonday);
        tvTuesday = (TextView)findViewById(R.id.txTuesday);
        tvWednesday = (TextView)findViewById(R.id.txWednesday);
        tvThursday = (TextView)findViewById(R.id.txThursday);
        tvFriday = (TextView)findViewById(R.id.txFriday);
        tvSaturday = (TextView)findViewById(R.id.txSaturday);

        btnAlarm = (Button)findViewById(R.id.btnAlarm);
        alarmWayBtn = (ImageButton)findViewById(R.id.alarmWayButton);
        alarmWay = (TextView)findViewById(R.id.alarmWay);
        alarmVol = (SeekBar)findViewById(R.id.alarmVolume);

        alarmMemo = (EditText)findViewById(R.id.alarmMemo);

        alarmDay = getSharedPreferences("day", MODE_PRIVATE);
        editor = alarmDay.edit();

        editor.putString("sunday", "false");
        editor.commit();

        editor.putString("monday", "false");
        editor.commit();

        editor.putString("tuesday", "false");
        editor.commit();

        editor.putString("wednesday", "false");
        editor.commit();

        editor.putString("thursday", "false");
        editor.commit();

        editor.putString("friday", "false");
        editor.commit();

        editor.putString("saturday", "false");
        editor.commit();


        mTime = (TimePicker)findViewById(R.id.timepicker);
        mTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int m) {
                //값이 바뀔때 hour, minute에 바뀐 시,분을 저장
                hour = h;
                minute = m;
            }
        });

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        if(getIntent().getSerializableExtra("data") != null){
            data = (Alarm) getIntent().getSerializableExtra("data");
            btnAlarm.setText("알람 수정하기");

            alarmWay.setText(data.getRingtone());

            alarmMemo.setText(data.getMemo());

            if(data.getTime().contains("오후"))
                mTime.setHour(data.getTime().indexOf(2,4)+12);
            else
                mTime.setHour(data.getTime().indexOf(2,4));
            mTime.setMinute(data.getTime().indexOf(4,6));

            if(data.isMonday()){
                tvMonday.setTextColor(Color.parseColor("#B5A5F2"));
            }
            if(data.isTuesday()){
                tvTuesday.setTextColor(Color.parseColor("#B5A5F2"));
            }
            if(data.isWednesday()){
                tvWednesday.setTextColor(Color.parseColor("#B5A5F2"));
            }
            if(data.isThursday()){
                tvThursday.setTextColor(Color.parseColor("#B5A5F2"));
            }
            if(data.isFriday()){
                tvFriday.setTextColor(Color.parseColor("#B5A5F2"));
            }
            if(data.isSaturday()){
                tvSaturday.setTextColor(Color.parseColor("#B5A5F2"));
            }
            if(data.isSunday()){
                tvSunday.setTextColor(Color.parseColor("#B5A5F2"));
            }
        }

        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nCurrentVolumn = audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);

        alarmVol.setMax(nMax);
        alarmVol.setProgress(nCurrentVolumn);

        alarmVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = alarmDBHelper.getWritableDatabase();
                ContentValues row = new ContentValues();


                if (alarmDay.getString("monday", "").equals("true")) {
                    monday = true;
                    forday(2);
                } else if (alarmDay.getString("tuesday", "").equals("true")) {
                    tuesday = true;
                    forday(3);
                } else if (alarmDay.getString("wednesday", "").equals("true")) {
                    wednesday = true;
                    forday(4);
                } else if (alarmDay.getString("thursday", "").equals("true")) {
                    thursday = true;
                    forday(5);
                } else if (alarmDay.getString("friday", "").equals("true")) {
                    friday = true;
                    forday(6);
                } else if (alarmDay.getString("saturday", "").equals("true")) {
                    saturday = true;
                    forday(7);
                } else if (alarmDay.getString("sunday", "").equals("true")) {
                    sunday = true;
                    forday(1);
                }



                //                1. DB 관련 메소드를 사용하여 데이터 추가

                String time;
                if(hour >= 12)
                    time = "오후"+(hour-12)+":"+minute;
                else
                    time = "오전"+hour+":"+minute;
                row.put("time", time );
                row.put("sunday", (sunday)? 1:0);
                row.put("monday", (monday)? 1 : 0);
                row.put("tuesday", (tuesday)? 1 : 0);
                row.put("wednesday", (wednesday)? 1:0);
                row.put("thursday", (thursday)? 1:0);
                row.put("friday", (friday)? 1:0);
                row.put("saturday", (saturday)? 1:0);

                row.put("cancel", 0);
                if(alarmMemo.getText() != null)
                    row.put("memo", alarmMemo.getText().toString());
                else
                    row.put("memo", "씻고 왔나요?");
                row.put("ringtone", ringtoneString);

                db.insert(AlarmDBHelper.TABLE_NAME, null, row);
//                db.execSQL("insert into " + AlarmDBHelper.TABLE_NAME + " values (null, '"+time+"', '"+ monday+"', '"+ tuesday+"','"+wednesday
//                        +"');");
                // 2. SQL을 사용하여 데이터추가
//                db.execSQL("insert into " + myDBHelper.TABLE_NAME + " values ( null, '"
//                        + title + "', '" + phone + "');");

                alarmDBHelper.close();

                Intent insertIntent = new Intent(AlarmUpdateActivity.this, AlarmListActivity.class);
                startActivity(insertIntent);
                finish();
//                break;
            }
        });
    }




    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setAction("com.example.devcamp.alarm.AlarmReceiver"); //리시버 등록
        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(this, 100, intent, 0);

        alarmDay = getSharedPreferences("day", MODE_PRIVATE);
        editor = alarmDay.edit();

        switch(v.getId()) {
//            case R.id.btnAlarm:

////                alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);  //알람 등록(일회성)
//
//                //추가점수2 - 반복적 알람 설정
//                alarmManager.setRepeating(AlarmManager.RTC, cal.getTimeInMillis(), 300000, pendingIntent); //반복적 알람1, 5분마다 알람 반복
////                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 300000, pendingIntent); //반복적 알람2, 앱이 꺼진 상태에서도 장비를 깨운다.

//                SQLiteDatabase db = alarmDBHelper.getWritableDatabase();
//                ContentValues row = new ContentValues();
//                row.put("time", calSet.getTimeInMillis() );
//
//                if (alarmDay.getString("monday", "").equals("true")) {
//                    monday = true;
//                    forday(2);
//                } else if (alarmDay.getString("tuesday", "").equals("true")) {
//                    tuesday = true;
//                    forday(3);
//                } else if (alarmDay.getString("wednesday", "").equals("true")) {
//                    wednesday = true;
//                    forday(4);
//                } else if (alarmDay.getString("thursday", "").equals("true")) {
//                    thursday = true;
//                    forday(5);
//                } else if (alarmDay.getString("friday", "").equals("true")) {
//                    friday = true;
//                    forday(6);
//                } else if (alarmDay.getString("saturday", "").equals("true")) {
//                    saturday = true;
//                    forday(7);
//                } else if (alarmDay.getString("sunday", "").equals("true")) {
//                    sunday = true;
//                    forday(1);
//                }
//
//
//
//                //                1. DB 관련 메소드를 사용하여 데이터 추가
//
//                row.put("sunday", (sunday)? 1:0);
//                row.put("monday", (monday)? 1 : 0);
//                row.put("tuesday", (tuesday)? 1 : 0);
//                row.put("wednesday", (wednesday)? 1:0);
//                row.put("thursday", (thursday)? 1:0);
//                row.put("friday", (friday)? 1:0);
//                row.put("saturday", (saturday)? 1:0);
//
//                row.put("cancel", 0);
//                row.put("memo", alarmMemo.getText().toString());
//                row.put("ringtone", ringtoneString);
//
//                db.insert(AlarmDBHelper.TABLE_NAME, null, row);
////                db.execSQL("insert into " + AlarmDBHelper.TABLE_NAME + " values (null, '"+time+"', '"+ monday+"', '"+ tuesday+"','"+wednesday
////                        +"');");
//                // 2. SQL을 사용하여 데이터추가
////                db.execSQL("insert into " + myDBHelper.TABLE_NAME + " values ( null, '"
////                        + title + "', '" + phone + "');");
//
//                alarmDBHelper.close();
//
//                Intent insertIntent = new Intent(this, AlarmListActivity.class);
//                startActivity(insertIntent);
//
//                break;
//            case R.id.btnCancel:
//                alarmManager.cancel(pendingIntent); //알람을 끈다
//                break;
            case R.id.txSunday:
                Toast.makeText(this, "sunday", Toast.LENGTH_SHORT).show();
                if(alarmDay.getString("sunday", "").equals("false")) {
                    editor.putString("sunday", "true");
                    editor.commit();
                    tvSunday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("sunday", "false");
                    editor.commit();

                    tvSunday.setTextColor(Color.GRAY);
                }
                break;
            case R.id.txMonday:
                if(alarmDay.getString("monday", "").equals("false")) {
                    editor.putString("monday", "true");
                    editor.commit();
                    tvMonday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("monday", "false");
                    editor.commit();
                    tvMonday.setTextColor(Color.GRAY);
                }
                break;
            case R.id.txTuesday:
                if(alarmDay.getString("tuesday", "").equals("false")) {
                    editor.putString("tuesday", "true");
                    editor.commit();
                    tvTuesday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("tuesday", "false");
                    editor.commit();
                    tvTuesday.setTextColor(Color.GRAY);
                }
                break;
            case R.id.txWednesday:
                if(alarmDay.getString("wednesday", "").equals("false")) {
                    editor.putString("wednesday", "true");
                    editor.commit();
                    tvWednesday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("wednesday", "false");
                    editor.commit();
                    tvWednesday.setTextColor(Color.GRAY);
                }

                break;
            case R.id.txThursday:
                if(alarmDay.getString("thursday", "").equals("false")) {
                    editor.putString("thursday", "true");
                    editor.commit();
                    tvThursday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("thursday", "false");
                    editor.commit();
                    tvThursday.setTextColor(Color.GRAY);
                }

                break;
            case R.id.txFriday:
                if(alarmDay.getString("friday", "").equals("false")) {
                    editor.putString("friday", "true");
                    editor.commit();
                    tvFriday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("friday", "false");
                    tvFriday.setTextColor(Color.GRAY);
                }
                break;
            case R.id.txSaturday:
                if(alarmDay.getString("saturday", "").equals("false")) {
                    editor.putString("saturday", "true");
                    tvSaturday.setTextColor(Color.parseColor("#B5A5F2"));
                }
                else {
                    editor.putString("saturday", "false");
                    editor.commit();
                    tvSaturday.setTextColor(Color.GRAY);
                }
                break;
            case R.id.alarmWayButton:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.System.canWrite(this)) {
                        Toast.makeText(this, "onCreate: Already Granted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                        startActivityForResult(i, 0);
                    } else {
                        Toast.makeText(this, "onCreate: Not Granted. Permission Requested", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                        intent.setData(Uri.parse("package:" + this.getPackageName()));
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                        showPermissionsDialog();
                    }
                }

                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:

                try {
                    uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        try {
                            ringtoneString = RingtoneManager.getRingtone(this, uri).getTitle(this);
                            alarmVol.setVisibility(View.VISIBLE);
                        } catch (final Exception e) {
                            ringtoneString = "없음";
                            alarmVol.setVisibility(View.INVISIBLE);
                            AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
                            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                        alarmWay.setText(ringtoneString);
                    }
                    RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, uri);

                }catch (NullPointerException e){

                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                Toast.makeText(this, "onResume: Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void forday(int week) {
        String memo;
        Intent intent = new Intent();
        intent.putExtra("url", uri.toString());

        Log.d("main", "memobefore : " + alarmMemo.getText().toString());
        if(alarmMemo.getText().toString() == null) {
            memo = "씻고 왔나요?";
        }else{
            memo = alarmMemo.getText().toString();
        }
        Log.d("main", "memo : " + alarmMemo.getText().toString());
        intent.putExtra("memo", alarmMemo.getText().toString());
        intent.setAction("com.example.devcamp.alarm.AlarmReceiver"); //리시버 등록

//        Toast.makeText(this, "url: "+uri, Toast.LENGTH_SHORT).show();
        Log.d("main", "url: "+ uri);

        //intent 설정 변경 , FLAG_ONE_SHOT 일회성 인텐트
        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_ONE_SHOT);


        calSet = Calendar.getInstance();
        calSet.set(Calendar.DAY_OF_WEEK, week);
        hour = mTime.getHour();
        minute = mTime.getMinute();

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), 1 * 60 * 60 * 1000, pendingIntent);
    }

    private void showPermissionsDialog() {
        if (Build.VERSION.SDK_INT == 23) {

            int hasWriteSettingsPermission = checkSelfPermission(Manifest.permission.WRITE_SETTINGS);
            if (hasWriteSettingsPermission != PackageManager.PERMISSION_GRANTED) {
                //You can skip the next if block. I use it to explain to user why I wan his permission.
                if (!ActivityCompat.shouldShowRequestPermissionRationale(AlarmUpdateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showMessageOKCancel("You need to allow write settings",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                    intent.setData(Uri.parse("package:" + AlarmUpdateActivity.this.getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                            });
                    return;
                }
//The next line causes a dialog to popup, asking the user to allow or deny us write permission
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
                return;
            } else {
                //Permissions have already been granted. Do whatever you want :)
            }
        }
    }

    //Now you only need this if you want to show the rationale behind
//requesting the permission.
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AlarmUpdateActivity.this).setMessage(message).setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null).show();
    }

    //This method is called immediately after the user makes his decision to either allow
    // or disallow us permision.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_SETTINGS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //User pressed the allowed button
                    //Do what you want :)
                    Intent i = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    startActivityForResult(i, 0);
                } else {
                    //User denied the permission
                    //Come up with how to hand the requested permission
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}