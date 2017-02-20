package com.ckz.baisi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by CKZ on 2017/2/7.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    private List<String> mTitleList;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> mList, List<String> mTitleList){
        super(fm);
        this.mList = mList;
        this.mTitleList = mTitleList;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return mTitleList.get(position);//页卡标题
    }
}
