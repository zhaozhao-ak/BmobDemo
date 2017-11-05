package com.zz.ak.demo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/3.
 */

public class Users extends BmobObject {

    private String Name;
    private int Id;
    private int Number;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }



}
