package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class AdminShopActivity extends AppCompatActivity {

    Button btn_updata, btn_insertMenu;
    ImageView img_Back, image_adminShop;
    TextView admin_shopName, admin_shopLoca;

    String admin_shopID, adminShop_Name, adminShop_Loca;
    String[] menuList_ID, menuList_name, menuList_price;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;
    int count, i;

    byte[] adminShop_image = null;
    byte[][] menuList_image = null;
    Bitmap[] menuBitmap = null;
    Bitmap bitmap;

    private RecyclerView adminRecyclerView = null;
    private static adminMenuListAdapter adminMenuListAdapter = null;
    ArrayList<adminData> itemAdminData = new ArrayList<adminData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shop);

        btn_updata = (Button)findViewById(R.id.btnUpdata);
        img_Back = (ImageView)findViewById(R.id.imgBack);
        image_adminShop = (ImageView)findViewById(R.id.imageAdminShop);
        admin_shopName = (TextView)findViewById(R.id.adminShopName);
        admin_shopLoca = (TextView)findViewById(R.id.adminShopLoca);
        btn_insertMenu = (Button)findViewById(R.id.btnIsertMenu);

        zeroHelper = new zeroDBHelper(this);

        Intent intent = getIntent();
        admin_shopID = intent.getExtras().getString("shopID");

        adminRecyclerView = (RecyclerView) findViewById(R.id.recycler_adminMenuList);

        adminMenuListAdapter = new adminMenuListAdapter(itemAdminData);
        adminRecyclerView.setAdapter(adminMenuListAdapter);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adminSelectShop(admin_shopID);
        adminSelectMenu(admin_shopID);

        itemAdminData.clear();//리스트 초기화
        for(int i = 0; i<count; i++){
            itemAdminData.add(new adminData(menuBitmap[i], menuList_ID[i], menuList_name[i],menuList_price[i]));
        }
        adminMenuListAdapter.notifyDataSetChanged();

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_insertMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMenuActivity.class);
                intent.putExtra("shopID",admin_shopID);
                startActivity(intent);
            }
        });

        btn_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("shopID",admin_shopID);
                startActivity(intent);
            }
        });
    }

    public void adminSelectShop(String id){
        try{

            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT shopName, shopLogo, shopLocation FROM shop WHERE shopID = '"+id+"';", null);

            while (cursor.moveToNext()){
                adminShop_Name = cursor.getString(0);
                adminShop_image = cursor.getBlob(1);
                adminShop_Loca = cursor.getString(2);

                bitmap = BitmapFactory.decodeByteArray(adminShop_image,0,adminShop_image.length);
            }

            admin_shopName.setText(adminShop_Name);
            image_adminShop.setImageBitmap(bitmap);
            admin_shopLoca.setText(adminShop_Loca);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "가게 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }
    }

    public void adminSelectMenu(String id){
        try{
            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT menuID, menuName, menuPrice, menuImage FROM menu WHERE shopID ='"+id+"' ;", null);

            count = cursor.getCount();

            menuList_ID = new String[count];
            menuList_name = new String[count];
            menuList_price = new String[count];
            menuList_image = new byte[count][];
            menuBitmap = new Bitmap[count];

            i = 0;
            while (cursor.moveToNext()) {
                menuList_ID[i] = cursor.getString(0);
                menuList_name[i] = cursor.getString(1);
                menuList_price[i] = cursor.getString(2);
                menuList_image[i] = cursor.getBlob(3);

                menuBitmap[i] = BitmapFactory.decodeByteArray(menuList_image[i],0,menuList_image[i].length);
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "메뉴 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }
    }
}