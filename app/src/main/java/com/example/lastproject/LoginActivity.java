package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    int login_code=0;

    TextView text_Join;
    Button btn_Login;
    EditText edit_ID, edit_Password;
    String adminID = "admin", adminPass = "1234";
    ImageView img_x;

    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;

    String loginID, loginPassword, sql, selectID, selectPassword, selectNickname;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Login = (Button)findViewById(R.id.btnLogin);
        text_Join = (TextView)findViewById(R.id.textJoin);
        edit_ID = (EditText)findViewById(R.id.editID);
        edit_Password = (EditText)findViewById(R.id.editPassword);
        img_x = (ImageView)findViewById(R.id.img_x);

        zeroHelper = new zeroDBHelper(this);

        img_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                startActivity(intent);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginID = edit_ID.getText().toString();
                loginPassword = edit_Password.getText().toString();

                if(loginID.equals("admin") & loginPassword.equals("a1234")){
                    Intent intent = new Intent(getApplicationContext(),AdminActivity.class);
                    startActivity(intent);
                }else if(loginID.length()==0){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(loginPassword.length()==0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        sqlDB = zeroHelper.getReadableDatabase();
                        sql = "SELECT * FROM user WHERE userID = '"+loginID+"';";
                        cursor = sqlDB.rawQuery(sql, null);

                        while (cursor.moveToNext()){
                            selectID = cursor.getString(0);
                            selectPassword = cursor.getString(1);
                            selectNickname = cursor.getString(2);

                        }

                        if(cursor.getCount() != 1){ //아이디가 틀렸을 때
                            edit_ID.setText("");
                            edit_Password.setText("");
                            Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        }else if(!loginPassword.equals(selectPassword)){ //비밀번호가 틀렸을 때
                            edit_ID.setText("");
                            edit_Password.setText("");
                            Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }else{ //로그인 성공
                            sql = "SELECT userNickname FROM user WHERE userID = '"+loginID+"';";
                            cursor = sqlDB.rawQuery(sql, null);
                            Toast.makeText(getApplicationContext(),selectNickname+"님, 반갑습니다.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("selectNickname", selectNickname);
                            startActivity(intent);
                        }
                        cursor.close();
                        sqlDB.close();
                    }catch (Exception e){
                        e.printStackTrace();
                        edit_ID.setText("");
                        edit_Password.setText("");
                        Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        text_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}