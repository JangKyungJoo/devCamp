package com.example.devcamp.util;

/**
 * Created by samdasu on 2017-02-24.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.example.devcamp.R;

import java.util.ArrayList;

public class BaseExpandableAdapter extends BaseExpandableListAdapter{

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public BaseExpandableAdapter(Context c, ArrayList<String> groupList,
                                 ArrayList<ArrayList<String>> childList
    ){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;

    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.tv_groupName = (TextView) v.findViewById(R.id.tv_group);
            viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
            viewHolder.icon_image= (ImageView)v.findViewById(R.id.icon_image);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.tv_groupName.setTextColor(Color.parseColor("#ffffff"));
        switch(groupPosition){
            case 0:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_01_skin);

                v.setBackgroundColor(Color.parseColor("#A188E5"));
                break;
            case 1:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_02_checklist);

                v.setBackgroundColor(Color.parseColor("#B5A5F2"));
                break;
            case 2:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_03_push);

                v.setBackgroundColor(Color.parseColor("#B5A5F2"));
                break;
            case 3:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_04_special);

                v.setBackgroundColor(Color.parseColor("#B5A5F2"));
                break;
            case 4:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_05_specialday);

                v.setBackgroundColor(Color.parseColor("#B5A5F2"));
                break;
            case 5:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_06_cycle);

                v.setBackgroundColor(Color.parseColor("#B5A5F2"));
                break;
            case 6:
                viewHolder.iv_image.setImageResource(R.drawable.setting_icon_07_trainer);

                v.setBackgroundColor(Color.parseColor("#B5A5F2"));
                break;

        }

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if(isExpanded){
            viewHolder.icon_image.setImageResource(R.drawable.setting_icon_08_arrow03);
        }else{
            viewHolder.icon_image.setImageResource(R.drawable.setting_icon_08_arrow01);
        }

        viewHolder.tv_groupName.setText(getGroup(groupPosition));

        return v;
    }


    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {

        return childList.get(groupPosition).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        if(childList.get(groupPosition)!=null){
            return childList.get(groupPosition).size();
        }else{
            return 0;
        }

    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, null);
            v.setBackgroundColor(Color.parseColor("#000000"));
            viewHolder.tv_childName = (TextView) v.findViewById(R.id.tv_child);
            viewHolder.icon_childImage = (ImageView) v.findViewById(R.id.image_child);

            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.tv_childName.setTextColor(Color.parseColor("#A188E5"));
        if((groupPosition==3 && childPosition==1)|| (groupPosition==4 && childPosition==1)){

            //viewHolder.mySwitch = (Switch)v.findViewById(R.id.mySwitch);

            viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

            viewHolder.icon_childImage.setImageResource(R.drawable.setting_icon_09_on);
        }else{
            viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

            viewHolder.icon_childImage.setImageResource(R.drawable.setting_icon_08_arrow02);
        }
        return v;
    }

    @Override
    public boolean hasStableIds() {	return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public ImageView iv_image;
        public TextView tv_groupName;
        public TextView tv_childName;
        public ImageView icon_image;
        public ImageView icon_childImage;
        public Switch mySwitch;
    }

}

