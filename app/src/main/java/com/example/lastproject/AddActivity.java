package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class
AddActivity extends AppCompatActivity {

    public static final int insert_code=1002;

    ImageView img_back, image_shopLogo;
    Button btn_shopInsert;
    CheckBox[] check = new CheckBox[5];
    EditText edit_shopID, edit_shopName, edit_shopLoca;
    String add_shopID, add_shopName, add_shopKategorie, add_shopLocation, add_sql;
    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    int ck = 0;

    final int GET_GALLERY_IMAGE = 200;

    byte[] add_imageLogo = null;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        img_back = (ImageView)findViewById(R.id.imgBack);
        image_shopLogo = (ImageView) findViewById(R.id.imageShop);
        btn_shopInsert = (Button)findViewById(R.id.btnShopInsert);

        check[0] = (CheckBox)findViewById(R.id.checkKorean);
        check[1] = (CheckBox)findViewById(R.id.checkSnack);
        check[2] = (CheckBox)findViewById(R.id.checkJapanese);
        check[3] = (CheckBox)findViewById(R.id.checkChinese);
        check[4] = (CheckBox)findViewById(R.id.checkMidnight);

        edit_shopID = (EditText)findViewById(R.id.editShopID);
        edit_shopName = (EditText)findViewById(R.id.editShopName);
        edit_shopLoca = (EditText)findViewById(R.id.editShopLoca);

        zeroHelper = new zeroDBHelper(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        image_shopLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                ck++;
            }
        });

            btn_shopInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_shopKategorie = "";

                    add_shopID = edit_shopID.getText().toString();
                    add_shopName = edit_shopName.getText().toString();
                    add_shopLocation = edit_shopLoca.getText().toString();

                    for (int i = 0; i < check.length; i++) {
                        if (check[i].isChecked() == true) {
                            add_shopKategorie += check[i].getText().toString();
                        }
                    }

                    if (add_shopID.length() == 0 | add_shopName.length() == 0 | add_shopLocation.length() == 0)
                        Toast.makeText(getApplicationContext(), "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                    else if (add_shopKategorie.length() == 0)
                        Toast.makeText(getApplicationContext(), "분류를 하나이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    else if (ck == 0)
                        Toast.makeText(getApplicationContext(), "이미지를 업로드 해주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(AddActivity.this);
                        dlg.setTitle(add_shopID + " : " + add_shopName);
                        dlg.setMessage("종류 : " + add_shopKategorie + "\n" + "매장 주소 : " + add_shopLocation);
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    sqlDB = zeroHelper.getWritableDatabase();
                                    add_sql = "INSERT INTO shop (shopID, shopName, shopLogo, shopKategorie, shopLocation) VALUES(?,?,?,?,?);";
                                    SQLiteStatement insertStmt = sqlDB.compileStatement(add_sql);

                                    insertStmt.clearBindings();
                                    insertStmt.bindString(1, add_shopID);
                                    insertStmt.bindString(2, add_shopName);
                                    insertStmt.bindBlob(3, add_imageLogo);
                                    insertStmt.bindString(4, add_shopKategorie);
                                    insertStmt.bindString(5, add_shopLocation);
                                    insertStmt.execute();

                                    sqlDB.close();

                                    Toast.makeText(getApplicationContext(), "가게가 등록 되었습니다.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), AdminShopActivity.class);
                                    intent.putExtra("shopID", add_shopID);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "가게 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
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
                add_imageLogo = getBytes(is);
                image_shopLogo.setImageURI(selectedImageUri);
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