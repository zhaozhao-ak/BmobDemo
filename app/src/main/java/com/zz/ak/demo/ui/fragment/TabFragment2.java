package com.zz.ak.demo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by chengxi on 17/4/26.
 */
public class TabFragment2 extends BaseFragment implements TimeInterface {

    private Context mContext;
    private List<Person> personList = new ArrayList<>();
    private QueryTool queryTool;
    private BmobApplication application;
    private RecyclerView mRvTextList;
    private TextListAdapter textListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = this.getContext();
        View view = inflater.inflate(R.layout.fragment_tab_2, null);
        mRvTextList = view.findViewById(R.id.rv_text_list);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryTool = new QueryTool(this.getContext(),this);
        application = new BmobApplication();
        textListAdapter = new TextListAdapter(this.getActivity(),null);
        mRvTextList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRvTextList.setAdapter(textListAdapter);
        setData();
    }

    private void setData() {
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
        textListAdapter.upData(application.personMsgList);
    }

    //获取数据失败
    @Override
    public void getNewDataError() {
        closeloading();
        showToast("获取数据失败");
    }
}
