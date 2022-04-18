package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminMenuActivity extends AppCompatActivity {

    Button btn_MenuUpdate;
    TextView adminMenu_name, adminMenu_info, adminMenu_price, adminMenu_size, adminMenu_Con;
    ImageView image_adminBack, image_AdminMenu;

    String admin_MenuID, admin_menuName, admin_menuPice, admin_menuInfo, admin_MenuSize, admin_MenuCon, admin_shopID;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;

    byte[] adminMenu_image = null;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        image_adminBack = (ImageView)findViewById(R.id.imgMenuBack);
        btn_MenuUpdate = (Button)findViewById(R.id.btnMenuUpdata);
        image_AdminMenu = (ImageView)findViewById(R.id.imageAdminMenu);
        adminMenu_name = (TextView)findViewById(R.id.adminMenuName);
        adminMenu_info = (TextView)findViewById(R.id.adminMenuInfo);
        adminMenu_price = (TextView)findViewById(R.id.adminMenuPrice);
        adminMenu_size = (TextView)findViewById(R.id.adminMenuSize);
        adminMenu_Con = (TextView)findViewById(R.id.adminMenuCon);

        zeroHelper = new zeroDBHelper(this);

        Intent intent = getIntent();
        admin_MenuID = intent.getExtras().getString("menuID");

        try{

            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM menu WHERE menuID = '"+admin_MenuID+"';", null);

            while (cursor.moveToNext()){
                admin_MenuID = cursor.getString(0);
                admin_menuName = cursor.getString(1);
                admin_menuPice = cursor.getString(2);
                adminMenu_image = cursor.getBlob(3);
                admin_menuInfo = cursor.getString(4);
                admin_MenuSize = cursor.getString(5);
                admin_MenuCon = cursor.getString(6);
                admin_shopID = cursor.getString(7);


                bitmap = BitmapFactory.decodeByteArray(adminMenu_image,0,adminMenu_image.length);
            }

            image_AdminMenu.setImageBitmap(bitmap);
            adminMenu_name.setText(admin_menuName);
            adminMenu_info.setText(admin_menuInfo);
            adminMenu_price.setText(admin_menuPice);
            adminMenu_size.setText(admin_MenuSize);
            adminMenu_Con.setText(admin_MenuCon);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "메뉴 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }

        image_adminBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminShopActivity.class);
                intent.putExtra("shopID", admin_shopID);
                startActivity(intent);
            }
        });

        btn_MenuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateMenuActivity.class);
                intent.putExtra("menuID",admin_MenuID);
                startActivity(intent);
            }
        });

    }
}