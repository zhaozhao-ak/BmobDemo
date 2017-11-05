package com.zz.ak.demo.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.zz.ak.demo.R;
import com.zz.ak.demo.view.timelinerow.TimelineRow;
import com.zz.ak.demo.view.timelinerow.TimelineViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimelineActivity extends AppCompatActivity {

    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_test);
        mContext = this;

        //填充数据
        for (int i = 0; i < 15; i++) {
            //add the new row to the list
            timelineRowsList.add(createRandomTimelineRow(i));
        }

        //Adapter true按日期排序
        myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,true);

        ListView myListView = (ListView) findViewById(R.id.timeline_listView);
        myListView.setAdapter(myAdapter);
        //if you wish to handle list scrolling
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    ////on scrolling to end of the list, add new rows
                    for (int i = 0; i < 15; i++) {
                        myAdapter.add(createRandomTimelineRow(i));
                    }

                }
            }


        });

        //item点击事件
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = timelineRowsList.get(position);
                Toast.makeText(TimelineActivity.this, row.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };
        myListView.setOnItemClickListener(adapterListener);


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
