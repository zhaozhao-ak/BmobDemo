package com.zz.ak.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zz.ak.demo.BaseActivity;
import com.zz.ak.demo.R;

/**
 * Created by Administrator on 2017/11/4.
 */

public class SplashActivity extends BaseActivity {

    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable,2000);
    }
}
