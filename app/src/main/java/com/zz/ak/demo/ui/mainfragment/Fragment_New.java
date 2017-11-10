package com.zz.ak.demo.ui.mainfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.Person;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.QueryTool;
import com.zz.ak.demo.ui.BaseFragment;
import com.zz.ak.demo.ui.adapters.TextListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RJYX on 2017/11/10.
 */

public class Fragment_New extends BaseFragment implements TimeInterface {
    private String TAG = "Fragment_New";
    private Context mContext;
    private List<Person> personList = new ArrayList<>();
    private QueryTool queryTool;
    private BmobApplication application;
    private RecyclerView mRvTextList;
    private TextListAdapter textListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        View view = inflater.inflate(R.layout.fragment_new, null);
        mRvTextList = view.findViewById(R.id.rv_text_list);
        textListAdapter = new TextListAdapter(this.getActivity(),null);
        mRvTextList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRvTextList.setAdapter(textListAdapter);
        mRvTextList.setNestedScrollingEnabled(false);
        return view;
    }

    public static Fragment_New newInstance() {
        Bundle args = new Bundle();
        Fragment_New fragment = new Fragment_New();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryTool = new QueryTool(this.getContext(),this);
        application = new BmobApplication();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            setData();
        }catch (Exception e){
            Log.e(TAG, "setData: ",e );
        }
    }

    public void setData() {
        if (application.personMsgList!=null && application.personMsgList.size()>0){
            textListAdapter.upData(application.personMsgList);
        }else {
            queryTool.queryAllPersonMsgUp();
        }
    }

    public void getNewListData() {
        showloading();
    }


    //获取新数据成功
    @Override
    public void getNewData() {
        closeloading();
        showToast("获取成功");
        try {
            textListAdapter.upData(application.personMsgList);
        }catch (Exception e){
            Log.e(TAG, "getNewData: ",e );
        }
    }

    //获取数据失败
    @Override
    public void getNewDataError() {
        closeloading();
        showToast("获取数据失败");
    }


}