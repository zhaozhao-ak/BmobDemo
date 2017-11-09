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

import com.bumptech.glide.Glide;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean._User;
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

    public void setData() {
        if (application.personList==null || application.personList.size()==0 ){
            showloading();
            queryTool.queryAllPerson();
            queryTool.queryAllPersonMsg();
        }else {
            setListData();
        }
    }

    public void getAllPerson() {

    }
    public void setListData() {
        List<_User> userList = new ArrayList<>();
        userList = setUserData(application.personList);
        ArrayList<String> customLetters = new ArrayList<>();
        letters.clear();
        letters = new HashMap<>();
        int position = 0;
        for(_User person: userList){
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
        adapter.addAll(userList);
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
                setListData();
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
            _User user = getItem(position);
            TextView tv_name = (TextView) holder.itemView.findViewById(R.id.tv_name);
            tv_name.setText(user.getUsername());
            ImageView iv_head = holder.itemView.findViewById(R.id.iv_head);
//            iv_head.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.user));

            if (getItem(position).getPic()!=null){
                Glide.with(mContext).load(user.getPic().getFileUrl())
                        .placeholder(R.mipmap.ic_cat) //设置占位图，在加载之前显示
                        .error(R.mipmap.ic_cat) //在图像加载失败时显示
                        .into(iv_head);
            }else {
                Glide.with(mContext).load(R.mipmap.ic_cat)
                        .error(R.mipmap.ic_cat) //在图像加载失败时显示
                        .into(iv_head);
            }

            TextView add_msg = (TextView) holder.itemView.findViewById(R.id.add_msg);
            if (TextUtils.isEmpty(user.getState()) ){
                add_msg.setVisibility(View.GONE);
            }else {
                add_msg.setVisibility(View.VISIBLE);
                add_msg.setText(user.getState());
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



    private List<_User> setUserData(List<_User> userInfos){
        List<_User>  machienUser = new ArrayList<>();

        List<_User> listUserA = new ArrayList<>();
        List<_User> listUserB = new ArrayList<>();
        List<_User> listUserC = new ArrayList<>();
        List<_User> listUserD = new ArrayList<>();
        List<_User> listUserE = new ArrayList<>();
        List<_User> listUserF = new ArrayList<>();
        List<_User> listUserG = new ArrayList<>();
        List<_User> listUserH = new ArrayList<>();
        List<_User> listUserI = new ArrayList<>();
        List<_User> listUserJ = new ArrayList<>();
        List<_User> listUserK = new ArrayList<>();
        List<_User> listUserL = new ArrayList<>();
        List<_User> listUserM = new ArrayList<>();
        List<_User> listUserN = new ArrayList<>();
        List<_User> listUserO = new ArrayList<>();
        List<_User> listUserP = new ArrayList<>();
        List<_User> listUserQ = new ArrayList<>();
        List<_User> listUserR = new ArrayList<>();
        List<_User> listUserS = new ArrayList<>();
        List<_User> listUserT = new ArrayList<>();
        List<_User> listUserU = new ArrayList<>();
        List<_User> listUserV = new ArrayList<>();
        List<_User> listUserW = new ArrayList<>();
        List<_User> listUserX = new ArrayList<>();
        List<_User> listUserY = new ArrayList<>();
        List<_User> listUserZ = new ArrayList<>();

        for (_User user: userInfos){
            if (user.getFirstLetter().equalsIgnoreCase("A")){
                listUserA.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("B")){
                listUserB.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("C")){
                listUserC.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("D")){
                listUserD.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("E")){
                listUserE.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("F")){
                listUserF.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("G")){
                listUserG.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("H")){
                listUserH.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("I")){
                listUserI.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("J")){
                listUserJ.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("K")){
                listUserK.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("L")){
                listUserL.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("M")){
                listUserM.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("N")){
                listUserN.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("O")){
                listUserO.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("P")){
                listUserP.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("Q")){
                listUserQ.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("R")){
                listUserR.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("S")){
                listUserS.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("T")){
                listUserT.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("U")){
                listUserU.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("V")){
                listUserV.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("W")){
                listUserW.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("X")){
                listUserX.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("Y")){
                listUserY.add(user);
            }if (user.getFirstLetter().equalsIgnoreCase("Z")){
                listUserZ.add(user);
            }
        }
        if (listUserA.size()>0){
            machienUser.addAll(listUserA);
        }if (listUserB.size()>0){
            machienUser.addAll(listUserB);
        }if (listUserC.size()>0){
            machienUser.addAll(listUserC);

        }if (listUserD.size()>0){
            machienUser.addAll(listUserD);

        }if (listUserE.size()>0){
            machienUser.addAll(listUserE);

        }if (listUserF.size()>0){
            machienUser.addAll(listUserF);

        }if (listUserG.size()>0){
            machienUser.addAll(listUserG);

        }if (listUserH.size()>0){
            machienUser.addAll(listUserH);

        }if (listUserI.size()>0){
            machienUser.addAll(listUserI);

        }if (listUserJ.size()>0){
            machienUser.addAll(listUserJ);

        }if (listUserK.size()>0){
            machienUser.addAll(listUserK);

        }if (listUserL.size()>0){
            machienUser.addAll(listUserL);

        }if (listUserM.size()>0){
            machienUser.addAll(listUserM);

        }if (listUserN.size()>0){
            machienUser.addAll(listUserN);

        }if (listUserO.size()>0){
            machienUser.addAll(listUserO);

        }if (listUserP.size()>0) {
            machienUser.addAll(listUserP);

        }if (listUserQ.size()>0){
            machienUser.addAll(listUserQ);

        }if (listUserR.size()>0){
            machienUser.addAll(listUserR);

        }if (listUserS.size()>0){
            machienUser.addAll(listUserS);

        }if (listUserT.size()>0){
            machienUser.addAll(listUserT);

        }if (listUserU.size()>0){
            machienUser.addAll(listUserU);

        }if (listUserV.size()>0){
            machienUser.addAll(listUserV);

        }if (listUserW.size()>0){
            machienUser.addAll(listUserW);

        }if (listUserX.size()>0){
            machienUser.addAll(listUserX);

        }if (listUserY.size()>0){
            machienUser.addAll(listUserY);

        }if (listUserZ.size()>0){
            machienUser.addAll(listUserZ);

        }

        return machienUser;
    }

}
