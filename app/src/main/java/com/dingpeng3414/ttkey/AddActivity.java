package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    //账户名称，类别，账户，密码，email，电话，网址，备注
    private TextView accountNameTv, categoriesTv, accountTv, passwordTv, emailTv, phoneTv, urlTv, remarksTv;
    private Button saveBt;
    private Intent intent;
    private Toolbar toolbar;
    private String title, categories;
    private LocalDataBase localDataBase;
    private AccountPassword accountPassword;
    private Spinner spinner;
    private int zitiColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.intent = getIntent();
        title = intent.getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.localDataBase = new LocalDataBaseLmpl();
        this.initView();
        this.switchView();
    }

    private void switchView() {
        switch (intent.getStringExtra("sign")) {
            case "add":
                accountPassword = new AccountPassword();
                break;
            case "updata":
                saveBt.setText("更新数据");
                accountPassword = localDataBase.getAccountPassword(intent.getStringExtra("appName"));
                this.initViewData();

                break;

            default:
                break;
        }
    }

    private void initView() {
        accountNameTv = (TextView) findViewById(R.id.account_name);
        categoriesTv = (TextView) findViewById(R.id.categories);
        accountTv = (TextView) findViewById(R.id.account);
        passwordTv = (TextView) findViewById(R.id.password);
        emailTv = (TextView) findViewById(R.id.email);
        phoneTv = (TextView) findViewById(R.id.phone);
        urlTv = (TextView) findViewById(R.id.url);
        remarksTv = (TextView) findViewById(R.id.remarks);
        saveBt = (Button) findViewById(R.id.save);
        saveBt.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.sp_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] categoriesS = getResources().getStringArray(R.array.categories);
                categories = categoriesS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViewData() {
        accountNameTv.setText(accountPassword.getAppName());
        categoriesTv.setText(accountPassword.getCategory());
        accountTv.setText(accountPassword.getAccountName());
        passwordTv.setText(accountPassword.getAccountPassword());
        emailTv.setText(accountPassword.getEmail());
        phoneTv.setText(accountPassword.getPhone());
        urlTv.setText(accountPassword.getUrl());
        remarksTv.setText(accountPassword.getRemarks());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            zitiColor = Integer.valueOf(data.getStringExtra("color"));
//            showToast(String.valueOf(zitiColor));
            showToast("字体颜色设置成功");
        }
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_guide_menu://教程
                startActivity(new Intent(this, Guide.class));
                return true;
            case android.R.id.home:
                this.finish();
                break;
//            case R.id.action_menu_color:
//                Intent intentColor = new Intent(this, ColcrActivity.class);
//                startActivityForResult(intentColor, 1);
//                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                accountPassword.setAccountName(accountTv.getText().toString());
                accountPassword.setAppName(accountNameTv.getText().toString());
                accountPassword.setCategory(categories);
                accountPassword.setAccountPassword(passwordTv.getText().toString());
                accountPassword.setEmail(emailTv.getText().toString());
                accountPassword.setPhone(phoneTv.getText().toString());
                accountPassword.setUrl(urlTv.getText().toString());
                accountPassword.setRemarks(remarksTv.getText().toString());
                accountPassword.setZitiColor(zitiColor);
                switch (intent.getStringExtra("sign")) {
                    case "add":
                        if (localDataBase.addAccountPassword(accountPassword) == 0) {
                            this.finish();
                        } else {

                        }
                        break;
                    case "updata":
                        localDataBase.updataAccountPassword(accountPassword, accountPassword.get_id());
                        this.finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
