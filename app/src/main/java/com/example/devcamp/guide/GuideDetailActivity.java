package com.example.devcamp.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.devcamp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GuideDetailActivity extends AppCompatActivity {

    Intent intent;
    String tagName;
    TextView guideDetail_tag, guideDetail_question, guideDetail_description, guideDetail_checkListTitle;

    ListView cleansingListView, skincareListView;
    ArrayList<CheckItem> cleansingList, skincareList;


    HashMap<String, String> description_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guide_detail);

        intent = getIntent();
        tagName = intent.getStringExtra("tag");

        // 설명
        description_list = new HashMap<>();

        // 클랜징 리스트
        cleansingList = new ArrayList<>();

        // 스킨케어 리스트
        skincareList = new ArrayList<>();

        fvbi();

        guideDetail_tag.setText(tagName + "");
        guideDetail_question.setText(tagName + "이란?");
        guideDetail_description.setText(description_list.get(tagName));
        guideDetail_checkListTitle.setText(tagName + " 전용 체크리스트");

    }

    public void fvbi(){
        guideDetail_tag = (TextView) findViewById(R.id.guideDetail_tag);
        guideDetail_question = (TextView) findViewById(R.id.guideDetail_question);
        guideDetail_description = (TextView) findViewById(R.id.guideDetail_description);
        guideDetail_checkListTitle = (TextView) findViewById(R.id.guideDetail_checkListTitle);

        cleansingListView = (ListView) findViewById(R.id.cleansingListView);
        skincareListView = (ListView) findViewById(R.id.skincareListView);

        if(tagName.equals("건성")){
            description_list.put("건성", "피부결이 곱고 피지 분비가 적으며, 건조하고 자극에 민감하다.\n" + "주름이 잘 생기고 세안 후에 당기는 느낌이 있다.");
            cleansingList.add(new CheckItem("저자극성의 폼클렌징 하나만 사용하기","클렌징 시 단계가 많을 수록 피부가 손상될 염려가 크기 때문이다."));
            cleansingList.add(new CheckItem("미온수로 먼저 세안을 한 뒤 클렌징하기", "미온수로 인해 모공이 살짝 열려 노폐물 제거가 원활하게 이뤄질 수 있다."));
            cleansingList.add(new CheckItem("수분 함량이 높은 클렌징 제품 사용하기","피부가 건조하므로 가급적 촉촉한 타입의 클렌징 제품을 이용하면 건조함을 줄일 수 있다."));
            skincareList.add(new CheckItem("눈가, 입가 등은 아이크림 사용", "눈가와 입가는 피부가 얇아 주름이 생기기 쉬우므로, 아이크림으로 한 번 더 영양을 공급해준다."));
            skincareList.add(new CheckItem("세안 후 마스크팩이나 영양 크림 사용","각질층이 얇고 피부 유수분도가 낮은 건성 피부에는 영양 공급이 중요하다."));
            skincareList.add(new CheckItem("볼, 이마 등의 부위에 부분적으로 고보습 크림 사용", "건성 피부는 유달리 건조한 부위가 있으므로, 부분적으로 고보습을 더해주어 피부의 유수분도를 조절한다."));
        }

        else if(tagName.equals("지성")){
            description_list.put("지성", "피부결이 거칠고 피지 분비가 많아 피부 표층이 기름져 보인다.\n" + "외부 자극에 대한 저항력은 강하나 여드름 등이 쉽게 생긴다.");
            cleansingList.add(new CheckItem("이중 세안하기","모공 속 잔여 노폐물이 다른 타입보다 과다생성되기 때문에, 이중 세안을 통해 모공 속까지 깨끗하게 지워주는 것이 중요하다."));
            cleansingList.add(new CheckItem("1~2분 내로 피지를 잡아주는 제품을 사용","지성 피부는 너무 오랫동안 세안을 하게 되면 노폐물이 다시 모공 속에 쌓일 수 있어, 빠르게 세안을 해주어야 한다."));
            cleansingList.add(new CheckItem("AHA 및 BHA 성분 클렌징 제품 사용","지성 피부는 노폐물로 인한 트러블이 많이 생기므로, AHA와 BHA 성분으로 노폐물을 효과적으로 제거해 트러블을 방지한다."));
            skincareList.add(new CheckItem("저자극 스크럽제로 각질을 관리","지성 피부는 피지 분비가 많기 때문에 각질을 관리해주는 것이 중요하다."));
            skincareList.add(new CheckItem("가볍고 흡수력이 높은 수분 크림 사용","지성 피부의 유수분 밸런스를 적정하게 조절할 수 있다.\n"));
            skincareList.add(new CheckItem("쿨링 기능이 있는 마스크팩 사용","여드름 등이 잘 생기는 지성 피부에 진정 효과를 제공한다."));
        }

        else if(tagName.equals("복합성")){
            description_list.put("복합성", "일반적으로 T존 (이마, 코)은 피지분비가 많은 지성 타입이고, U존 (볼, 턱)은 컨성 타입으로 얼굴에 두 가지 피부 타입이 공존한다.");
            cleansingList.add(new CheckItem("자극이 적은 클렌징","수분이 없어 민감한 U존부위에 자극을 주게 되면 위험"));
            cleansingList.add(new CheckItem("U존과 T존을 구분하여 클렌징", "U존에는 클렌징 크림이나 오일, T존에는 클렌징 젤이나 워터를 사용"));
            cleansingList.add(new CheckItem("립앤 아이 리무버 사용","U존과 T존은 구분하여 클렌징하므로 포인트 메이크업은 전용 리무버 사용"));
            skincareList.add(new CheckItem("토너로 꼭 정리해주기","두가지 피부 타입이 공존하는 복합성은 토너로 꼭 피부결 정리"));
            skincareList.add(new CheckItem("로션 꼭 사용하기 (유수분 벨런스)", "복합성 피부는 유수분 밸런스를 맞추기 위해 로션이 필수"));
            skincareList.add(new CheckItem("모공 관리 제품 사용", "모공이 상대적으로 관리하기 힘든 복합성은 관련 제품을 써주는 것이 좋음"));
            skincareList.add(new CheckItem("건조한 부분에만 보습 크림 덧 바르기", "T존은 수분 위주, U존에는 보습을 한번더 발라서 건조함 개선"));
        }

        else if(tagName.equals("민감성")){
            description_list.put("민감성", "자극에 예민하게 반응하는 피부형으로 화장품에 의한 알레르기 접촉피부염이나 자극 접촉 피부염을 일으켜 잦은 피부트러블이 일어난다.");
            cleansingList.add(new CheckItem("자극이 적은 클렌징 제품 사용하기","과도한 클렌징은 트러블을 일으켜요"));
            cleansingList.add(new CheckItem("알레르기 테스트를 거친 제품인지 확인하기", "특정성분은 피부를 자극시키기 때문에 위험해요"));
            skincareList.add(new CheckItem("스킨은 알코올 프리 제품 사용하기","알코올은 탈지기능 때문에 피부가 위험해요"));
            skincareList.add(new CheckItem("밀크클렌징제품 사용하기","폼타입의 세안제는 피부를 더욱 건조하게 해요"));
            skincareList.add(new CheckItem("수분 보습 충분히 해주기","피부가 건조한 만큼 자극 가능성이 늘어나요"));
            skincareList.add(new CheckItem("피부자극 완화제품 선택하기","민감성 피부는 화장품 선택할 때 주의해야해요"));
        }

        cleansingListView.setAdapter(new GuideCheckListAdapter(GuideDetailActivity.this, R.layout.item_guidecheck, cleansingList));
        skincareListView.setAdapter(new GuideCheckListAdapter(GuideDetailActivity.this, R.layout.item_guidecheck, skincareList));
    }
}