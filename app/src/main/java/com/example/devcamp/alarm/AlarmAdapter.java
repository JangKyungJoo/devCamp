package com.example.devcamp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.R;
import com.example.devcamp.util.Alarm;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.devcamp.alarm.AlarmUpdateActivity.alarmManager;
import static com.example.devcamp.alarm.AlarmUpdateActivity.pendingIntent;
import static java.lang.Integer.parseInt;

public class AlarmAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Alarm> myData;
    private LayoutInflater inflater;

    private static final int HOUR = 0;
    private static final int MINUTE = 1;

    int hour;
    int minute;


    public AlarmAdapter(Context context, int layout, ArrayList<Alarm> data){
        this.context = context;
        this.layout = layout;
        this.myData = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public AlarmAdapter(AlarmListActivity context, int alarm_item, ArrayList<Alarm> alarmList) {
//        this.context = context;
//        this.layout = alarm_item;
//        this.myData = alarmList;
//        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    @Override
    public int getCount() {//원본데이터의 개수 반환
        return myData.size();
    }

    @Override
    public Object getItem(int position) {//순서에 해당하는 원본 객체 반환
        return myData.get(position);
    }

    @Override
    public long getItemId(int position) {//id반환(dp에서 사용)
        return (long)position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        final int pos = position;

        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }


        TextView textTime = (TextView)convertView.findViewById(R.id.textTime);
        TextView monday = (TextView)convertView.findViewById(R.id.monday);
        TextView tuesday = (TextView)convertView.findViewById(R.id.tuesday);
        TextView wednesday = (TextView)convertView.findViewById(R.id.wednesday);
        TextView thursday = (TextView)convertView.findViewById(R.id.thursday);
        TextView friday = (TextView)convertView.findViewById(R.id.friday);
        TextView saturday = (TextView)convertView.findViewById(R.id.saturday);
        TextView sunday = (TextView)convertView.findViewById(R.id.sunday);
        TextView memo = (TextView)convertView.findViewById(R.id.memo);
        Switch btnCancel = (Switch)convertView.findViewById(R.id.alarmSwitch);


        String refineTime = setString(myData.get(position).getTime());
        textTime.setText(refineTime);
        if(myData.get(position).isMonday()){
            monday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(myData.get(position).isTuesday()){
            tuesday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(myData.get(position).isWednesday()){
            wednesday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(myData.get(position).isThursday()){
            thursday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(myData.get(position).isFriday()){
            friday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(myData.get(position).isSaturday()){
            saturday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(myData.get(position).isSunday()){
            sunday.setTextColor(Color.parseColor("#B5A5F2"));
        }
        if(!(myData.get(position).getMemo().isEmpty()))
            memo.setText(myData.get(position).getMemo());
        else
            memo.setText("씻고 오셨나요?");

        Log.d("cancel ", " " + myData.get(position).isCancel());
        if(!myData.get(position).isCancel())
            btnCancel.setChecked(true);

        btnCancel.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCancel.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                Log.d("alarm adapter", "position : "  + myData.get(position).get_id());
                if(!isChecked){
//                    pendingIntent[myData.get(position).get_id()-1].cancel();
                    Log.d("cancel", " not checked");
                }
                else{

//                    Log.d("cancel", " checked");
//                    if (myData.get(position).isMonday()) {
//                        forday(2, position);
//                    }
//                    if (myData.get(position).isTuesday()) {
//                        forday(3, position);
//                    }
//                    if (myData.get(position).isWednesday()) {
//                        forday(4, position);
//                    }
//                    if (myData.get(position).isThursday()) {
//                        forday(5, position);
//                    }
//                    if (myData.get(position).isFriday()) {
//                        forday(6, position);
//                    }
//                    if (myData.get(position).isSaturday()) {
//                        forday(7,position);
//                    }
//                    if (myData.get(position).isSunday()) {
//                        forday(1, position);
//                    }


                }

            }
        });
        btnCancel.setFocusable(false);



        return convertView;
    }

    public void forday(int week, int pos) {

        Intent intent = new Intent();

        intent.putExtra("memo", myData.get(pos));
        intent.setAction("com.example.devcamp.alarm.AlarmReceiver"); //리시버 등록

        //intent 설정 변경 , FLAG_ONE_SHOT 일회성 인텐트
        pendingIntent[myData.get(pos).get_id()]
                = PendingIntent.getBroadcast(context, myData.get(pos).get_id(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.DAY_OF_WEEK, week);
        curTime.set(Calendar.HOUR_OF_DAY, hour);
        curTime.set(Calendar.MINUTE, minute);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                curTime.getTimeInMillis(),   24 * 60 * 60 * 1000, pendingIntent[myData.get(pos).get_id()]);

        AlarmDBHelper alarmDBHelper = new AlarmDBHelper(context);;
        SQLiteDatabase db = alarmDBHelper.getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = new String[]{Integer.valueOf(myData.get(pos).get_id()).toString()};

        db.execSQL("update " + AlarmDBHelper.TABLE_NAME + " set cancel = " + 1 + " where _id = " + myData.get(pos).get_id() + ";");
        alarmDBHelper.close();
    }

    public String setString(String rawTime){
        String return_string = "";

        String amPm = rawTime.substring(0,2);
        String str = rawTime.substring(2);

        return_string += amPm;  // 오전 오후 추가


        String[] spilt_string = str.split(":");

        hour = parseInt(spilt_string[HOUR]);

        if(amPm.equals("오후"))
            hour += 12;
        minute = parseInt(spilt_string[MINUTE]);

        if(parseInt(spilt_string[HOUR]) < 10) {       // 시간 부분 처리
            return_string += "0" + parseInt(spilt_string[HOUR]);
        }
        else{
            return_string += Integer.parseInt(spilt_string[HOUR]);
        }

        return_string += ":";

        if(parseInt(spilt_string[MINUTE]) < 10) {       // 시간 부분 처리
            return_string += "0" + parseInt(spilt_string[MINUTE]);
        }
        else{
            return_string += Integer.parseInt(spilt_string[MINUTE]);
        }

        return return_string;
    }
}