package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.dingpeng3414.Adapter.FragmentPagerAdapterTab;
import com.dingpeng3414.ui.CategoryTabStrip;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private GridView gridView_main;
    private SimpleAdapter simpleAdapter;
    private int[] listImg;
    private String[] listName;
    private Button add_button;
    private CategoryTabStrip tabs;
    private ViewPager pager;
    private FragmentPagerAdapterTab adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.initView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initView() {
        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new FragmentPagerAdapterTab(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabs.setViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings://设置

                return true;
            case R.id.action_share://分享应用
                shareMsg("TT-Key", "http://blog.csdn.net/dingpeng3414/article/details/52429853", "");
                return true;
            case R.id.action_search://搜索
                startActivity(new Intent(this, SearchActivity.class));
                return true;

            case R.id.action_add://添加
                start();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_settings) {//设置
            startActivity(new Intent(this, SettingsActivity.class));

        } else if (id == R.id.nav_guide) {//指南
            startActivity(new Intent(this, GuideActivity.class));
        } else if (id == R.id.nav_send) {//反馈
           // startActivity(new Intent(this, ThreadActivity.class));
        } else if (id == R.id.nav_about) {//联系我们
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_sync_data) {
            startActivity(new Intent(this, SyncActivity.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void start() {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("sign", "add");
        intent.putExtra("title", "新建");
        startActivity(intent);
    }

    //分享标题，内容，图片
    public void shareMsg(String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享 TT - Key App"));
    }
}
