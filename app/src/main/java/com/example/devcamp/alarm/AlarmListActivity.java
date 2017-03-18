package com.example.devcamp.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.R;
import com.example.devcamp.util.Alarm;

import java.util.ArrayList;

public class AlarmListActivity extends AppCompatActivity {


    // 멤버 변수 선언
    AlarmDBHelper alarmDBHelper;
    ArrayList<Alarm> alarmList;

    int UpdatePosition;
    private AlarmAdapter alarmAdapter;
    private ListView listView;

    Button addAlarm;

    TextView textNoti;

    Intent intentNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        textNoti = (TextView)findViewById(R.id.notiAlarmList);
        intentNoti = getIntent();
        if(intentNoti.getStringExtra("alarm").equals("sleep")){
            textNoti.setText("취침 전 알림");
        }else{
            textNoti.setText("세안 전 알림");
        }
        addAlarm = (Button)findViewById(R.id.alarmAdd);

        alarmList = new ArrayList<Alarm>();
        alarmDBHelper = new AlarmDBHelper(this);
        alarmList = readDatabase();

        alarmAdapter = new AlarmAdapter(this, R.layout.alarm_item, alarmList);

        listView = (ListView) findViewById(R.id.alarmList);

        listView.setAdapter(alarmAdapter);

        AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UpdatePosition = position;
                Alarm currentData = alarmList.get(position);

                Intent intent = new Intent(AlarmListActivity.this, AlarmUpdateActivity.class);
                intent.putExtra("data", currentData);
                intent.putExtra("alarm", intentNoti.getStringExtra("alarm"));
                startActivityForResult(intent, 100);

//                Toast.makeText(MainActivity.this, bookList.get(position).getTitle() + " 선택", Toast.LENGTH_SHORT).show();
            }
        };
        listView.setOnItemClickListener(mItemClickListener);

        // 롱클릭 시 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {

                final int position = pos;
                AlertDialog.Builder delete_builder = new AlertDialog.Builder(AlarmListActivity.this);
                delete_builder.setTitle("삭제 확인");
                delete_builder.setMessage("삭제하시겠습니까?");
                delete_builder.setPositiveButton("확인버튼",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Alarm currentData = alarmList.get(position);

                                SQLiteDatabase db = alarmDBHelper.getWritableDatabase();

                                String whereClause = "_id=?";
                                String[] whereArgs = new String[] { Integer.valueOf(currentData.get_id()).toString()};
                                int result = db.delete(AlarmDBHelper.TABLE_NAME, whereClause, whereArgs);
                                alarmDBHelper.close();

                                if (result > 0) {
                                    alarmList.remove(position);
                                    alarmAdapter.notifyDataSetChanged();
                                    Toast.makeText(AlarmListActivity.this, "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                                } else  {
                                    Toast.makeText(AlarmListActivity.this, "삭제를 실패하였습니다.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                );
                delete_builder.setNegativeButton("취소버튼", null);

                Dialog dlg = delete_builder.create();

                dlg.setCanceledOnTouchOutside(true);
                dlg.show();


                return true;
            }
        });


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.alarmAdd:
                Intent intent = new Intent(AlarmListActivity.this, AlarmUpdateActivity.class);
                intent.putExtra("alarm", intentNoti.getStringExtra("alarm"));
                startActivity(intent);
                finish();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){     // 수정하고 온 경우
            switch (resultCode){
                case Activity.RESULT_OK:
                    Alarm mydata = alarmList.get(UpdatePosition); //고칠려고 클릭했던 객체

                    Alarm updateData = (Alarm) data.getSerializableExtra("return_data");
                    mydata.setTime(updateData.getTime());
                    mydata.setMonday(updateData.isMonday());
                    mydata.setTuesday(updateData.isTuesday());
                    mydata.setWednesday(updateData.isWednesday());
                    mydata.setThursday(updateData.isThursday());
                    mydata.setFriday(updateData.isFriday());
                    mydata.setSaturday(updateData.isSaturday());
                    mydata.setSunday(updateData.isSunday());
                    mydata.setCancel(updateData.isCancel());
                    mydata.setMemo(updateData.getMemo());
                    mydata.setRingtone(updateData.getRingtone());
                    alarmAdapter.notifyDataSetChanged();

                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    }



    // DB 읽어오기 메소드 선언
    private ArrayList<Alarm> readDatabase() {

        ArrayList<Alarm> list = new ArrayList<Alarm>();

        SQLiteDatabase db = alarmDBHelper.getReadableDatabase();

        Cursor cursor = db.query(AlarmDBHelper.TABLE_NAME, null, null, null, null, null, null, null);

//        SQL 문을 사용하여 데이터 추출
//        Cursor cursor = db.rawQuery("select * from " + AlarmDBHelper.DB_NAME);

        while(cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String time = cursor.getString(1);
            boolean sunday = (cursor.getInt(2) != 0);
            boolean monday = (cursor.getInt(3) != 0);
            boolean tuesday = (cursor.getInt(4) != 0);
            boolean wednesday = (cursor.getInt(5) != 0);
            boolean thursday = (cursor.getInt(6) != 0);
            boolean friday = (cursor.getInt(7) != 0);
            boolean saturday = (cursor.getInt(8) != 0);
            boolean cancel = (cursor.getInt(9) != 0);
            String memo = cursor.getString(10);
            String ringtone = cursor.getString(11);
            Alarm newItem = new Alarm(id, time, sunday, monday, tuesday, wednesday, thursday, friday, saturday, cancel, memo, ringtone);
            list.add(newItem);
        }

        cursor.close();
        alarmDBHelper.close();

        return list;
    }


}
