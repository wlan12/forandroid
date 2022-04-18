
package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {

    Button btn_All, btn_Korean, btn_Snack, btn_Japanese, btn_Chiness, btn_Midnght;
    String[] shopList_ID, shopList_name, shopList_kategorie, shopList_location;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;

    String kategorie;
    int count;
    byte[][] shopList_image = null;
    Bitmap[] bitmap = null;

    private RecyclerView recyclerView = null;
    private shopListAdapter shopListAdapter = null;
    ArrayList<listData> itemListDate = new ArrayList<listData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Button.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListDate.clear();//리스트 초기화
                switch (view.getId()){
                    case R.id.btnAll :
                        selectAll();
                        itemListDate.clear();//리스트 초기화
                        for(int i = 0; i<count; i++){
                            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                        }
                        shopListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btnKorean :
                        kategorie = btn_Korean.getText().toString();
                        kategori_select(kategorie);

                        for(int i = 0; i<count; i++){
                            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                        }
                        shopListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btnSnack :
                        kategorie = btn_Snack.getText().toString();
                        kategori_select(kategorie);

                        for(int i = 0; i<count; i++){
                            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                        }
                        shopListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btnJapanese :
                        kategorie = btn_Japanese.getText().toString();
                        kategori_select(kategorie);

                        for(int i = 0; i<count; i++){
                            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                        }
                        shopListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btnChinese :
                        kategorie = btn_Chiness.getText().toString();
                        kategori_select(kategorie);

                        for(int i = 0; i<count; i++){
                            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                        }
                        shopListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btnMidnight :
                        kategorie = btn_Midnght.getText().toString();
                        kategori_select(kategorie);

                        for(int i = 0; i<count; i++){
                            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                        }
                        shopListAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        btn_All = (Button)findViewById(R.id.btnAll);
        btn_All.setOnClickListener(onClickListener);
        btn_Korean = (Button)findViewById(R.id.btnKorean);
        btn_Korean.setOnClickListener(onClickListener);
        btn_Snack = (Button)findViewById(R.id.btnSnack);
        btn_Snack.setOnClickListener(onClickListener);
        btn_Japanese = (Button)findViewById(R.id.btnJapanese);
        btn_Japanese.setOnClickListener(onClickListener);
        btn_Chiness = (Button)findViewById(R.id.btnChinese);
        btn_Chiness.setOnClickListener(onClickListener);
        btn_Midnght = (Button)findViewById(R.id.btnMidnight);
        btn_Midnght.setOnClickListener(onClickListener);

        recyclerView = findViewById(R.id.recycler_shopList);

        shopListAdapter = new shopListAdapter(itemListDate);
        recyclerView.setAdapter(shopListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        zeroHelper = new zeroDBHelper(this);

        selectAll();

        itemListDate.clear();//리스트 초기화
        for(int i = 0; i<count; i++){
            itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
        }
        shopListAdapter.notifyDataSetChanged();

    }

    public void selectAll(){
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

    public void kategori_select(String kategorie){
        try{

            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM shop WHERE shopKategorie = '"+kategorie+"';", null);
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