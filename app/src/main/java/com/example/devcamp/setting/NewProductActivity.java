package com.example.devcamp.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.R;
import com.example.devcamp.util.Dday;
import com.example.devcamp.util.ListViewAdapter;
import com.example.devcamp.util.Special;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewProductActivity extends Activity {
    private int tYear;           //오늘 연월일 변수
    private int tMonth;
    private int tDay;

    private int dYear=2017;        //디데이 연월일 변수
    private int dMonth=3;
    private int dDay=14;

    private String resultDday ;

    private long d;
    private long t;
    private long r;
    private View view = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        // ArrayAdapter를 통해 LIST로 표시할 오브젝트를 지정한다.
        // 여기서는 심플하게 그냥 String
        // 레이아웃 android.R.layout.simple_list_item_1 는 안드로이드가 기본적으로 제공하는 간단한 아이템 레이아웃
        ListView listview;
        final ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listview = (ListView) findViewById(R.id.listView);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        ArrayList<Special> ddayArr = Special.load(getApplicationContext(), date.getYear() + "." + date.getMonth() + "." + date.getDay(), Special.NEWPRODUCT_NAME);

        // 아이템을 추가
        /*adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), "두근두근 소개팅", "2017/08/30", "D-20") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), "두근두근 소개팅", "2017/03/14", "D-30") ;*/
        Calendar calendar =Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);


        Calendar dCalendar =Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        t=calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        Calendar calendarTo = Calendar.getInstance();

        for(int i= 0; i<ddayArr.size();i++){

            d = Long.parseLong(ddayArr.get(i).getName());
            r=(d-t)/(24*60*60*1000);
            int resultNumber=(int)r+1;
            if(resultNumber>=0){
                resultDday = (String.format("D-%d", resultNumber));
            }
            else{
                int absR=Math.abs(resultNumber);
                resultDday= (String.format("D+%d", absR));
            }
            calendarTo.setTimeInMillis(d);

            int mYear = calendarTo.get(Calendar.YEAR);
            int mMonth = calendarTo.get(Calendar.MONTH);
            int mDay = calendarTo.get(Calendar.DAY_OF_MONTH);

            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialcare_icon_01_edit), ddayArr.get(i).getID(), String.format("%d/%d/%d",mYear, mMonth+1,mDay), resultDday);
        }

        view = findViewById(R.id.addDdayButton);
        //addDdayButton = (Button) findViewById(R.id.addDdayButton);
        // ListView 가져오기
        ListView listView = (ListView) findViewById(R.id.listView);

        // ListView에 각각의 아이템표시를 제어하는 Adapter를 설정
        listView.setAdapter(adapter);

        // 아이템을 [클릭]시의 이벤트 리스너를 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                //String item = (String) listView.getItemAtPosition(position).toString();
                TextView listText = (TextView)view.findViewById(R.id.dday_title);
                Intent intent = new Intent(NewProductActivity.this, NewProductModifyActivity.class);
                intent.putExtra("item",listText.getText().toString());
                startActivity(intent);
                //Toast.makeText(DdayActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDday = new Intent(NewProductActivity.this, NewProductSettingActivity.class);
                startActivity(addDday);
                finish();
            }
        });

        // 아이템을 [선택]시의 이벤트 리스너를 등록
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 선택시에 구현할 내용은 여기에.
                String item = (String) listView.getSelectedItem();

                Toast.makeText(NewProductActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}