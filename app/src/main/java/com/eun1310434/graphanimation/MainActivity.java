/*=====================================================================
□ Infomation
  ○ Data : 15.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference
     - Do it android app Programming
     - Hello JAVA Programming
     - http://itmining.tistory.com/5

□ Function
  ○ 막대그래프 모양처럼 만들어 애니메이션을 적용
  ○ 소스코드에서 XML 적용하기

□ Study
  ○ 트윈 애니메이션
     - 보여줄 대상을 적절하게 연산한 후 그 결과를 연속적으로 디스플레이

  ○ 트윈 애니메이션을 위한 액션(Action) 정보
     - XML 리소스로 정의하거나 자바 코드에서 직접 객체로 만듬
     - 애니메이션을 위한 XML 파일은[/res/anim] 폴더의 밑에 두어야 하며 확장자를 xml로 함
     - 리소스로 포함된 애니메이션 액션 정의는 다른 리소스와 마찬가지로 필드할 때 컴파일되어 설치 파일에 포함

  ○ 대상
     - 뷰
       01) View는 위젯이나 레이아웃을 모두 포함
       02) 텍스트뷰나 리니어 레이아웃에 애니메이션을 적용할 수 있음
     - 그리기 객체
       01) 다양한 Drawable에 애니메이션을 적용할 수 있음
       02) ShaperDrawable은 캔버스에 그릴 도형을 지정할 수 있음
       03) BitmapDrawable은 비트맵 이미지를 지정할 수 있음

  ○ 효과
     - 위치이동 : Translate
     - 확대/축소 : Scale
     - 회전 : Rotate
     - 투명도 : Alpha

  ○ 인터폴레이터
     - accelerate_interpolator : 점점 빠르게 나타나도록 만듬
     - decelerate_interpolator : 점점 느리게 나타나도록 만듬
     - accelerate_decelerate_interpolator  : 점점 빠르다가 느리게 나타나도록 만듬
     - anticipate_interpolator : 시작 위치에서 조금 뒤로 당겼다가 시작하도록 만듬
     - overshoot_interpolator : 애니메이션 효과를 종료 위치에서 조금 지나쳤다가 종료되도록 만듬
=====================================================================*/
package com.eun1310434.graphanimation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout buttonLayout;
    LinearLayout mainLayout;
    Animation growAnim;
    int itemID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLayout = (LinearLayout)findViewById(R.id.buttonLayout);
        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        //버튼 추가
        addButton("start");

        // 아이템 추가
        addItem();
    }


    //아이템 추가 메소드
    private void addButton(String name) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        // 버튼 추가
        Button button = new Button(this);
        button.setText(name);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이템 추가
                addItem();
            }
        });

        buttonLayout.addView(button,params);
    }


    //아이템 추가 메소드
    private void addItem() {
        String name = "Item-"+(++itemID);
        int value= (int) (Math.random()*100 + 1);


        /**Wrap Layout*/
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Wrap Layout - Layout Setting
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.gravity = Gravity.LEFT;
        itemParams.setMargins(10, 0, 0, 0);



        /**TextView*/
        TextView textView = new TextView(this);
        textView.setText(name);
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);

        // TextView - Layout Setting
        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewParam.width = 240;
        textViewParam.setMargins(0, 4, 0, 4);

        // ADD - TextView
        itemLayout.addView(textView, textViewParam);//프로그래스바를 params의 레이아웃에 맞춰서 itemLayout에 입력




        /**ProgressBar*/
        ProgressBar proBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        proBar.setIndeterminate(false); //게이지가 올라가는 것이 작업이 완료될때까지 멈추지 않음
        proBar.setMax(100);
        proBar.setProgress(100);
        growAnim = AnimationUtils.loadAnimation(this, R.anim.grow);//XML이 아닌 소스코드로 추가
        proBar.setAnimation(growAnim);

        // ProgressBar - Layout Setting
        LinearLayout.LayoutParams proBarParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        proBarParams.height = 80;
        proBarParams.width = value * 6;
        proBarParams.gravity = Gravity.LEFT;


        // ADD - ProgressBar
        itemLayout.addView(proBar, proBarParams);//프로그래스바를 params2의 레이아웃에 맞춰서 itemLayout에 입력



        /**MainLayout*/
        mainLayout.addView(itemLayout, itemParams);
    }

    //화면에 보여지기 전에 호출되는 메소드
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            growAnim.start(); // growAnim을 설정한 모든 애니메이션을 실행
        } else {
            growAnim.reset();
        }
    }
}
