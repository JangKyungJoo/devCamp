package com.example.devcamp.setting;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcamp.util.CleansingListDBHelper;
import com.example.devcamp.entity.CleansingList;
import com.example.devcamp.MainActivity;
import com.example.devcamp.R;
import com.example.devcamp.util.User;

import java.util.ArrayList;

import static com.example.devcamp.entity.CleansingList.TABLE_NAME;

public class CleansingActivity extends AppCompatActivity{
    ArrayList<CleansingList> list;
    CleansingListDBHelper cleansingListDBHelper;
    FrameLayout saveBtn, cleansing1, cleansing2, cleansing3, cleansing4, addCleansing;
    TextView textView1, textView2, textView3, textView4;
    ImageView updateItem1, updateItem2, updateItem3, updateItem4;
    Dialog mMainDialog;
    final int UPDATE = 1;
    final int ADD = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_cleansing);

        cleansing1 = (FrameLayout) findViewById(R.id.cleansingList1);
        cleansing2 = (FrameLayout) findViewById(R.id.cleansingList2);
        cleansing3 = (FrameLayout) findViewById(R.id.cleansingList3);
        cleansing4 = (FrameLayout) findViewById(R.id.cleansingList4);

        textView1 = (TextView) findViewById(R.id.cleansing1_text1);
        textView2 = (TextView) findViewById(R.id.cleansing1_text2);
        textView3 = (TextView) findViewById(R.id.cleansing1_text3);
        textView4 = (TextView) findViewById(R.id.cleansing1_text4);

        updateItem1 = (ImageView) findViewById(R.id.update1);
        updateItem2 = (ImageView) findViewById(R.id.update2);
        updateItem3 = (ImageView) findViewById(R.id.update3);
        updateItem4 = (ImageView) findViewById(R.id.update4);

        cleansingListDBHelper = new CleansingListDBHelper(this);

        saveBtn = (FrameLayout) findViewById(R.id.save_cleansing_list);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size() > 0) {
                    SQLiteDatabase db = cleansingListDBHelper.getWritableDatabase();
                    db.delete(TABLE_NAME, null, null);

                    for (int i = 0; i < list.size(); i++) {
                        ContentValues row = new ContentValues();
                        row.put("item", list.get(i).getItem());
                        db.insert(TABLE_NAME, null, row);
                    }

                    Toast.makeText(getApplicationContext(), "Save Success!", Toast.LENGTH_SHORT).show();
                    User.setStartDate(getApplicationContext(), MainActivity.nowDate);
                    User.saveLastUpdateDate(getApplicationContext(), MainActivity.nowDate);
                    cleansingListDBHelper.close();
                }
                finish();
            }
        });

        list = User.getCleansingList(getApplicationContext());
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                showLayout(i);
            }
        }

        addCleansing = (FrameLayout) findViewById(R.id.add_cleansinglist);
        addCleansing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() > 3) {
                    Toast.makeText(getApplicationContext(), "MAX NUM is 4", Toast.LENGTH_SHORT).show();
                }else {
                    mMainDialog = createDialog("", "" + list.size(), ADD);
                    mMainDialog.show();
                }
            }
        });

        updateItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainDialog = createDialog(textView1.getText().toString(), "" + 0, UPDATE);
                mMainDialog.show();
            }
        });

        updateItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainDialog = createDialog(textView2.getText().toString(), "" + 1, UPDATE);
                mMainDialog.show();
            }
        });

        updateItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainDialog = createDialog(textView3.getText().toString(), "" + 2, UPDATE);
                mMainDialog.show();
            }
        });

        updateItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainDialog = createDialog(textView4.getText().toString(), "" + 3, UPDATE);
                mMainDialog.show();
            }
        });

    }

    private AlertDialog createDialog(String text, final String index, final int type) {
        final View innerView = getLayoutInflater().inflate(R.layout.layout_add_checklist_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final EditText editText = (EditText) innerView.findViewById(R.id.checklist_edittext);
        editText.setText(text);
        editText.setSelection(text.length());

        FrameLayout dialogSaveBtn = (FrameLayout) innerView.findViewById(R.id.checklist_dialog_save);
        dialogSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idx = Integer.parseInt(index);

                if(type == ADD) {
                    list.add(new CleansingList(0, editText.getText().toString()));
                }else{
                    list.set(idx, new CleansingList(list.get(idx).get_id(), editText.getText().toString()));
                }

                showLayout(idx);
                setDismiss(mMainDialog);
            }
        });
        ab.setView(innerView);
        return ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

    public void showLayout(int i){
        switch (i) {
            case 0:
                cleansing1.setVisibility(View.VISIBLE);
                textView1.setText(list.get(i).getItem().toString());
                break;
            case 1:
                cleansing2.setVisibility(View.VISIBLE);
                textView2.setText(list.get(i).getItem().toString());
                break;
            case 2:
                cleansing3.setVisibility(View.VISIBLE);
                textView3.setText(list.get(i).getItem().toString());
                break;
            case 3:
                cleansing4.setVisibility(View.VISIBLE);
                textView4.setText(list.get(i).getItem().toString());
                break;
            default:
                break;
        }
    }
}
