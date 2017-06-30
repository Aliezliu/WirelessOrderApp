package com.softeem.orderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softeem.orderapp.R;

public class SettingActivity extends AppCompatActivity {
    private EditText edit;
    private Button btnxg;
    private String password;
    private EditText editTai;
    private Button btnsz;
    private String taihao;
    private TextView Taihao;
    private Button btnback;
    private Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnxg = (Button)findViewById(R.id.button);
        edit = (EditText)findViewById(R.id.setPass);
        btnsz = (Button)findViewById(R.id.button2);
        editTai = (EditText)findViewById(R.id.setTai);
        Taihao = (TextView)findViewById(R.id.textView3);
        btnback = (Button)findViewById(R.id.button5);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnsz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taihao = editTai.getText().toString();
               // Taihao.setText(taihao);
                Toast.makeText(SettingActivity.this,"台号修改成功!",Toast.LENGTH_SHORT).show();
                editTai.setText("");
            }
        });

        btnxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = edit.getText().toString();

                Toast.makeText(SettingActivity.this,"修改密码成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
