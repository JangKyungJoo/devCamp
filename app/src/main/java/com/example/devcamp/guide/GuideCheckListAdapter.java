package com.example.devcamp.guide;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ridickle on 2017. 3. 28..
 */

public class GuideCheckListAdapter extends ArrayAdapter<CheckItem> {

    ArrayList<CheckItem> data;
    Context context;
    int itemLayout;
    LayoutInflater inflater;

    public GuideCheckListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CheckItem> objects) {
        super(context, resource, objects);
        this.context = context;
        itemLayout = resource;
        data = (ArrayList<CheckItem>)objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(itemLayout, parent, false);
        }

        TextView guideDetail_ItemTitle = (TextView) convertView.findViewById(R.id.guideDetail_ItemTitle);
        FrameLayout guideDetail_ItemDescriptionButton = (FrameLayout) convertView.findViewById(R.id.guideDetail_ItemDescriptionButton);
        TextView guideDetail_ItemDescriptionText = (TextView) convertView.findViewById(R.id.guideDetail_ItemDescriptionText);

        guideDetail_ItemTitle.setText(data.get(position).getTitle());

        guideDetail_ItemDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다이얼로그 띄워주기
                Toast.makeText(context, data.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                GuideDialog mCustomDialog = new GuideDialog(context
                        ,R.style.MyDialog, data.get(position));
                mCustomDialog.setCanceledOnTouchOutside(true);  // Dialog 밖을 터치 했을 경우 Dialog 사라지게 하기


                mCustomDialog.show();
            }
        });

        guideDetail_ItemDescriptionText.setText(data.get(position).getDescription());

        return convertView;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        return false;
//    }
}
