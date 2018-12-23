package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;


/**
 * 账户信息显示界面
 */
public class InformationActivity extends AppCompatActivity {
    private Intent intent;
    static String title;
    private ImageView im;
    private ListView lv;
    private AccountPassword accountPassword;
    private LocalDataBase localDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.intent = getIntent();
        this.title = intent.getStringExtra("itemName");
        toolbar.setTitle(this.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initView();

    }


    private void initView() {
        im = (ImageView) findViewById(R.id.info_im);
        lv = (ListView) findViewById(R.id.info_lv);
        this.initListView();

    }

    private void initListView() {
        String[] itemSt = new String[8];
        localDataBase = new LocalDataBaseLmpl();
        accountPassword = localDataBase.getAccountPassword(title);
        int j = 0;
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    itemSt[j] = "详细信息";
                    j++;
                    break;
                case 1:
                    itemSt[j] = "用户：" + accountPassword.getAccountName().toString();
                    j++;
                    break;
                case 2:
                    itemSt[j] = "密码：" + accountPassword.getAccountPassword().toString();
                    j++;
                    break;
                case 3:
                    if (!accountPassword.getCategory().equals("")) {
                        itemSt[j] = "类别：" + accountPassword.getCategory().toString();
                        j++;
                    }
                    break;
                case 4:
                    if (!accountPassword.getEmail().equals("")) {
                        itemSt[j] = "电邮：" + accountPassword.getEmail().toString();
                        j++;
                    }
                    break;
                case 5:
                    if (!accountPassword.getPhone().equals("")) {
                        itemSt[j] = "电话：" + accountPassword.getPhone().toString();
                        j++;
                    }
                    break;
                case 6:
                    if (!accountPassword.getUrl().equals("")) {
                        itemSt[j] = "URL：" + accountPassword.getUrl().toString();
                        j++;
                    }
                    break;
                case 7:
                    if (!accountPassword.getRemarks().equals("")) {
                        itemSt[j] = "备注：" + accountPassword.getRemarks().toString();
                        j++;
                    }
                    break;
            }
        }

        String[] itemS = new String[j];
        for (int i = 0; i < j; i++) {
            itemS[i] = itemSt[i];
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemS);
        this.lv.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.updata://修改数据
                Intent stIntent = new Intent(this, AddActivity.class);
                stIntent.putExtra("appName", title);
                stIntent.putExtra("sign", "updata");
                stIntent.putExtra("title", "更新" + title);
                startActivity(stIntent);
                return true;
            case R.id.delete://删除数据

                if (localDataBase.deleteAccountPassword(accountPassword.get_id()) == 1) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
                    this.finish();
                } else {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_LONG).show();
                }

                return true;
            case android.R.id.home:
                this.finish();
                break;
//            case R.id.share://发送
//
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.initListView();
    }
}
