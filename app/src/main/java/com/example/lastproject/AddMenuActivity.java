package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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

public class AddMenuActivity extends AppCompatActivity {

    ImageView image_addMenuBack, image_menu;
    Button btn_menuInsert;
    EditText edit_menuID, edit_menuName, edit_menuPrice, edit_menuInfo, edit_menuSize, edit_menuCon;
    String shopID, menuID, menuName, menuPrice, menuInfo, menuSize, menuCon, sql;
    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    int ck = 0;

    final int GET_GALLERY_IMAGE = 200;

    byte[] imageMenu = null;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        image_addMenuBack = (ImageView)findViewById(R.id.imageAddMenuBack);
        image_menu = (ImageView)findViewById(R.id.imageMenu);
        btn_menuInsert = (Button)findViewById(R.id.btnMenuInsert);
        edit_menuID = (EditText) findViewById(R.id.editMenuID);
        edit_menuName = (EditText)findViewById(R.id.editMenuName);
        edit_menuPrice = (EditText)findViewById(R.id.editMenuPrice);
        edit_menuInfo = (EditText)findViewById(R.id.editMenuInfo);
        edit_menuSize = (EditText)findViewById(R.id.editMenuSize);
        edit_menuCon = (EditText)findViewById(R.id.editMenuCon);

        Intent intent = getIntent();
        shopID = intent.getExtras().getString("shopID");

        zeroHelper = new zeroDBHelper(this);

        image_addMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminShopActivity.class);
                intent.putExtra("shopID", shopID);
                startActivity(intent);
            }
        });

        image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                ck++;
            }
        });

        btn_menuInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuID = edit_menuID.getText().toString();
                menuName = edit_menuName.getText().toString();
                menuPrice = edit_menuPrice.getText().toString();
                menuInfo = edit_menuInfo.getText().toString();
                menuSize = edit_menuSize.getText().toString();
                menuCon = edit_menuCon.getText().toString();

                if(menuID.length()==0|menuName.length()==0|menuName.length()==0|menuInfo.length()==0|menuSize.length()==0|menuCon.length()==0){
                    Toast.makeText(getApplicationContext(), "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                }else if(ck==0){
                    Toast.makeText(getApplicationContext(), "이미지를 업로드 해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(AddMenuActivity.this);
                    dlg.setTitle(menuID + " : " + menuName);
                    dlg.setMessage("설명 : " + menuInfo + "\n" + "매장 용량 : " + menuSize+ "\n" + "매장 구성 : " + menuCon);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            try {
                                sqlDB = zeroHelper.getWritableDatabase();
                                sql = "INSERT INTO menu (menuID, menuName, menuPrice, menuImage, menuInfomation, menuSize, menuConcept, shopID) VALUES(?,?,?,?,?,?,?,?);";
                                SQLiteStatement insertStmt = sqlDB.compileStatement(sql);

                                insertStmt.clearBindings();
                                insertStmt.bindString(1, menuID);
                                insertStmt.bindString(2, menuName);
                                insertStmt.bindString(3,menuPrice);
                                insertStmt.bindBlob(4, imageMenu);
                                insertStmt.bindString(5, menuInfo);
                                insertStmt.bindString(6, menuSize);
                                insertStmt.bindString(7, menuCon);
                                insertStmt.bindString(8, shopID);
                                insertStmt.execute();

                                sqlDB.close();

                                Toast.makeText(getApplicationContext(), "메뉴 등록 되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
                                intent.putExtra("menuID", menuID);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "메뉴 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
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
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            try{
                selectedImageUri = data.getData();
                InputStream is = getContentResolver().openInputStream(selectedImageUri);
                imageMenu = getBytes(is);
                image_menu.setImageURI(selectedImageUri);
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
