package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    EditText edit_JoinID, edit_JoinPassword, edit_PassCk, edit_JoinNickname;
    Button btn_Join, btn_PassCK;
    ImageView img_X;
    String joinID, joinPassword, joinPassCK, joinNickname, sql;
    SQLiteDatabase sqlDB;
    zeroDBHelper zeroHelper;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edit_JoinID = (EditText)findViewById(R.id.editJoinID);
        edit_JoinPassword = (EditText)findViewById(R.id.editJoinPassword);
        edit_PassCk = (EditText)findViewById(R.id.editPasswordCk);
        edit_JoinNickname = (EditText)findViewById(R.id.editJoinNickname);

        img_X = (ImageView)findViewById(R.id.imgX);

        btn_Join = (Button)findViewById(R.id.btnJoin);
        btn_PassCK = (Button)findViewById(R.id.btnPassCk);

        zeroHelper = new zeroDBHelper(this);

        img_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_PassCK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinPassword = edit_JoinPassword.getText().toString();
                joinPassCK = edit_PassCk.getText().toString();

                if(joinPassword.length()==0|joinPassCK.length()==0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(joinPassword.equals(joinPassCK)){
                    i++;
                    Toast.makeText(getApplicationContext(), "비밀번호가 같습니다.", Toast.LENGTH_SHORT).show();
                    edit_JoinPassword.setClickable(false);
                    edit_JoinPassword.setFocusable(false);
                    edit_PassCk.setClickable(false);
                    edit_PassCk.setFocusable(false);
                }else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinID = edit_JoinID.getText().toString();
                joinNickname = edit_JoinNickname.getText().toString();

                if (joinID.length() == 0 | joinPassword.length() == 0 | joinNickname.length() == 0) {
                    Toast.makeText(getApplicationContext(), "빈공간을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else if (i == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        sqlDB = zeroHelper.getWritableDatabase();
                        sql = "INSERT INTO user VALUES('" + joinID + "','" + joinPassword + "','" + joinNickname + "');";
                        sqlDB.execSQL(sql);
                        sqlDB.close();
                        Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}