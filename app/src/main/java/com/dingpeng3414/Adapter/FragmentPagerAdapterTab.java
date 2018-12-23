package com.dingpeng3414.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dingpeng3414.Fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentPagerAdapterTab extends FragmentPagerAdapter {

    private final List<String> catalogs = new ArrayList<String>();

    public FragmentPagerAdapterTab(FragmentManager fm) {
        super(fm);
        catalogs.add("全部");
        catalogs.add("网址");
        catalogs.add("游戏");
        catalogs.add("邮箱");
        catalogs.add("软件");
        catalogs.add("社区");
        catalogs.add("其他");

    }

   

	@Override
    public CharSequence getPageTitle(int position) {
        return catalogs.get(position);
    }

    @Override
    public int getCount() {
        return catalogs.size();
    }

    @Override
    public Fragment getItem(int position) {

        return NewsFragment.newInstance(position);
    }

}

