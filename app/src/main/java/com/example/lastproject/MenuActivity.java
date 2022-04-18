package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MenuActivity extends AppCompatActivity {

    TextView user_menuTop, user_menuName, user_menuInfo, user_menuPrice, user_menuSize, user_menuCon;
    ImageView image_menuBack, user_menuImage;

    String intent_menuName, menu_ID, menu_Info, menu_price, menu_size, menu_con, shopID;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;

    byte[] menu_image = null;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        intent_menuName = intent.getExtras().getString("menuName");

        user_menuTop = (TextView)findViewById(R.id.userMenuTop);
        user_menuName = (TextView)findViewById(R.id.userMenuName);
        user_menuInfo = (TextView)findViewById(R.id.userMenuInfo);
        user_menuPrice = (TextView)findViewById(R.id.userMenuPrice);
        user_menuSize = (TextView)findViewById(R.id.userMenuSize);
        user_menuCon = (TextView)findViewById(R.id.userMenuCon);
        image_menuBack = (ImageView)findViewById(R.id.imgMenuBack);
        user_menuImage = (ImageView)findViewById(R.id.userMenuImage);

        zeroHelper = new zeroDBHelper(this);

        selectMenu(intent_menuName);
    }

    public void selectMenu(String name){
        try{
            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM menu WHERE menuName ='"+name+"' ;", null);

            cursor.moveToNext();

            menu_ID = cursor.getString(0);
            intent_menuName = cursor.getString(1);
            menu_price = cursor.getString(2);
            menu_image = cursor.getBlob(3);
            menu_Info = cursor.getString(4);
            menu_size = cursor.getString(5);
            menu_con = cursor.getString(6);

            bitmap = BitmapFactory.decodeByteArray(menu_image,0,menu_image.length);

            user_menuTop.setText(name);
            user_menuName.setText(name);
            user_menuImage.setImageBitmap(bitmap);
            user_menuInfo.setText(menu_Info);
            user_menuPrice.setText(menu_price);
            user_menuSize.setText(menu_size);
            user_menuCon.setText(menu_con);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "메뉴 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }
    }
}