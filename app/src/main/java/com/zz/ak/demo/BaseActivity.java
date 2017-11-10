package com.zz.ak.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zz.ak.demo.bean.Person;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.LoadingDialog;
import com.zz.ak.demo.tool.QueryTool;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ValueEventListener;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity implements TimeInterface {

    public static String TAG = "zhao--BaseActivity";
    protected ListView mListview;
    protected BaseAdapter mAdapter;

    private CompositeSubscription mCompositeSubscription;
    private QueryTool queryTool;

    public static LoadingDialog loadingdialog;
    public static List<Person> personList = new ArrayList<>();

    /**
     * 解决Subscription内存泄露问题
     *
     * @param s
     */
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryTool = new QueryTool(this.getApplicationContext(),this);
        final BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject data) {
                Log.d("zhao---bmob", "("+data.optString("action")+")"+"数据："+data);
                queryTool.queryAllPerson();
                queryTool.queryAllPersonMsg();
            }
            @Override
            public void onConnectCompleted(Exception ex) {
                Log.d("bmob", "连接成功:"+rtd.isConnected());
                // 监听表更新
                rtd.subTableUpdate("_User");
                rtd.subTableUpdate("PersonMsg");

                // 监听表删除
//                rtd.subTableDelete("PersonMsg");
                // 监听行更新
//                rtd.subRowUpdate("", "_User");
//                // 监听行删除
//                rtd.subRowDelete("", "PersonMsg");
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void log(String msg) {
        Log.i(TAG, "===============================================================================");
        Log.i(TAG, msg);
    }

    public static void loge(Throwable e) {
        Log.i(TAG, "===============================================================================");
        if (e instanceof BmobException) {
            Log.e(TAG, "错误码：" + ((BmobException) e).getErrorCode() + ",错误描述：" + ((BmobException) e).getMessage());
        } else {
            Log.e(TAG, "错误描述：" + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public void showloading() {
        loadingdialog = new LoadingDialog(BaseActivity.this);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.setCancelable(false);
        loadingdialog.show();
    }

    public void closeloading() {
        if (loadingdialog!=null){
            loadingdialog.dismiss();
        }
    }

    @Override
    public void getNewData() {
        Intent mIntent = new Intent("MainActivity_New_Up");
        //发送广播
        sendBroadcast(mIntent);

    }

    @Override
    public void getNewDataError() {

    }
}
