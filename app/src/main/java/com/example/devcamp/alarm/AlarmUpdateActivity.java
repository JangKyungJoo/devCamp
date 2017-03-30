package com.example.devcamp.alarm;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


public class AlarmUpdateActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 5000;
    private static final int HOUR = 0;
    private static final int MINUTE = 1;

    public static AlarmManager alarmManager;
    public static PendingIntent[] pendingIntent;


    TimePicker mTime;
    int hour, minute; //timepicker로 받아온 시,분을 저장
    boolean reviceDataFlag = false;     // 수정하기 위해 넘어온 것인가
    Uri ringtone_url;
    String type;

    Intent intent;

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

    boolean monday = false;
    boolean tuesday = false;
    boolean wednesday = false;
    boolean thursday = false;
    boolean friday = false;
    boolean saturday = false;
    boolean sunday = false;
    TextView textNoti;
    int updateFlag;
    final int INSERT = 0; final int UPDATE = 1;
    public static int insertNum = 1;
    String ringtoneString;

    AlarmDBHelper alarmDBHelper;
    Alarm data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        intent = getIntent();
        pendingIntent = new PendingIntent[100];

        type=intent.getStringExtra("alarm");
        textNoti = (TextView) findViewById(R.id.notiAlarm);

        if (intent.getStringExtra("alarm").equals("sleep")) {
            textNoti.setText("취침 전 알림");
        } else {
            textNoti.setText("세안 전 알림");
        }
        updateFlag = intent.getIntExtra("updateFlag", -1);

        alarmDBHelper = new AlarmDBHelper(this);
        tvSunday = (TextView) findViewById(R.id.txSunday);
        tvMonday = (TextView) findViewById(R.id.txMonday);
        tvTuesday = (TextView) findViewById(R.id.txTuesday);
        tvWednesday = (TextView) findViewById(R.id.txWednesday);
        tvThursday = (TextView) findViewById(R.id.txThursday);
        tvFriday = (TextView) findViewById(R.id.txFriday);
        tvSaturday = (TextView) findViewById(R.id.txSaturday);

        btnAlarm = (Button) findViewById(R.id.btnAlarm);
        alarmWayBtn = (ImageButton) findViewById(R.id.alarmWayButton);
        alarmWay = (TextView) findViewById(R.id.alarmWay);
        alarmVol = (SeekBar) findViewById(R.id.alarmVolume);

        alarmMemo = (EditText) findViewById(R.id.alarmMemo);

        mTime = (TimePicker) findViewById(R.id.timepicker);

        mTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int m) {
                //값이 바뀔때 hour, minute에 바뀐 시,분을 저장
                hour = h;
                minute = m;
            }
        });

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (getIntent().getSerializableExtra("data") != null) {
            data = (Alarm) getIntent().getSerializableExtra("data");
            btnAlarm.setText("알람 수정하기");

            alarmWay.setText(data.getRingtone());

            alarmMemo.setText(data.getMemo());

            type = data.getType();

            ringtone_url = Uri.parse(data.getRingtone_url());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (data.getTime().contains("오후"))
                    mTime.setHour(refineTime(data.getTime().substring(2), HOUR) + 12);
                else
                    mTime.setHour(refineTime(data.getTime().substring(2), HOUR));
                mTime.setMinute(refineTime(data.getTime().substring(2), MINUTE));
            } else {
                if (data.getTime().contains("오후"))
                    mTime.setCurrentHour(refineTime(data.getTime().substring(2), HOUR) + 12);
                else
                    mTime.setCurrentHour(refineTime(data.getTime().substring(2), HOUR));
                mTime.setCurrentMinute(refineTime(data.getTime().substring(2), MINUTE));
            }

            if (data.isMonday()) {
                tvMonday.setTextColor(Color.parseColor("#B5A5F2"));
                monday = true;
            }
            if (data.isTuesday()) {
                tvTuesday.setTextColor(Color.parseColor("#B5A5F2"));
                tuesday = true;
            }
            if (data.isWednesday()) {
                tvWednesday.setTextColor(Color.parseColor("#B5A5F2"));
                wednesday = true;
            }
            if (data.isThursday()) {
                tvThursday.setTextColor(Color.parseColor("#B5A5F2"));
                thursday = true;
            }
            if (data.isFriday()) {
                tvFriday.setTextColor(Color.parseColor("#B5A5F2"));
                friday = true;
            }
            if (data.isSaturday()) {
                tvSaturday.setTextColor(Color.parseColor("#B5A5F2"));
                saturday = true;
            }
            if (data.isSunday()) {
                tvSunday.setTextColor(Color.parseColor("#B5A5F2"));
                sunday = true;
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

                Log.d("day", "mon : " + monday + " fri : " + friday);


                if (tvMonday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    monday = true;
                    forday(2);
                } else if (tvTuesday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    tuesday = true;
                    forday(3);
                } else if (tvWednesday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    wednesday = true;
                    forday(4);
                } else if (tvThursday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    thursday = true;
                    forday(5);
                } else if (tvFriday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    friday = true;
                    forday(6);
                } else if (tvSaturday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    saturday = true;
                    forday(7);
                } else if (tvSunday.getCurrentTextColor() == Color.parseColor("#B5A5F2")) {
//                    sunday = true;
                    forday(1);
                }

                String time;
                if (hour >= 12)
                    time = "오후" + (hour - 12) + ":" + minute;
                else
                    time = "오전" + hour + ":" + minute;
                row.put("type", type);
                row.put("time", time);
                row.put("sunday", (sunday) ? 1 : 0);
                row.put("monday", (monday) ? 1 : 0);
                row.put("tuesday", (tuesday) ? 1 : 0);
                row.put("wednesday", (wednesday) ? 1 : 0);
                row.put("thursday", (thursday) ? 1 : 0);
                row.put("friday", (friday) ? 1 : 0);
                row.put("saturday", (saturday) ? 1 : 0);
                row.put("cancel", 0);
                if (alarmMemo.getText() != null)
                    row.put("memo", alarmMemo.getText().toString());
                else
                    row.put("memo", "");
                row.put("ringtone", ringtoneString);
                if(ringtone_url.toString() != null)
                    row.put("ringtone_url", ringtone_url.toString());
                else
                    row.put("ringtone_url", "");


                if(updateFlag == INSERT) {
                    Toast.makeText(AlarmUpdateActivity.this, hour + " : " + minute, Toast.LENGTH_SHORT).show();
                    db.insert(AlarmDBHelper.TABLE_NAME, null, row);
                    insertNum++;


                }
                else if(updateFlag == UPDATE){
                    String whereClause = "_id=?";
                    String[] whereArgs = new String[]{Integer.valueOf(data.get_id()).toString()};

                    db.update(AlarmDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                }


                alarmDBHelper.close();

                Alarm return_data = new Alarm(type, time ,monday, tuesday, wednesday, thursday, friday, saturday, true, alarmMemo.getText().toString(), ringtoneString, ringtone_url.toString());
                Intent insertIntent = new Intent(AlarmUpdateActivity.this, AlarmListActivity.class);
                insertIntent.putExtra("alarm", type);
                insertIntent.putExtra("return_data", return_data);
                setResult(Activity.RESULT_OK, insertIntent);
                finish();
//                break;
            }
        });
    }


    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setAction("com.example.devcamp.alarm.AlarmReceiver"); //리시버 등록

        if(data == null){
            Log.d("insert position", "" + insertNum);
            pendingIntent[insertNum]
                    = PendingIntent.getBroadcast(this, insertNum, intent, FLAG_UPDATE_CURRENT);
        }
        else{
            pendingIntent[data.get_id()]
                    = PendingIntent.getBroadcast(this, data.get_id(), intent, FLAG_UPDATE_CURRENT);
        }



        switch (v.getId()) {
            case R.id.txSunday:

                if (tvSunday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvSunday.setTextColor(Color.parseColor("#B5A5F2"));
                    sunday = true;
                }
                else {
                    tvSunday.setTextColor(Color.GRAY);
                    sunday = false;
                }
                break;

            case R.id.txMonday:

                if (tvMonday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvMonday.setTextColor(Color.parseColor("#B5A5F2"));
                    monday = true;
                } else {
                    tvMonday.setTextColor(Color.GRAY);
                    monday = false;
                }
                break;

            case R.id.txTuesday:
                if (tvTuesday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvTuesday.setTextColor(Color.parseColor("#B5A5F2"));
                    tuesday = true;
                } else {
                    tvTuesday.setTextColor(Color.GRAY);
                    tuesday = false;
                }

                break;
            case R.id.txWednesday:
                if (tvWednesday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvWednesday.setTextColor(Color.parseColor("#B5A5F2"));
                    wednesday = true;
                } else {
                    tvWednesday.setTextColor(Color.GRAY);
                    wednesday = false;
                }

                break;
            case R.id.txThursday:
                if (tvThursday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvThursday.setTextColor(Color.parseColor("#B5A5F2"));
                    thursday = true;
                } else {
                    tvThursday.setTextColor(Color.GRAY);
                    thursday = false;
                }
                break;
            case R.id.txFriday:
                if (tvFriday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvFriday.setTextColor(Color.parseColor("#B5A5F2"));
                    friday = true;
                } else {
                    tvFriday.setTextColor(Color.GRAY);
                    friday = false;
                }
                break;
            case R.id.txSaturday:
                if (tvSaturday.getCurrentTextColor() != Color.parseColor("#B5A5F2") ) {
                    tvSaturday.setTextColor(Color.parseColor("#B5A5F2"));
                    saturday = true;
                } else {
                    tvSaturday.setTextColor(Color.GRAY);
                    saturday = sunday;
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
                } else {
                    Toast.makeText(this, "onCreate: Already Granted (jellybean) ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    startActivityForResult(i, 0);
                }

                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:             // 등록
                try {
                    ringtone_url = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Log.d("ringtone_url", "ringtone_url : " + ringtone_url + "auth : " + ringtone_url.getAuthority());

                    if (ringtone_url != null) {
                        try {
                            ringtoneString = RingtoneManager.getRingtone(this, ringtone_url).getTitle(this);
                            alarmVol.setVisibility(View.VISIBLE);
                        } catch (final Exception e) {
                            ringtoneString = "없음";
                            alarmVol.setVisibility(View.INVISIBLE);
                            AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
                            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                        alarmWay.setText(ringtoneString);
                    }
                    RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, ringtone_url);

                } catch (NullPointerException e) {

                }
                break;

            case 100:           // 수정
                reviceDataFlag = true;
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


    public void forday(int week) {
        String memo;
        Intent intent = new Intent();
        intent.putExtra("url", ringtone_url.toString());

        if (alarmMemo.getText().toString().isEmpty()) {
            memo = "씻고 왔나요?";
        } else {
            memo = alarmMemo.getText().toString();
        }

        intent.putExtra("memo", alarmMemo.getText().toString());
        intent.setAction("com.example.devcamp.alarm.AlarmReceiver"); //리시버 등록



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = mTime.getHour();
            minute = mTime.getMinute();
        }

        else {
            hour = mTime.getCurrentHour();
            minute = mTime.getCurrentMinute();
        }

        Calendar curTime = Calendar.getInstance();
//        curTime.set(Calendar.YEAR, curTime.get(Calendar.YEAR));
//        curTime.set(Calendar.MONTH, curTime.get(Calendar.MONTH));
        Log.d("alarm time", "day : " + week + " hour : " + hour + " minute : " + minute);
        curTime.set(Calendar.DAY_OF_WEEK, week);
        curTime.set(Calendar.HOUR_OF_DAY, hour);
        curTime.set(Calendar.MINUTE, minute);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);

        if(data == null)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    curTime.getTimeInMillis(),   24 * 60 * 60 * 1000, pendingIntent[insertNum]);
        else
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    curTime.getTimeInMillis(),   24 * 60 * 60 * 1000, pendingIntent[data.get_id()]);
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

    public int refineTime(String rawTime, int flag){
        String[] st = rawTime.split(":");
        int return_value = -1;

        switch(flag){
            case 0:     // hour
                return_value = Integer.parseInt(st[HOUR]);
                break;

            case 1:     // minute
                return_value = Integer.parseInt(st[MINUTE]);
                break;
        }

        return return_value;
    }

}
