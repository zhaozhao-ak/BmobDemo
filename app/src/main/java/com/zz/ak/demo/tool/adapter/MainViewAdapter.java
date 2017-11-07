package com.zz.ak.demo.tool.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zz.ak.demo.R;


/**
 * Created by chengxi on 17/4/26.
 */
public class MainViewAdapter extends BaseAdapter {

    private Fragment[] fragmentArray;
    private FragmentManager fragmentManager;
    private int hasMsgIndex=0;

    public void setHasMsgIndex(int hasMsgIndex) {
        this.hasMsgIndex = hasMsgIndex;
    }

    public MainViewAdapter(FragmentManager fragmentManager, Fragment[] fragmentArray) {
        this.fragmentManager = fragmentManager;
        this.fragmentArray = fragmentArray;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int hasMsgIndex() {
        return hasMsgIndex;
    }


    @Override
    public String[] getTextArray() {
        return new String[] {"首页", "最新","我的"};
    }

    @Override
    public Fragment[] getFragmentArray() {
        return fragmentArray;
    }

    @Override
    public int[] getIconImageArray() {
        return new int[] {R.mipmap.new_life_icon_grey, R.mipmap.new_find_icon_grey,R.mipmap.new_myhome_icon_grey};
    }

    @Override
    public int[] getSelectedIconImageArray() {
        return new int[] {R.mipmap.new_life_icon, R.mipmap.new_find_icon,R.mipmap.new_myhome_icon};
    }

    @Override
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
