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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    TextView user_shopTop, user_shopName, user_shopLoca;
    ImageView image_shopBack, user_shopImage;

    String intent_shopName, shop_shopID, shop_shopLoca;
    String[] menuList_name, menuList_price;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;
    int count, i;

    byte[] shop_image = null;
    byte[][] menuList_image = null;
    Bitmap[] menuBitmap = null;
    Bitmap shopBitmap;

    private RecyclerView recyclerView = null;
    private static menuListAdapter menuListAdapter = null;
    ArrayList<listData> itemListDate = new ArrayList<listData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        intent_shopName = intent.getExtras().getString("shopName");

        user_shopTop = (TextView)findViewById(R.id.userShopTop);
        user_shopName = (TextView)findViewById(R.id.userShopName);
        user_shopLoca = (TextView)findViewById(R.id.userShopLoca);
        image_shopBack = (ImageView)findViewById(R.id.imageShopBack);
        user_shopImage = (ImageView)findViewById(R.id.userShopImage);

        recyclerView = findViewById(R.id.recycler_menuList);

        menuListAdapter = new menuListAdapter(itemListDate);
        recyclerView.setAdapter(menuListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        zeroHelper = new zeroDBHelper(this);

        image_shopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopListActivity.class);
                startActivity(intent);
            }
        });

        selectShop(intent_shopName);
        selectMenu(shop_shopID);

        itemListDate.clear();//리스트 초기화
        for(int i = 0; i<count; i++){
            itemListDate.add(new listData(menuBitmap[i],menuList_name[i], menuList_price[i]));
        }
        menuListAdapter.notifyDataSetChanged();
    }

    void selectShop(String name){
        try{
            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT shopID, shopLogo, shopLocation FROM shop WHERE shopName ='"+name+"' ;", null);

            cursor.moveToNext();

            shop_shopID = cursor.getString(0);
            shop_image = cursor.getBlob(1);
            shop_shopLoca = cursor.getString(2);

            shopBitmap = BitmapFactory.decodeByteArray(shop_image,0,shop_image.length);

            user_shopTop.setText(name);
            user_shopName.setText(name);
            user_shopImage.setImageBitmap(shopBitmap);
            user_shopLoca.setText(shop_shopLoca);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "가게 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }
    }

    void selectMenu(String id){
        try{
            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT menuName, menuPrice, menuImage FROM menu WHERE shopID ='"+id+"' ;", null);

            count = cursor.getCount();

            menuList_name = new String[count];
            menuList_price = new String[count];
            menuList_image = new byte[count][];
            menuBitmap = new Bitmap[count];

            i = 0;
            while (cursor.moveToNext()) {
                menuList_name[i] = cursor.getString(0);
                menuList_price[i] = cursor.getString(1);
                menuList_image[i] = cursor.getBlob(2);

                menuBitmap[i] = BitmapFactory.decodeByteArray(menuList_image[i],0,menuList_image[i].length);
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "메뉴 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }
    }
}