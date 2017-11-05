package com.zz.ak.demo.tool;

import android.util.Log;

import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.bean.Person;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/5.
 */

public class QueryTool {

    public static String TAG = "zhao--QueryTool";

    public QueryTool() {
    }

    /**
     * 根据表名查询多条数据
     * 表：Person  更具最新更新时间
     */
    public void queryObjects(){
        final BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.order("updatedAt");
        //先判断是否有缓存
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//		observable形式
        bmobQuery.findObjectsObservable(Person.class)
                .subscribe(new Subscriber<List<Person>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loge(e);
                    }
                    @Override
                    public void onNext(List<Person> persons) {
                        log("zhao-----查询成功：共"+persons.size()+"条数据。");
                        BmobApplication application = new BmobApplication();
                        application.personList = persons;
                        System.out.println("zhao--666------");
                    }
                });
    }










    public  void log(String msg) {
        Log.i(TAG, "===============================================================================");
        Log.i(TAG, msg);
    }

    public  void loge(Throwable e) {
        Log.i(TAG, "===============================================================================");
        if (e instanceof BmobException) {
            Log.e(TAG, "错误码：" + ((BmobException) e).getErrorCode() + ",错误描述：" + ((BmobException) e).getMessage());
        } else {
            Log.e(TAG, "错误描述：" + e.getMessage());
        }
    }
}
