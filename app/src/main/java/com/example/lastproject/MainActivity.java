package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_Shop, btn_My, btn_Select, btn_Home;
    ImageView imgView_My, imgView_Select;
    TextView text_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Shop = (Button)findViewById(R.id.btnShop);
        imgView_My = (ImageView)findViewById(R.id.ImgMy);
        btn_My = (Button)findViewById(R.id.btnMy);
        imgView_Select = (ImageView)findViewById(R.id.imgSelect);
        btn_Select = (Button)findViewById(R.id.btnSelect);
        btn_Home = (Button)findViewById(R.id.btnHome);
        text_select = (TextView)findViewById(R.id.text_select);

        btn_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShopListActivity.class);
                startActivity(intent);
            }
        });

        imgView_My.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                startActivity(intent);
            }
        });

        btn_My.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                startActivity(intent);
            }
        });

        text_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectActivity.class);
                startActivity(intent);
            }
        });

        imgView_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectActivity.class);
                startActivity(intent);
            }
        });

        btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectActivity.class);
                startActivity(intent);
            }
        });

        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}