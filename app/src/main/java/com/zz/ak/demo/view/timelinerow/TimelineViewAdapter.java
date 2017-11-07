package com.zz.ak.demo.view.timelinerow;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.Person;

import java.util.ArrayList;
import java.util.List;


public class TimelineViewAdapter extends ArrayAdapter<Person> {

    private Context context;
    private Resources res;
    private List<Person> DataList;
    private String AND;


    public TimelineViewAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.context = context;
        res = context.getResources();
        AND = res.getString(R.string.AND);
        this.DataList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = DataList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ctimeline_row, null);
        TextView rowDate = (TextView) view.findViewById(R.id.crowDate);
        TextView rowTitle = (TextView) view.findViewById(R.id.crowTitle);
        TextView rowDescription = (TextView) view.findViewById(R.id.crowDesc);
        ImageView rowImage = (ImageView) view.findViewById(R.id.crowImg);
        View rowUpperLine = (View) view.findViewById(R.id.crowUpperLine);
        View rowLowerLine = (View) view.findViewById(R.id.crowLowerLine);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        return view;
    }
}
