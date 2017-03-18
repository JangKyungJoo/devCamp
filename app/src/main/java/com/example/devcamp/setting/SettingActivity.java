package com.example.devcamp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;


import com.example.devcamp.R;
import com.example.devcamp.alarm.AlarmListActivity;
import com.example.devcamp.util.BaseExpandableAdapter;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity{

    final int SKIN_SELECT = 0;
    final int SETTING_SPECIAL_SKIN = 0;
    final int SETTING_SPECIAL_DAY = 0;
    final int CLEANSING_LIST = 0;
    final int ALARM_CLEANSING = 0;
    final int CHECKLIST = 1;
    final int SKINCARE_LIST = 1;
    final int ALARM_SLEEP = 1;
    final int PUSH_SETTING = 2;
    public static final int SPECIAL_CARE = 3;
    public static final int SPECIAL_DAY = 4;
    final int BODY_RYTHM = 5;
    final int TRAINER = 6;

    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent1 = null;
    private ArrayList<String> mChildListContent2 = null;
    private ArrayList<String> mChildListContent3 = null;
    private ArrayList<String> mChildListContent4 = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        setLayout();

        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent1 = new ArrayList<>();
        mChildListContent2 = new ArrayList<>();
        mChildListContent3 = new ArrayList<>();
        mChildListContent4 = new ArrayList<>();

        mGroupList.add("스킨 선택");
        mGroupList.add("체크리스트");
        mGroupList.add("푸쉬알림");
        mGroupList.add("스페셜 피부 관리");
        mGroupList.add("스페셜 데이");
        mGroupList.add("신체주기");
        mGroupList.add("트레이너 친구");

        mChildListContent1.add("클렌징(세안) 리스트");
        mChildListContent1.add("피부 관리 리스트");

        mChildListContent2.add("세안 알림");
        mChildListContent2.add("취침 전 알림");
        mChildListContent3.add("피부 관리 요일 / 종류 / 주기 설정");
        mChildListContent3.add("푸쉬 알림 on/off");

        mChildListContent4.add("D-Day 지정");
        mChildListContent4.add("푸쉬 알림 on/off");

        mChildList.add(null);
        mChildList.add(mChildListContent1);
        mChildList.add(mChildListContent2);
        mChildList.add(mChildListContent3);
        mChildList.add(mChildListContent4);
        mChildList.add(null);
        mChildList.add(null);

        mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));

        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition == BODY_RYTHM){
                    Intent intent = new Intent(getApplicationContext(), BodyRythmActivity.class);
                    //startActivity(intent);
                }
                if(groupPosition == TRAINER){
                    Intent intent = new Intent(getApplicationContext(), TrainerActivity.class);
                    //startActivity(intent);
                }
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch(groupPosition){
                    case CHECKLIST :
                        if(childPosition == CLEANSING_LIST){
                            Intent intent = new Intent(getApplicationContext(), CleansingActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), SkincareActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case PUSH_SETTING :

                        if(childPosition == ALARM_CLEANSING){
                            // setting alarm
                            Intent intent = new Intent(SettingActivity.this, AlarmListActivity.class);
                            intent.putExtra("alarm","cleansing");
                            startActivity(intent);
                        }else{
                            // setting alarm
                            Intent intent = new Intent(SettingActivity.this, AlarmListActivity.class);
                            intent.putExtra("alarm","sleep");
                            startActivity(intent);
                        }
                        break;

                    case SPECIAL_CARE :
                        if(childPosition == SETTING_SPECIAL_SKIN){
                            Intent intent = new Intent(getApplicationContext(), SpecialActivity.class);
                            startActivity(intent);
                            break;
                        }else{
                            break;
                        }

                    case SPECIAL_DAY :
                        if(childPosition == SETTING_SPECIAL_DAY){
                            Intent intent = new Intent(getApplicationContext(), DdayActivity.class);
                            startActivity(intent);
                            break;
                        }else{
                            break;
                        }

                    default:
                        break;
                }
                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
    }

    private ExpandableListView mListView;

    private void setLayout(){
        mListView = (ExpandableListView) findViewById(R.id.elv_list);
    }
}
