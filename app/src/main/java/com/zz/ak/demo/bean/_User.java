package com.zz.ak.demo.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/9.
 */

public class _User extends BmobUser {
    private Integer age;   //年龄
    private Boolean gender; //性别
    private BmobFile pic;   //头像
    private BmobDate uploadTime; //更新的最新时间
    private String firstLetter;
    private String state;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    public BmobDate getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(BmobDate uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
