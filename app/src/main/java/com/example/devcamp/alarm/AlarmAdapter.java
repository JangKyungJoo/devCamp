package com.example.devcamp.alarm;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.devcamp.R;
import com.example.devcamp.util.Alarm;

import java.util.ArrayList;

/**
 * Created by jiyoung on 2017-02-25.
 */

public class AlarmAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Alarm> myData;
    private LayoutInflater inflater;

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



        textTime.setText(myData.get(position).getTime().toString());
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
        if(myData.get(position).getMemo() != null)
            memo.setText(myData.get(position).getMemo());
        else
            memo.setText("씻고 오셨나요?");
        if(myData.get(position).isCancel())
            btnCancel.setChecked(true);

        btnCancel.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
            }
        });
        btnCancel.setFocusable(false);



        return convertView;
    }
}