package com.gadik.randomfacts;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gadi
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private static final String TAG = TabsAdapter.class.getSimpleName();

    private final List<Fragment> fragmentsList = new ArrayList<>();
    private final List<String> fragmentsTitleList = new ArrayList<>();


    public TabsAdapter(FragmentManager fm) {
        super(fm);
        Log.d(TAG, "TabsAdapter ctor called.");
    }

    public void addFragment(Fragment fragment, String title){
        fragmentsList.add(fragment);
        fragmentsTitleList.add(title);
        Log.d(TAG, "Fragment added, Fragment title: " + title);

    }

    @Override
    public Fragment getItem(int i) {
        return fragmentsList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
