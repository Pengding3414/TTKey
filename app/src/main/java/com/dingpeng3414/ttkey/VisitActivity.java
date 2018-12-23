package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.dingpeng3414.util.UtilPref;

public class VisitActivity extends AppCompatActivity {
    private EditText ed_key;
    private Button bt_key;
    private UtilPref utilPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        utilPref = new UtilPref(this);
        this.initView();
    }

    private void initView() {
        ed_key = (EditText) findViewById(R.id.ed_key);
        ed_key.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        bt_key = (Button) findViewById(R.id.bt_key);
        bt_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_key.getText().toString().equals(utilPref.getPass())) {
                    Intent i = new Intent(VisitActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else if (utilPref.getPass().equals("false")) {
                    if (ed_key.getText().toString().equals("123456")){
                        Intent i = new Intent(VisitActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }


}
