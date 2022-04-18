package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpdateMenuActivity extends AppCompatActivity {

    ImageView image_updateMenu;
    TextView updateMenu_ID, updateMenu_name, updateMenu_price, updateMenu_info, updateMenu_size, updateMenu_con;
    Button btn_menuUpdate, btn_menuDelect;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    Cursor cursor;
    String admin_menuID, up_menuID, up_menuName, up_menuPrice, up_menuInfo, up_menuSize, up_menuCon, shopID, update_sql, delete_sql;

    final int GET_GALLERY_IMAGE = 200;

    byte[] up_imageMenu = null;
    Uri selectedImageUri;
    Bitmap up_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        image_updateMenu = (ImageView)findViewById(R.id.imageUpdateMenu);

        updateMenu_ID = (EditText)findViewById(R.id.updateMenuID);
        updateMenu_name = (EditText)findViewById(R.id.updateMenuName);
        updateMenu_price = (EditText)findViewById(R.id.updateMenuPrice);
        updateMenu_info = (EditText)findViewById(R.id.updateMenuInfo);
        updateMenu_size = (EditText)findViewById(R.id.updateMenuSize);
        updateMenu_con = (EditText)findViewById(R.id.updateMenuCon);

        btn_menuUpdate = (Button)findViewById(R.id.menuUpdate);
        btn_menuDelect = (Button)findViewById(R.id.menuDelete);

        Intent intent = getIntent();
        admin_menuID = intent.getExtras().getString("menuID");

        zeroHelper = new zeroDBHelper(this);

        try {
            sqlDB = zeroHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM menu WHERE menuID ='"+admin_menuID+"' ;", null);

            cursor.moveToNext();

            up_menuID = cursor.getString(0);
            up_menuName = cursor.getString(1);
            up_menuPrice = cursor.getString(2);
            up_imageMenu = cursor.getBlob(3);
            up_menuInfo = cursor.getString(4);
            up_menuSize = cursor.getString(5);
            up_menuCon = cursor.getString(6);
            shopID = cursor.getString(7);

            up_bitmap = BitmapFactory.decodeByteArray(up_imageMenu,0,up_imageMenu.length);

            updateMenu_ID.setText(up_menuID);
            updateMenu_ID.setClickable(false);
            updateMenu_ID.setFocusable(false);

            updateMenu_name.setText(up_menuName);
            image_updateMenu.setImageBitmap(up_bitmap);
            updateMenu_price.setText(up_menuPrice);
            updateMenu_info.setText(up_menuInfo);
            updateMenu_size.setText(up_menuSize);
            updateMenu_con.setText(up_menuCon);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "메뉴 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();

        }

        image_updateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(imageIntent, GET_GALLERY_IMAGE);
            }
        });

        btn_menuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_menuName = updateMenu_name.getText().toString();
                up_menuPrice = updateMenu_price.getText().toString();
                up_menuInfo = updateMenu_info.getText().toString();
                up_menuSize = updateMenu_size.getText().toString();
                up_menuCon = updateMenu_con.getText().toString();

                AlertDialog.Builder dlg = new AlertDialog.Builder(UpdateMenuActivity.this);
                dlg.setTitle("변경 : "+up_menuID);
                dlg.setMessage(up_menuName + "\n" + up_menuPrice + "\n" + up_menuInfo + "\n" + up_menuSize + "\n" + up_menuCon);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            sqlDB = zeroHelper.getWritableDatabase();
                            update_sql = "UPDATE menu SET menuName = ?, menuPrice = ?, menuImage = ?, menuInfomation = ?, menuSize = ?, menuConcept = ? WHERE menuID = ?;";
                            SQLiteStatement updateStmt = sqlDB.compileStatement(update_sql);

                            updateStmt.clearBindings();
                            updateStmt.bindString(1, up_menuName);
                            updateStmt.bindString(2, up_menuPrice);
                            updateStmt.bindBlob(3, up_imageMenu);
                            updateStmt.bindString(4, up_menuInfo);
                            updateStmt.bindString(5, up_menuSize);
                            updateStmt.bindString(6, up_menuCon);
                            updateStmt.bindString(7, up_menuID);
                            updateStmt.execute();

                            sqlDB.close();

                            Toast.makeText(getApplicationContext(), "변경 되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
                            intent.putExtra("menuID",up_menuID);
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

        btn_menuDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(UpdateMenuActivity.this);
                dlg.setTitle("삭제 : "+up_menuID);
                dlg.setMessage(up_menuName + "\n" + up_menuPrice + "\n" + up_menuInfo + "\n" + up_menuSize + "\n" + up_menuCon);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            sqlDB = zeroHelper.getWritableDatabase();
                            delete_sql = "DELETE FROM menu WHERE menuID = ?;";
                            SQLiteStatement deleteStmt = sqlDB.compileStatement(delete_sql);

                            deleteStmt.clearBindings();
                            deleteStmt.bindString(1,up_menuID);
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
                up_imageMenu = getBytes(is);
                image_updateMenu.setImageURI(selectedImageUri);
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