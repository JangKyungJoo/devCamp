package com.example.devcamp.guide;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devcamp.R;

/**
 * Created by ridickle on 2017. 3. 28..
 */

public class GuideDialog extends Dialog {

    Button closeBt;
    TextView guideDialog_title, guideDialog_body;
    CheckItem checkItem;

    public GuideDialog(@NonNull Context context, int resId, CheckItem checkItem) {
        super(context, resId);
        this.checkItem = checkItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 메인 layout
        setContentView(R.layout.dialog_guide);

        closeBt = (Button) findViewById(R.id.listview_bt);
        guideDialog_title = (TextView) findViewById(R.id.guideDialog_title);
        guideDialog_body = (TextView) findViewById(R.id.guideDialog_body);

        // 제목 설정
        guideDialog_title.setText(checkItem.getTitle());
        // 내용 설정
        guideDialog_body.setText(checkItem.getDescription());

        // 버튼 리스너 설정
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
