package com.zz.ak.demo.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.PersonMsg;
import com.zz.ak.demo.bean._User;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.QueryTool;
import com.zz.ak.demo.ui.mainfragment.FragmentVPAdapter;
import com.zz.ak.demo.ui.mainfragment.Fragment_New;
import com.zz.ak.demo.ui.mainfragment.Fragment_User;
import com.zz.ak.demo.ui.mainfragment.Fragment_me;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends BaseActivity implements TimeInterface {

    private ViewPager mViewPager;
    FragmentVPAdapter adapter;
    private MenuItem menuItem;
    BottomNavigationView navigation;

    private QueryTool queryTool;
    private Context mContext;
    private final String ACTION_NAME = "MainActivity_New_Up";

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
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        queryTool = new QueryTool(mContext,this);
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
                showPasswordSetDailog();
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

        //注册广播
        registerBoradcastReceiver();

    }

    private void showPasswordSetDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.balldialig, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText et_ball = (EditText) view
                .findViewById(R.id.et_ball);
        final EditText ev_shuo = (EditText) view
                .findViewById(R.id.ev_shuo);
        ImageView dialog_head = view.findViewById(R.id.dialog_head);
        TextView dialog_name = view.findViewById(R.id.dialog_name);

        try {
            dialog_name.setText(BmobApplication.UserMsg.getUsername());
            if (BmobApplication.UserMsg!=null && BmobApplication.UserMsg.getPic()!=null && !TextUtils.isEmpty(BmobApplication.UserMsg.getPic().getFileUrl().toString())){
                Glide.with(mContext).load(BmobApplication.UserMsg.getPic().getFileUrl())
                        .placeholder(R.mipmap.ic_cat) //设置占位图，在加载之前显示
                        .error(R.mipmap.ic_cat) //在图像加载失败时显示
                        .into(dialog_head);
            }else {
                Glide.with(mContext).load(R.mipmap.ic_cat)
                        .error(R.mipmap.ic_cat) //在图像加载失败时显示
                        .into(dialog_head);
            }
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }

        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_off);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String biaoqian = et_ball.getText().toString();
                final String beizhu = ev_shuo.getText().toString();
                if (!TextUtils.isEmpty(biaoqian)) {
                    String userId = BmobApplication.myUser.getObjectId();
                    final _User user = new _User();
                    user.setState(biaoqian);
                    addSubscription(user.update(userId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                toast("更新成功:");
                            }else{
                                toast("更新失败：");
                            }
                        }

                    }));
                }
                if (!TextUtils.isEmpty(beizhu)) {
                    PersonMsg personMsg = new PersonMsg();
                    personMsg.setPersonMsg(beizhu);
                    if (BmobApplication.UserMsg!=null && BmobApplication.UserMsg.getPic()!=null && !TextUtils.isEmpty(BmobApplication.UserMsg.getPic().getFileUrl().toString())){
                        personMsg.setPic(BmobApplication.UserMsg.getPic().getFileUrl().toString());
                    }
                    personMsg.setName(BmobApplication.myUser.getUsername());
                    personMsg.setPersonId(BmobApplication.myUser.getObjectId());
                    addSubscription(personMsg.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                toast("发布成功" + s);
                            } else {
                                toast("发布失败");
                            }
                        }
                    }));
                }
                dialog.dismiss();
                if (!TextUtils.isEmpty(biaoqian) || !TextUtils.isEmpty(beizhu)) {
                    showloading();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();// 隐藏dialog
            }
        });

        dialog.show();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ACTION_NAME)){
                closeloading();
                //刷新Fragment
                adapter.notifyDataSetChanged();
            }
        }

    };

    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    @Override
    public void getNewData() {
        closeloading();
        //刷新Fragment
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getNewDataError() {
        closeloading();
    }

}
