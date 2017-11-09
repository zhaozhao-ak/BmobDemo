package com.zz.ak.demo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zz.ak.demo.BaseActivity;
import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.MainActivity;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.PersonMsg;
import com.zz.ak.demo.bean._User;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.QueryTool;
import com.zz.ak.demo.tool.adapter.MainViewAdapter;
import com.zz.ak.demo.tool.listener.OnTabSelectedListener;
import com.zz.ak.demo.tool.widget.Tab;
import com.zz.ak.demo.tool.widget.TabContainerView;
import com.zz.ak.demo.ui.fragment.TabFragment1;
import com.zz.ak.demo.ui.fragment.TabFragment2;
import com.zz.ak.demo.ui.fragment.TabFragment3;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,TimeInterface {
    private QueryTool queryTool;
    private Context mContext;
    private MainViewAdapter mainViewAdapter;
    private TabContainerView tabContainerView;
    private TabFragment1 fragment1;
    private TabFragment2 fragment2;
    private TabFragment3 fragment3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        mContext = this;
        queryTool = new QueryTool(mContext,this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordSetDailog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //内容区
        fragment1 = new TabFragment1();
        fragment2 = new TabFragment2();
        fragment3 = new TabFragment3();
        tabContainerView = (TabContainerView) findViewById(R.id.tab_container);
        mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[] {fragment1, fragment2,fragment3});
        mainViewAdapter.setHasMsgIndex(3);
        tabContainerView.setAdapter(mainViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_off);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String biaoqian = et_ball.getText().toString();
                String beizhu = ev_shuo.getText().toString();
                if (!TextUtils.isEmpty(biaoqian)) {
                    String userId = BmobApplication.myUser.getObjectId();
                    final _User user = new _User();
                    user.setState(biaoqian);
                    addSubscription(user.update(userId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                toast("成功更新标签:"+user.getUpdatedAt());
                            }else{
                                toast("标签更新失败：" + e.getMessage());
                            }
                        }

                    }));
                }
                if (!TextUtils.isEmpty(beizhu)) {
                    PersonMsg personMsg = new PersonMsg();
                    personMsg.setPersonMsg(beizhu);
                    if (BmobApplication.UserMsg!=null && !TextUtils.isEmpty(BmobApplication.UserMsg.getPic().getFileUrl().toString())){
                        personMsg.setPic(BmobApplication.UserMsg.getPic().getFileUrl().toString());
                    }
                    personMsg.setName(BmobApplication.myUser.getUsername());
                    personMsg.setPersonId(BmobApplication.myUser.getObjectId());
                    addSubscription(personMsg.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                toast("发表成功" + s);
                            } else {
                                toast("发表失败");
                            }
                        }
                    }));
                }
                dialog.dismiss();
                if (!TextUtils.isEmpty(biaoqian) || !TextUtils.isEmpty(beizhu)){
                    showloading();
                    queryTool.queryAllPerson();
                    queryTool.queryAllPersonMsg();
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


    @Override
    public void getNewData() {
        closeloading();
        //刷新Fragment
        fragment1.setData();
        fragment2.setData();

    }

    @Override
    public void getNewDataError() {
        closeloading();
    }
}
