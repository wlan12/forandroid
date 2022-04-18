package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {

    EditText edit_selet;
    ImageView image_select;
    String editSelet, sql;
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
        setContentView(R.layout.activity_select);

        edit_selet = (EditText)findViewById(R.id.editSelet);
        image_select = (ImageView)findViewById(R.id.imageSelect);

        recyclerView = findViewById(R.id.recycler_shopSelect);

        shopListAdapter = new shopListAdapter(itemListDate);
        recyclerView.setAdapter(shopListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        zeroHelper = new zeroDBHelper(this);

        image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSelet = edit_selet.getText().toString();

                if(editSelet.length()==0){
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                   try{
                       sqlDB = zeroHelper.getReadableDatabase();
                       sql = "SELECT * FROM shop WHERE shopName LIKE ?;";
                       cursor = sqlDB.rawQuery(sql, null);
                       SQLiteStatement selectStmt = sqlDB.compileStatement(sql);

                       selectStmt.clearBindings();
                       selectStmt.bindString(1, "%"+editSelet+"%");

                       selectStmt.execute();

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
                       Toast.makeText(getApplicationContext(), "검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
                   }

                    itemListDate.clear();//리스트 초기화
                    for(int i = 0; i<count; i++){
                        itemListDate.add(new listData(bitmap[i], shopList_name[i],shopList_location[i]));
                    }
                    shopListAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}