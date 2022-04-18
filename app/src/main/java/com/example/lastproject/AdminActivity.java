package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    Button btn_Add, btn_Control;
    TextView text_logout;
    int color, changeColor;
    Handler mHandler = new Handler();
    String adminID = "andmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //버튼 글씨색 변경을 위한
        color = ContextCompat.getColor(getApplicationContext(), R.color.darkgray);
        changeColor = ContextCompat.getColor(getApplicationContext(), R.color.black);

        btn_Add = (Button) findViewById(R.id.btnAdd);
        btn_Control = (Button) findViewById(R.id.btnControl);

        text_logout = (TextView)findViewById(R.id.textLogout);

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);

                //버튼 클릭시 글씨 색상 검정으로 변경
                btn_Add.setTextColor(changeColor);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_Add.setTextColor(color);
                    }
                },1000);
            }
        });

        btn_Control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdminShopListActivity.class);
                startActivity(intent);

                //버튼 클릭시 글씨 색상 검정으로 변경
                btn_Control.setTextColor(changeColor);

                //5초후 실행행
               mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_Control.setTextColor(color);
                    }
                },1000);
            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}