package com.example.devcamp.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.devcamp.R;
import com.example.devcamp.util.Special;

import org.json.JSONArray;

public class SpecialSettingActivity extends AppCompatActivity {

    private LinearLayout careView;
    private LinearLayout periodView;
    private LinearLayout careViewClick;
    private LinearLayout periodViewClick;
    private RadioGroup rg;
    private RadioButton rb;
    private RadioGroup dayRadioGroup;
    private RadioButton dayRadioButton;

    private View careBottomLine;
    private View periodBottomLine;
    private View periodTopLine;

    private TextView tvSunday;
    private TextView tvMonday;
    private TextView tvTuesday;
    private TextView tvWednesday;
    private TextView tvThursday;
    private TextView tvFriday;
    private TextView tvSaturday;
    private View addSpecialButton;
    private String clickedDay = "일";

    private ImageView icon_group_care;
    private ImageView icon_group_period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_setting);
        rg = (RadioGroup)findViewById(R.id.radioGroup1);

        ((RadioButton)rg.getChildAt(0)).setChecked(true);

        careView = (LinearLayout) findViewById(R.id.careView);
        careViewClick = (LinearLayout) findViewById(R.id.careViewClick);
        periodView = (LinearLayout) findViewById(R.id.periodView);
        periodViewClick = (LinearLayout) findViewById(R.id.periodViewClick);
        careBottomLine = findViewById(R.id.careBottomLine);
        periodBottomLine = findViewById(R.id.periodBottomLine);
        addSpecialButton = findViewById(R.id.addSpecialButton);

        dayRadioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        ((RadioButton)dayRadioGroup.getChildAt(0)).setChecked(true); //초기값 설정
        tvSunday = (TextView)findViewById(R.id.txSunday);
        tvMonday = (TextView)findViewById(R.id.txMonday);
        tvTuesday = (TextView)findViewById(R.id.txTuesday);
        tvWednesday = (TextView)findViewById(R.id.txWednesday);
        tvThursday = (TextView)findViewById(R.id.txThursday);
        tvFriday = (TextView)findViewById(R.id.txFriday);
        tvSaturday = (TextView)findViewById(R.id.txSaturday);

        icon_group_care = (ImageView)findViewById(R.id.icon_group_care);
        icon_group_period = (ImageView)findViewById(R.id.icon_group_period);

        careViewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(careView.getVisibility()==View.VISIBLE){
                    careView.setVisibility(View.GONE);
                    periodView.setVisibility(View.VISIBLE);
                    careBottomLine.setVisibility(View.GONE);
                    periodBottomLine.setVisibility(View.VISIBLE);
                    icon_group_care.setImageResource(R.drawable.specialcare_icon_03_arrow);
                    icon_group_period.setImageResource(R.drawable.specialcare_icon_02_arrow);
                }else{
                    careView.setVisibility(View.VISIBLE);
                    periodView.setVisibility(View.GONE);
                    careBottomLine.setVisibility(View.VISIBLE);
                    periodBottomLine.setVisibility(View.GONE);
                    icon_group_care.setImageResource(R.drawable.specialcare_icon_02_arrow);
                    icon_group_period.setImageResource(R.drawable.specialcare_icon_03_arrow);
                }
            }
        });
        periodViewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(periodView.getVisibility()==View.VISIBLE){
                    periodView.setVisibility(View.GONE);
                    careView.setVisibility(View.VISIBLE);
                    careBottomLine.setVisibility(View.VISIBLE);
                    periodBottomLine.setVisibility(View.GONE);
                    icon_group_care.setImageResource(R.drawable.specialcare_icon_02_arrow);
                    icon_group_period.setImageResource(R.drawable.specialcare_icon_03_arrow);
                }else{
                    periodView.setVisibility(View.VISIBLE);
                    careView.setVisibility(View.GONE);
                    careBottomLine.setVisibility(View.GONE);
                    periodBottomLine.setVisibility(View.VISIBLE);
                    icon_group_care.setImageResource(R.drawable.specialcare_icon_03_arrow);
                    icon_group_period.setImageResource(R.drawable.specialcare_icon_02_arrow);
                }
            }
        });
        addSpecialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayRadioButton = (RadioButton) findViewById(dayRadioGroup.getCheckedRadioButtonId());
                rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                JSONArray jarr = new JSONArray();
                jarr.put(clickedDay);
                jarr.put(dayRadioButton.getText().toString());
                Special.saveSpecialName(getApplicationContext(), Special.SPECIALCARE_NAME, rb.getText().toString(), jarr.toString());
                Intent backadd = new Intent(SpecialSettingActivity.this, SpecialActivity.class);
                startActivity(backadd);
                finish();
            }
        });
        //String str_Qtype = rb.getText().toString();
    }
    public void onClick(View v){
        switch(v.getId()){

            case R.id.txSunday:
                changeColor();
                tvSunday.setBackgroundColor(Color.parseColor("#7753c6"));
               clickedDay = "일";
                break;
            case R.id.txMonday:
                changeColor();
                tvMonday.setBackgroundColor(Color.parseColor("#7753c6"));
               clickedDay = "월";
                break;
            case R.id.txTuesday:
                changeColor();
                tvTuesday.setBackgroundColor(Color.parseColor("#7753c6"));
                clickedDay = "화";
                break;
            case R.id.txWednesday:
                changeColor();
                tvWednesday.setBackgroundColor(Color.parseColor("#7753c6"));
                clickedDay = "수";
                break;
            case R.id.txThursday:
                changeColor();
                tvThursday.setBackgroundColor(Color.parseColor("#7753c6"));
                clickedDay = "목";
                break;
            case R.id.txFriday:
                changeColor();
                tvFriday.setBackgroundColor(Color.parseColor("#7753c6"));
                clickedDay = "금";
                break;
            case R.id.txSaturday:
                changeColor();
                tvSaturday.setBackgroundColor(Color.parseColor("#7753c6"));
                clickedDay = "토";
                break;
        }
    }
    private void changeColor(){
        tvSunday.setBackgroundColor(Color.parseColor("#8564CC"));
        tvMonday.setBackgroundColor(Color.parseColor("#886CD3"));
        tvTuesday.setBackgroundColor(Color.parseColor("#8872D6"));
        tvWednesday.setBackgroundColor(Color.parseColor("#8979D8"));
        tvThursday.setBackgroundColor(Color.parseColor("#8F85DD"));
        tvFriday.setBackgroundColor(Color.parseColor("#928FE2"));
        tvSaturday.setBackgroundColor(Color.parseColor("#9898E5"));
    }
}
