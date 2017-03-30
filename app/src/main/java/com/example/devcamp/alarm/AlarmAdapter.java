package com.example.devcamp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import static java.lang.Integer.parseInt;

public class AlarmAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Alarm> myData;
    private LayoutInflater inflater;

    private static final int HOUR = 0;
    private static final int MINUTE = 1;

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

        btnCancel.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    Toast.makeText(context, myData.get(position).getMemo(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    PendingIntent sender
                            = PendingIntent.getBroadcast(context, myData.get(position).get_id(), intent, 0);

                    AlarmManager manager =
                            (AlarmManager)context
                                    .getSystemService(Context.ALARM_SERVICE);

                    manager.cancel(sender);


                }

            }
        });
        btnCancel.setFocusable(false);



        return convertView;
    }

    public String setString(String rawTime){
        String return_string = "";

        String amPm = rawTime.substring(0,2);
        String str = rawTime.substring(2);

        return_string += amPm;  // 오전 오후 추가

        String[] spilt_string = str.split(":");
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