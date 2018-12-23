package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dingpeng3414.Adapter.MainListAdapter;
import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;

import java.util.List;

public class MainListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private RecyclerView recyclerView;
    private MainListAdapter mainListAdapter;
    private List<AccountPassword> accountPasswordList;
    private LocalDataBase localDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        localDataBase = new LocalDataBaseLmpl();
        accountPasswordList = localDataBase.getAccountPasswordAll();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mainListAdapter = new MainListAdapter();
        recyclerView.setAdapter(mainListAdapter);
        mainListAdapter.setmData(accountPasswordList);
    }

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

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
