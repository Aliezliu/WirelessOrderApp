
package com.softeem.orderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softeem.orderapp.R;

public class SwitchActivity extends AppCompatActivity {
    private Button btnpass;
    private String strpass;
    private EditText edit;
    private Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        btnpass = (Button)findViewById(R.id.button3);
        edit = (EditText)findViewById(R.id.editText2);
        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strpass = edit.getText().toString();
                intent.setClass(SwitchActivity.this,SettingActivity.class);
                startActivity(intent);

                //Toast.makeText(SwitchActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
