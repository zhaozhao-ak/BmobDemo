package com.zz.ak.demo.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

import com.zz.ak.demo.BaseActivity;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.Person;
import com.zz.ak.demo.interfaceview.TimeInterface;
import com.zz.ak.demo.tool.QueryTool;
import com.zz.ak.demo.view.timelinerow.TimelineRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimelineActivity extends BaseActivity implements TimeInterface {

    //Create Timeline Rows List
    private ArrayList<Person> personList = new ArrayList<>();
    private Context mContext;
    private QueryTool queryTool;
    private ListView myListView;
    private TimeInterface timeInterface;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_test);
        mContext = this;
        init();
        //获取数据
        getData();
        //填充数据
        setData();

    }



    public void init() {
        myListView = (ListView) findViewById(R.id.timeline_listView);
        queryTool = new QueryTool(mContext,timeInterface);
        timeInterface = new TimelineActivity();
    }
    //获取最新信息
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getData() {
        showloading();
        queryTool.queryObjects();

        for (int i = 0; i < 15; i++) {
            //add the new row to the list
        }


    }
    //查询到最新的数据
    @Override
    public void getNewData() {
        setData();
        closeloading();
    }

    //查询数据失败
    @Override
    public void getNewDataError() {

    }


    private void setData(){

    }



    //绘制布局
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private TimelineRow createRandomTimelineRow(int id) {

        // Create new timeline row (pass your Id)
        TimelineRow myRow = new TimelineRow(id);

        //to set the row Date (optional)
        myRow.setDate(getRandomDate());
        //to set the row Title (optional)
        myRow.setTitle("Title " + id);
        //to set the row Description (optional)
        myRow.setDescription("Description " + id);
        //头像
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.img_0 + getRandomNumber(1, 1)));
        //线颜色
        myRow.setBellowLineColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        //线粗细(2, 25)
        myRow.setBellowLineSize(3);
        //头像大小(25, 40)
        myRow.setImageSize(25);
        //to set background color of the row image (optional)
        myRow.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        //(25, 40)
        myRow.setBackgroundSize(25);
        //时间颜色
        myRow.setDateColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        //Title 颜色
        myRow.setTitleColor(getRandomColor());
        //描述颜色
        myRow.setDescriptionColor(getRandomColor());

        return myRow;
    }


    //Random Methods
    public int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ;
        return color;
    }

    public int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * max);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Date getRandomDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        Date endDate = new Date();
        try {
            startDate = sdf.parse("02/09/2015");
            long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            endDate = new Date(random);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }



}
