package com.dingpeng3414.ttkey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;

import me.drakeet.materialdialog.MaterialDialog;

public class InfoScrollingActivity extends AppCompatActivity {
    private Intent intent;
    static String title;
    private ImageView im;
    private ListView lv;
    private AccountPassword accountPassword;
    private LocalDataBase localDataBase;
    private TextView category_tv;
    MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.intent = getIntent();
        this.title = intent.getStringExtra("itemName");
        toolbar.setTitle(this.title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updataF();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        this.initView();

    }

    private void initView() {
        im = (ImageView) findViewById(R.id.info_im);
        lv = (ListView) findViewById(R.id.info_lv);
        category_tv = (TextView) findViewById(R.id.category_tv);
        this.initListView();

    }

    private void initListView() {
        String[] itemSt = new String[8];
        localDataBase = new LocalDataBaseLmpl();
        accountPassword = localDataBase.getAccountPassword(title);
        int j = 0;
        for (int i = 0; i < 6; i++) {
            switch (i) {
//                case 0:
//                    itemSt[j] = "详细信息";
//                    j++;
//                    break;
                case 0:
                    itemSt[j] = "用户：" + accountPassword.getAccountName().toString();
                    j++;
                    break;
                case 1:
                    itemSt[j] = "密码：" + accountPassword.getAccountPassword().toString();
                    j++;
                    break;
//                case 2:
//                    if (!accountPassword.getCategory().equals("")) {
//                        itemSt[j] = "类别：" + accountPassword.getCategory().toString();
//                        j++;
//                    }
//                    break;
                case 2:
                    if (!accountPassword.getEmail().equals("")) {
                        itemSt[j] = "电邮：" + accountPassword.getEmail().toString();
                        j++;
                    }
                    break;
                case 3:
                    if (!accountPassword.getPhone().equals("")) {
                        itemSt[j] = "电话：" + accountPassword.getPhone().toString();
                        j++;
                    }
                    break;
                case 4:
                    if (!accountPassword.getUrl().equals("")) {
                        itemSt[j] = "URL：" + accountPassword.getUrl().toString();
                        j++;
                    }
                    break;
                case 5:
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
        category_tv.setText(accountPassword.getCategory().toString());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemS);
        this.lv.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_info, menu);
        return true;
    }

    private void updataF() {//修改数据
        Intent stIntent = new Intent(this, AddActivity.class);
        stIntent.putExtra("appName", title);
        stIntent.putExtra("sign", "updata");
        stIntent.putExtra("title", "更新" + title);
        startActivity(stIntent);
    }

    private void deleteF() {//删除数据
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("提示").setMessage("确定要删除这条数据吗？")

                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (localDataBase.deleteAccountPassword(accountPassword.get_id()) == 1) {
                            showToast("删除成功", 0);
                        } else {
                            showToast("删除失败", 1);
                        }
                        mMaterialDialog.dismiss();
                    }
                }).setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        }).setCanceledOnTouchOutside(true).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mMaterialDialog.dismiss();
            }
        }).show();
    }

    private void showToast(String string, int i) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        if (i == 0) {
            this.finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updata://修改数据
                updataF();
                return true;
            case R.id.delete://删除数据
                deleteF();
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
