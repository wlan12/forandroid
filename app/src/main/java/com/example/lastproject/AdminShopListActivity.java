package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminShopListActivity extends AppCompatActivity {

    String[] shopList_ID, shopList_name, shopList_kategorie, shopList_location;

    ImageView image_adminShopList;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;

    int count;
    byte[][] shopList_image = null;
    Bitmap[] bitmap = null;

    private RecyclerView recyclerView = null;
    private static adminShopListAdapter adminShopListAdapter = null;
    ArrayList<adminData> itemAdminData = new ArrayList<adminData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shop_list);

        image_adminShopList = (ImageView)findViewById(R.id.imageAdminShopList);

        recyclerView = findViewById(R.id.recycler_adminShopList);

        adminShopListAdapter = new adminShopListAdapter(itemAdminData);
        recyclerView.setAdapter(adminShopListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        zeroHelper = new zeroDBHelper(this);

        select();

        itemAdminData.clear();//리스트 초기화
        for(int i = 0; i<count; i++){
            itemAdminData.add(new adminData(bitmap[i], shopList_ID[i], shopList_name[i],shopList_location[i]));
        }
        adminShopListAdapter.notifyDataSetChanged();

        image_adminShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void select(){
        try{

            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM shop;", null);
            count = cursor.getCount();

            shopList_ID = new String[count];
            shopList_name = new String[count];
            shopList_image = new byte[count][];
            shopList_kategorie = new String[count];
            shopList_location = new String[count];
            bitmap = new Bitmap[count];

            int i = 0;
            while (cursor.moveToNext()) {
                shopList_ID[i] = cursor.getString(0);
                shopList_name[i] = cursor.getString(1);
                shopList_image[i] = cursor.getBlob(2);
                shopList_kategorie[i] = cursor.getString(3);
                shopList_location[i] = cursor.getString(4);

                bitmap[i] = BitmapFactory.decodeByteArray(shopList_image[i],0,shopList_image[i].length);
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "가게 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}