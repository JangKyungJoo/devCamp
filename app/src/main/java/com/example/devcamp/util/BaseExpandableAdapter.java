package com.example.devcamp.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devcamp.R;
import com.example.devcamp.SettingActivity;

import java.util.ArrayList;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    class ViewHolder {
        public ImageView icon_group, icon_toggle, icon_arrow;
        public TextView title_group, title_child;
    }

    final String colorPrimaryDark = "#B5A5F2";
    final String colorWhite = "#ffffff";

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public BaseExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.layout_item_expandable, parent, false);
            viewHolder.title_group = (TextView) v.findViewById(R.id.title_group);
            viewHolder.icon_group = (ImageView) v.findViewById(R.id.icon_group);
            viewHolder.icon_arrow= (ImageView)v.findViewById(R.id.icon_arrow);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        switch(groupPosition){
            case 0:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_skin);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            case 1:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_checklist);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            case 2:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_push);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            case 3:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_special);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            case 4:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_special_day);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            case 5:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_circle);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            case 6:
                viewHolder.icon_group.setImageResource(R.mipmap.icon_setting_trainer);
                v.setBackgroundColor(Color.parseColor(colorPrimaryDark));
                break;

            default:
                break;
        }

        if(isExpanded){
            viewHolder.icon_arrow.setImageResource(R.mipmap.icon_setting_arrow_up);
        }else{
            viewHolder.icon_arrow.setImageResource(R.mipmap.icon_setting_arrow_down);
        }

        viewHolder.title_group.setText(getGroup(groupPosition));

        return v;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(childList.get(groupPosition)!=null){
            return childList.get(groupPosition).size();
        }
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.layout_item_expandable, null);
            v.setBackgroundColor(Color.parseColor(colorWhite));
            viewHolder.title_child = (TextView) v.findViewById(R.id.title_child);
            viewHolder.icon_toggle = (ImageView) v.findViewById(R.id.icon_toggle);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        v.findViewById(R.id.icon_arrow).setVisibility(View.GONE);

        viewHolder.title_child.setTextColor(Color.parseColor("#A188E5"));
        if((groupPosition == SettingActivity.SPECIAL_CARE || groupPosition == SettingActivity.SPECIAL_DAY) && childPosition == 1){
            viewHolder.title_child.setText(getChild(groupPosition, childPosition));
            viewHolder.icon_toggle.setImageResource(R.mipmap.icon_setting_toggle_on);
            return v;
        }

        viewHolder.title_child.setText(getChild(groupPosition, childPosition));
        viewHolder.icon_toggle.setImageResource(R.mipmap.icon_setting_icon_right);
        return v;
    }

    @Override
    public boolean hasStableIds() {	return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

}
