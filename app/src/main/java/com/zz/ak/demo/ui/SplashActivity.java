package com.zz.ak.demo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.zz.ak.demo.BaseActivity;
import com.zz.ak.demo.R;

/**
 * Created by Administrator on 2017/11/4.
 */

public class SplashActivity extends BaseActivity {

    Handler handler;
    Runnable runnable;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("userInfo", 0);
        final String user =sp.getString("user", "");
        final String password =sp.getString("password", "");
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        };
        handler.postDelayed(runnable,2000);
    }
}
