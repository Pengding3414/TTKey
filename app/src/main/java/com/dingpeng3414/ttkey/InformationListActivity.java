package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Intent intent;
    private String title;
    private ListView lv;

    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        title = intent.getStringExtra("title");

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv = (ListView) findViewById(R.id.info_lv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_information_list);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        this.initListView();
    }

    private void initListView() {
        lv = (ListView) findViewById(R.id.info_lv);
        String[] listSt = new String[]{"info_list_cname", "info_list_acname", "info_list_acpass"};
        int[] listIn = new int[]{ R.id.info_list_cname, R.id.info_list_acname, R.id.info_list_acpass};
//        int[] listIn = new int[]{R.id.info_list_iv, R.id.info_list_cname, R.id.info_list_acname, R.id.info_list_acpass};
        List<Map<String, Object>> item = getDateListView();
        simpleAdapter = new SimpleAdapter(this, item, R.layout.listview_item_information_list, listSt, listIn);
        this.bindingSinmpleAdapter();
        this.lv.setOnItemClickListener(this);
    }

    private void bindingSinmpleAdapter() {//绑定adapter
        this.lv.setAdapter(this.simpleAdapter);
    }

    private List<Map<String, Object>> getDateListView() {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        LocalDataBase localDataBase = new LocalDataBaseLmpl();
        List<AccountPassword> accountPasswordList;
        if (title.equals("全部")) {
            accountPasswordList = localDataBase.getAccountPasswordAll();
        } else {
            accountPasswordList = localDataBase.category(title);
        }

        int x = accountPasswordList.size();
        for (int i = 0; i < x; i++) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
//            itemMap.put("info_list_iv", R.mipmap.alm_cspace_user_add);
            itemMap.put("info_list_cname", accountPasswordList.get(i).getAppName());
            itemMap.put("info_list_acname", accountPasswordList.get(i).getAccountName());
            itemMap.put("info_list_acpass", accountPasswordList.get(i).getAccountPassword());
            items.add(itemMap);
        }


        return items;
    }

    private void start() {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("sign","add");
        intent.putExtra("title","创建账户&密码");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_informationlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.test:
                startActivity(new Intent(this, InformationActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.info_list_cname);
        String st = tv.getText().toString();
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("itemName", st);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.initListView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        this.initListView();
    }
}
