package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {

    ImageView img_back, image_update;
    EditText edit_updateID, edit_updateName, edit_updateLoca;
    CheckBox[] check = new CheckBox[5];
    Button btn_update, btn_delect;

    String admin_shopID, up_shopID, up_shopName, up_shopKategorie, up_shopLocation, update_sql, delete_sql;
    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;

    final int GET_GALLERY_IMAGE = 200;

    byte[] up_imageLogo = null;
    Uri selectedImageUri;
    Bitmap up_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        img_back = (ImageView)findViewById(R.id.imgBack);
        image_update = (ImageView)findViewById(R.id.imageUpdate);
        edit_updateID = (EditText)findViewById(R.id.editUpdateID);
        edit_updateName = (EditText)findViewById(R.id.editUpdateName);
        edit_updateLoca = (EditText)findViewById(R.id.editUpdateLoca);

        check[0] = (CheckBox)findViewById(R.id.updateKorean);
        check[1] = (CheckBox)findViewById(R.id.updateSnack);
        check[2] = (CheckBox)findViewById(R.id.updateJapanese);
        check[3] = (CheckBox)findViewById(R.id.updateChinese);
        check[4] = (CheckBox)findViewById(R.id.updateMidnight);

        btn_update = (Button)findViewById(R.id.btnShopUpdate);
        btn_delect = (Button)findViewById(R.id.btnShopDelect);

        Intent intent = getIntent();
        admin_shopID = intent.getExtras().getString("shopID");

        zeroHelper = new zeroDBHelper(this);

        try{
            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM shop WHERE shopID ='"+admin_shopID+"' ;", null);

            cursor.moveToNext();

            up_shopID = cursor.getString(0);
            up_shopName = cursor.getString(1);
            up_imageLogo = cursor.getBlob(2);
            up_shopKategorie = cursor.getString(3);
            up_shopLocation = cursor.getString(4);

            up_bitmap = BitmapFactory.decodeByteArray(up_imageLogo,0,up_imageLogo.length);

            switch (up_shopKategorie){
                case "한식" : check[0].setChecked(true); break;
                case "분식" : check[1].setChecked(true); break;
                case "일식" : check[2].setChecked(true); break;
                case "중식" : check[3].setChecked(true); break;
                case "야식" : check[4].setChecked(true); break;
            }

            edit_updateID.setText(up_shopID);
            edit_updateID.setClickable(false);
            edit_updateID.setFocusable(false);

            edit_updateName.setText(up_shopName);
            image_update.setImageBitmap(up_bitmap);
            edit_updateLoca.setText(up_shopLocation);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "가게 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }

        image_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(imageIntent, GET_GALLERY_IMAGE);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_shopKategorie = "";

                up_shopName = edit_updateName.getText().toString();
                up_shopLocation = edit_updateLoca.getText().toString();
                for (int i = 0; i < check.length; i++) {
                    if (check[i].isChecked() == true) {
                        up_shopKategorie += check[i].getText().toString();
                    }
                }

                AlertDialog.Builder dlg = new AlertDialog.Builder(UpdateActivity.this);
                dlg.setTitle("변경 : "+up_shopID);
                dlg.setMessage(up_shopName + "\n" + up_shopKategorie + "\n" + up_shopLocation );
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            sqlDB = zeroHelper.getWritableDatabase();
                            update_sql = "UPDATE shop SET shopName = ?, shopLogo = ?, shopKategorie = ?, shopLocation = ? WHERE shopID = ?;";
                            SQLiteStatement updateStmt = sqlDB.compileStatement(update_sql);

                            updateStmt.clearBindings();
                            updateStmt.bindString(1, up_shopName);
                            updateStmt.bindBlob(2, up_imageLogo);
                            updateStmt.bindString(3, up_shopKategorie);
                            updateStmt.bindString(4, up_shopLocation);
                            updateStmt.bindString(5, up_shopID);
                            updateStmt.execute();

                            sqlDB.close();

                            Toast.makeText(getApplicationContext(), "변경 되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminShopActivity.class);
                            intent.putExtra("shopID",up_shopID);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dlg.show();
            }
        });

        btn_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(UpdateActivity.this);
                dlg.setTitle("삭제 : "+up_shopID);
                dlg.setMessage(up_shopName + "\n" + up_shopKategorie + "\n" + up_shopLocation );
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            sqlDB = zeroHelper.getWritableDatabase();
                            delete_sql = "DELETE FROM shop WHERE shopID = ?;";
                            SQLiteStatement deleteStmt = sqlDB.compileStatement(delete_sql);

                            deleteStmt.clearBindings();
                            deleteStmt.bindString(1,up_shopID);
                            deleteStmt.execute();
                            sqlDB.close();

                            Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();

                            Intent deleteIntent = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(deleteIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dlg.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            try{
                selectedImageUri = data.getData();
                InputStream is = getContentResolver().openInputStream(selectedImageUri);
                up_imageLogo = getBytes(is);
                image_update.setImageURI(selectedImageUri);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len=is.read(buffer))!=-1){
            byteBuffer.write(buffer,0,len);
        }
        return byteBuffer.toByteArray();
    }
}