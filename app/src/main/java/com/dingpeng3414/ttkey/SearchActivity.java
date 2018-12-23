package com.dingpeng3414.ttkey;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;
import com.dingpeng3414.service.ClipService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private GridView gridView;
    private int[] listImg;
    private String[] listName;
    private SimpleAdapter simpleAdapter;
    private ImageButton searchButton;
    private EditText search_ed;

    private ListView lv;
    private SimpleAdapter lvSimpleAdapter;
    private String[] listSt;
    private int[] listIn;
    private NotificationManager manger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("搜索");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void search(String textString) {

        List<AccountPassword> accountPasswordList;
        LocalDataBase localDataBase = new LocalDataBaseLmpl();
        accountPasswordList = localDataBase.category(textString);
        int x = accountPasswordList.size();

        if (x == 0) {//数据库没有数据
            Toast.makeText(this, "没有搜到，等待功能完善哦！！", Toast.LENGTH_SHORT).show();
        } else if (x > 0) {//数据库有数据
            showListView(x, accountPasswordList);
        }
    }

    private void showListView(int x, List<AccountPassword> accountPasswordList) {
        listSt = new String[]{"info_list_cname", "info_list_acname", "info_list_acpass", "tv_category"};
        listIn = new int[]{R.id.info_list_cname, R.id.info_list_acname, R.id.info_list_acpass, R.id.tv_category};


        List<Map<String, Object>> item = getDateListView(x, accountPasswordList);
        lvSimpleAdapter = new SimpleAdapter(this, item, R.layout.listview_item_information_list, listSt, listIn);
        lv = (ListView) findViewById(R.id.search_lv);
        lv.setAdapter(lvSimpleAdapter);
        this.lv.setOnItemClickListener(this);
        this.lv.setOnItemLongClickListener(this);
    }

    private List<Map<String, Object>> getDateListView(int x, List<AccountPassword> accountPasswordList) {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = x - 1; i >= 0; i--) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("info_list_cname", accountPasswordList.get(i).getAppName());
            itemMap.put("info_list_acname", accountPasswordList.get(i).getAccountName());
            itemMap.put("info_list_acpass", accountPasswordList.get(i).getAccountPassword());
            itemMap.put("tv_category", accountPasswordList.get(i).getCategory());
            items.add(itemMap);
        }
        return items;
    }


    private void initView() {
        search_ed = (EditText) findViewById(R.id.search_ed);
        searchButton = (ImageButton) findViewById(R.id.search_bt);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_ed.getText().toString().trim().equals("")) {
                    Toast.makeText(SearchActivity.this, "不能为空哦！！", Toast.LENGTH_SHORT).show();
                } else {
                    search(search_ed.getText().toString().trim());
                }
            }
        });

        this.gridView = (GridView) findViewById(R.id.search_gv);
        this.listImg = new int[]{R.id.gv_tv};
        this.listName = new String[]{"gv_tv"};
        List<Map<String, Object>> item = getDateGridView();
        simpleAdapter = new SimpleAdapter(this, item, R.layout.gv_item, this.listName, this.listImg);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getDateGridView() {//设置数据
        List<Map<String, Object>> items;
        String[] listName = new String[]{"网盘", "游戏", "邮箱", "软件", "社区"};
        items = new ArrayList<Map<String, Object>>();
        int y = listName.length;
        for (int i = 0; i < y; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("gv_tv", listName[i]);
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == 2131624109) {
            TextView tv = (TextView) view.findViewById(R.id.info_list_cname);
            String st = tv.getText().toString();
            Intent intent = new Intent(this, InformationActivity.class);
            intent.putExtra("itemName", st);
            startActivity(intent);
        } else if (parent.getId() == 2131624108) {
            TextView textView = (TextView) view.findViewById(R.id.gv_tv);
//            Toast.makeText(this, parent.getId() + "", Toast.LENGTH_SHORT).show();
            search(textView.getText().toString().trim());

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "下拉通知栏", Toast.LENGTH_SHORT).show();
        sendCustomerNotification(view);
        return true;
    }
    private void sendCustomerNotification(View view) {
        manger = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 16) {
            Notification notification = new Notification();
            notification.icon = R.mipmap.ic_launcher;
            notification.tickerText = "复制信息";
            notification.contentView = new RemoteViews(this.getPackageName(), R.layout.suspension_window_layout);
            RemoteViews remotvViews = new RemoteViews(this.getPackageName(), R.layout.suspension_window_layout);

            TextView tv = (TextView) view.findViewById(R.id.info_list_cname);
            TextView tv1 = (TextView) view.findViewById(R.id.tv_category);
            TextView tv2 = (TextView) view.findViewById(R.id.info_list_acname);
            TextView tv3 = (TextView) view.findViewById(R.id.info_list_acpass);

            remotvViews.setTextViewText(R.id.info_list_cname, tv.getText().toString());
            remotvViews.setTextViewText(R.id.tv_category, tv1.getText().toString());
            remotvViews.setTextViewText(R.id.info_list_acname, tv2.getText().toString());
            remotvViews.setTextViewText(R.id.info_list_acpass, tv3.getText().toString());
//账户复制
            Intent intent = new Intent(this, ClipService.class);
            intent.putExtra("data", tv2.getText().toString());

//            Toast.makeText(getActivity(),  tv2.getText().toString() + "", Toast.LENGTH_SHORT).show();

            PendingIntent PIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            remotvViews.setOnClickPendingIntent(R.id.bt1, PIntent);

//密码复制
            Intent intentPass = new Intent(this, ClipService.class);
            intentPass.putExtra("data", tv3.getText().toString());

            PendingIntent PIntentPass = PendingIntent.getService(this, 1, intentPass, PendingIntent.FLAG_CANCEL_CURRENT);
            remotvViews.setOnClickPendingIntent(R.id.bt2, PIntentPass);

            notification.bigContentView = remotvViews;
            manger.notify(10, notification);
        } else {
            Toast.makeText(this, "系统版本过低，不支持此种样式！", Toast.LENGTH_SHORT).show();
        }
    }
}
