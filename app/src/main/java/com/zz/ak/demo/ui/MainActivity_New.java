package com.zz.ak.demo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.zz.ak.demo.R;
import com.zz.ak.demo.ui.mainfragment.FragmentVPAdapter;
import com.zz.ak.demo.ui.mainfragment.Fragment_New;
import com.zz.ak.demo.ui.mainfragment.Fragment_User;
import com.zz.ak.demo.ui.mainfragment.Fragment_me;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_New extends AppCompatActivity {

    private ViewPager mViewPager;
    FragmentVPAdapter adapter;
    private MenuItem menuItem;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);


        List<Fragment> fragmentList = new ArrayList<>();
        Fragment_User fragmentOne = Fragment_User.newInstance();
        Fragment_New fragmentTwo = Fragment_New.newInstance();
        Fragment_me fragmentThree = Fragment_me.newInstance();
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);

        adapter = new FragmentVPAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.notifyDataSetChanged();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
