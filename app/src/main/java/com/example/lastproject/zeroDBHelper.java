package com.example.lastproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class zeroDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "zero.db"; //데이터 베이스 네임

    public zeroDBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user (userID TEXT NOT NULL PRIMARY KEY, userPassword TEXT NOT NULL, userNickname TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE shop (shopID TEXT NOT NULL PRIMARY KEY, shopName TEXT NOT NULL, shopLogo BLOB NOT NULL, shopKategorie TEXT NOT NULL, shopLocation TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE menu (menuID TEXT NOT NULL PRIMARY KEY, menuName TEXT NOT NULL,  menuPrice TEXT NOT NULL, menuImage BLOB NOT NULL, menuInfomation TEXT NOT NULL, menuSize TEXT NOT NULL, menuConcept TEXT NOT NULL, shopID TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shop");
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS menu");
        onCreate(sqLiteDatabase);
    }
}
