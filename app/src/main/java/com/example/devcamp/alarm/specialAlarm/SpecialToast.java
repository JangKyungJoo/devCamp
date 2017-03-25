package com.example.devcamp.alarm.specialAlarm;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.R;

/**
 * Created by ridickle on 2017. 3. 18..
 */

public class SpecialToast extends Toast {
    Context mContext;

    public SpecialToast(Context context) {
        super(context);
        mContext = context;
    }

    public void showToast(String mText, String sText, int duration) {
        // http://developer.android.com/guide/topics/ui/notifiers/toasts.html
        LayoutInflater inflater;
        View v;

        if (false) {
            Activity act = (Activity) mContext;
            inflater = act.getLayoutInflater();
            v = inflater.inflate(R.layout.toast_specialnotification, null);
        } else {
//            same
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.toast_specialnotification, null);
        }
        ImageView image = (ImageView) v.findViewById(R.id.mainImage);
        TextView mainText = (TextView) v.findViewById(R.id.mainText);
        TextView subText = (TextView) v.findViewById(R.id.subText);

        mainText.setText(mText);
        subText.setText(sText);
        show(this, v, duration);
    }

    private void show(Toast toast, View v, int duration) {
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(duration);
        toast.setView(v);
        toast.show();
    }

//androi.tistory.com/103 [안드로이 스토리]
}
