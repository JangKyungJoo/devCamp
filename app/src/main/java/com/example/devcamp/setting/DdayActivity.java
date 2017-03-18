package com.example.devcamp.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.devcamp.R;
import com.example.devcamp.util.Dday;
import com.example.devcamp.util.ListViewAdapter;

import java.util.ArrayList;

public class DdayActivity extends Activity {

    private View view = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);

        // ArrayAdapter를 통해 LIST로 표시할 오브젝트를 지정한다.
        // 여기서는 심플하게 그냥 String
        // 레이아웃 android.R.layout.simple_list_item_1 는 안드로이드가 기본적으로 제공하는 간단한 아이템 레이아웃
        ListView listview;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);


        ArrayList<Dday> dday = Dday.load(getApplicationContext(), Dday.DDAY_CHECK);
        if(dday.size()>0){
            for(int i = 0; i<dday.size();i++){
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit),dday.get(i).getID(), dday.get(i).getID(), dday.get(i).getID());
            }
        }
        // 아이템을 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), "두근두근 소개팅", "2017/08/30", "D-20") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), "두근두근 소개팅", "2017/03/14", "D-30") ;
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
                String item = (String) listView.getItemAtPosition(position).toString();
                Toast.makeText(DdayActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDday = new Intent(DdayActivity.this, DdaySettingActivity.class);
                startActivity(addDday);
            }
        });
        /*addDdayButton.getBackground().setColorFilter(Color.parseColor("#8564CC"), PorterDuff.Mode.MULTIPLY);
        addDdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDday = new Intent(DdayActivity.this, DdaySettingActivity.class);
                startActivity(addDday);
            }
        });*/

        // 아이템을 [선택]시의 이벤트 리스너를 등록
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 선택시에 구현할 내용은 여기에.
                String item = (String) listView.getSelectedItem();
                Toast.makeText(DdayActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}