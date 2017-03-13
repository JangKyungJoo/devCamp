package com.example.devcamp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReportActivity extends Activity {

    ListView l1;
    String[] t1={"Until now 피부관리 통합 통지표",
            "Monthly 피부관리 리포트",
            "My 피부 관리 경향 및 스타일"};
    String[] d1={"lesson1","lesson2"};
    int[] i1 ={R.drawable.image1,R.drawable.image2,R.drawable.image1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        l1=(ListView)findViewById(R.id.list);
        l1.setAdapter(new dataListAdapter(t1,d1,i1));
    }

    class dataListAdapter extends BaseAdapter {
        String[] Title, Detail;
        int[] imge;

        dataListAdapter() {
            Title = null;
            Detail = null;
            imge=null;
        }

        public dataListAdapter(String[] text, String[] text1,int[] text3) {
            Title = text;
            Detail = text1;
            imge = text3;

        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Title.length;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.list_report, parent, false);
            TextView title;
            ImageView i1;
            title = (TextView) row.findViewById(R.id.title);

            i1=(ImageView)row.findViewById(R.id.img);
            title.setText(Title[position]);

            i1.setImageResource(imge[position]);

            return (row);
        }
    }
}