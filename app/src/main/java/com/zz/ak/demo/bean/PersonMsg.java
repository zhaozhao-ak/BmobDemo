package com.zz.ak.demo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by RJYX on 2017/11/8.
 */

public class PersonMsg extends BmobObject {

    private String PersonId;
    private String PersonMsg;
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPersonId() {
        return PersonId;
    }
    public void setPersonId(String personId) {
        PersonId = personId;
    }
    public String getPersonMsg() {
        return PersonMsg;
    }
    public void setPersonMsg(String personMsg) {
        PersonMsg = personMsg;
    }

}
