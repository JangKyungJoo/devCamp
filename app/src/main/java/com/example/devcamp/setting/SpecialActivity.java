package com.example.devcamp.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.R;
import com.example.devcamp.util.Special;
import com.example.devcamp.util.SpecialListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SpecialActivity extends Activity {

    private View view = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);

        // ArrayAdapter를 통해 LIST로 표시할 오브젝트를 지정한다.
        // 여기서는 심플하게 그냥 String
        // 레이아웃 android.R.layout.simple_list_item_1 는 안드로이드가 기본적으로 제공하는 간단한 아이템 레이아웃
        ListView listview;
        SpecialListViewAdapter adapter;

        adapter = new SpecialListViewAdapter();

        listview = (ListView) findViewById(R.id.listViewSpecial);
        listview.setAdapter(adapter);


        ArrayList<Special> specialCare = Special.load(getApplicationContext(), null, Special.SPECIALCARE_NAME);
        if(specialCare.size()>0){

            String jsonStr = null;
            JSONArray jarr = null;
            try {
                for (int i = 0; i < specialCare.size(); i++) {
                    ArrayList<String> cares = new ArrayList<String>();
                    jsonStr = specialCare.get(i).getName();
                    jarr = new JSONArray(jsonStr);
                    for (int h = 0; h < jarr.length(); h++) {
                        cares.add(jarr.optString(h));
                    }
                    if(cares.size()>0){
                        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), specialCare.get(i).getID(), cares.get(0), cares.get(1));
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        // 아이템을 추가
        //adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), "각질 제거", "월", "7일주기") ;
        //adapter.addItem(ContextCompat.getDrawable(this, R.drawable.specialday_icon_01_edit), "녹차 팩", "월", "14일주기") ;
        view = findViewById(R.id.addDdayButton);
        // ListView 가져오기
        ListView listView = (ListView) findViewById(R.id.listViewSpecial);

        // ListView에 각각의 아이템표시를 제어하는 Adapter를 설정
        listView.setAdapter(adapter);

        // 아이템을 [클릭]시의 이벤트 리스너를 등록
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                String item = (String) listView.getItemAtPosition(position).toString();
                Toast.makeText(SpecialActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDday = new Intent(SpecialActivity.this, SpecialSettingActivity.class);
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
                //Toast.makeText(SpecialActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 롱클릭 시 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {

                final int position = pos;
                final TextView listText = (TextView)view.findViewById(R.id.dday_title);
                AlertDialog.Builder delete_builder = new AlertDialog.Builder(SpecialActivity.this);
                delete_builder.setTitle("삭제 확인");
                delete_builder.setMessage("삭제하시겠습니까?");
                delete_builder.setPositiveButton("확인버튼",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(listText.getText().toString()!=null){
                                    Special.deleteSpecial(getApplicationContext(), Special.SPECIALCARE_NAME, listText.getText().toString());
                                    Toast.makeText(SpecialActivity.this, "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SpecialActivity.this, "삭제를 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
}