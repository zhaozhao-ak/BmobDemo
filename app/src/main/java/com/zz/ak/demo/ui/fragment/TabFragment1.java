package com.zz.ak.demo.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.Person;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.QueryTool;
import com.zz.ak.demo.tool.bar.CityListAdapter;
import com.zz.ak.demo.tool.bar.DividerDecoration;
import com.zz.ak.demo.tool.bar.unit.QuickSideBarTipsView;
import com.zz.ak.demo.tool.bar.unit.QuickSideBarView;
import com.zz.ak.demo.tool.bar.unit.listener.OnQuickSideBarTouchListener;
import com.zz.ak.demo.ui.BaseFragment;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chengxi on 17/4/26.
 */
public class TabFragment1 extends BaseFragment implements OnQuickSideBarTouchListener,TimeInterface {
    private String TAG = "TabFragment1";

    RecyclerView recyclerView;
    HashMap<String,Integer> letters = new HashMap<>();
    QuickSideBarView quickSideBarView;
    QuickSideBarTipsView quickSideBarTipsView;
    CityListWithHeadersAdapter adapter;
    private QueryTool queryTool;
    private Context mContext;
    private BmobApplication application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = this.getContext();
        View view =  inflater.inflate(R.layout.fragment_tab_1, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        quickSideBarView = (QuickSideBarView) view.findViewById(R.id.quickSideBarView);
        quickSideBarTipsView = (QuickSideBarTipsView) view.findViewById(R.id.quickSideBarTipsView);
        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);
        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        adapter = new CityListWithHeadersAdapter();

        recyclerView.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new DividerDecoration(this.getContext()));
        System.out.println("zhao--------执行--66666666------");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("zhao--------执行--------");
        queryTool = new QueryTool(this.getContext(),this);
        application = new BmobApplication();
        setData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setData() {
        if (application.personList==null || application.personList.size()==0 ){
            showloading();
            queryTool.queryAllPerson();
            queryTool.queryAllPersonMsg();
        }else {
            setData(application.personList);
        }
    }

    public void getAllPerson() {

    }
    private void setData(List<Person> personList) {

        ArrayList<String> customLetters = new ArrayList<>();
        letters = new HashMap<>();
        int position = 0;
        for(Person person: personList){
            String letter = person.getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if(!letters.containsKey(letter)){
                letters.put(letter,position);
                customLetters.add(letter);
            }
            position++;
        }

        //不自定义则默认26个字母
        quickSideBarView.setLetters(customLetters);
        adapter.addAll(personList);
    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if(letters.containsKey(letter)) {
            recyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching? View.VISIBLE:View.INVISIBLE);
    }

    //获取数据成功
    @Override
    public void getNewData() {
        closeloading();

        try {
            BmobApplication application = new BmobApplication();
            if (application.personList!=null && application.personList.size()>0){
                setData(application.personList);
            }
        }catch (Exception e){
            Log.e(TAG, "getNewData: ",e );
        }

        showToast("加载成功");
    }
    //获取数据失败
    @Override
    public void getNewDataError() {
        closeloading();
        showToast("获取人员资料失败");
    }

    private class CityListWithHeadersAdapter extends CityListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_item, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView tv_name = (TextView) holder.itemView.findViewById(R.id.tv_name);
            tv_name.setText(getItem(position).getName());
            ImageView iv_head = holder.itemView.findViewById(R.id.iv_head);
            iv_head.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_0));
            TextView add_msg = (TextView) holder.itemView.findViewById(R.id.add_msg);
            if (TextUtils.isEmpty(getItem(position).getState()) ){
                add_msg.setVisibility(View.GONE);
            }else {
                add_msg.setVisibility(View.VISIBLE);
                add_msg.setText(getItem(position).getState());
            }
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).getFirstLetter()==null?0:getItem(position).getFirstLetter().charAt(0);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(String.valueOf(getItem(position).getFirstLetter()));
//            holder.itemView.setBackgroundColor(getRandomColor());
        }

        private int getRandomColor() {
            SecureRandom rgen = new SecureRandom();
            return Color.HSVToColor(150, new float[]{
                    rgen.nextInt(359), 1, 1
            });
        }

    }
}
