package com.zz.ak.demo.ui.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zz.ak.demo.R;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.QueryTool;
import com.zz.ak.demo.ui.BaseFragment;
import com.zz.ak.demo.ui.LoginActivity;

/**
 * Created by RJYX on 2017/11/10.
 */

public class Fragment_me extends BaseFragment implements TimeInterface {
    TextView textView;
    private String mText;
    Button btn_tuiChu,btn_shuaXin;
    private QueryTool queryTool;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        btn_tuiChu = view.findViewById(R.id.btn_tuiChu);
        btn_shuaXin= view.findViewById(R.id.btn_shuaXin);
        btn_tuiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("userInfo", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("user");
                editor.remove("password");
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        btn_shuaXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showloading();
                queryTool.queryAllPerson();
                queryTool.queryAllPersonMsg();
            }
        });
        return view;
    }

    public static Fragment_me newInstance() {
        Bundle args = new Bundle();
        Fragment_me fragment = new Fragment_me();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryTool = new QueryTool(this.getContext(),this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void getNewData() {
        closeloading();
        showToast("刷新成功");

    }

    @Override
    public void getNewDataError() {
        closeloading();
        showToast("刷新失败");

    }
}