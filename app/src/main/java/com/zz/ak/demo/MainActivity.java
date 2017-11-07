package com.zz.ak.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zz.ak.demo.bean.Ak_48;
import com.zz.ak.demo.bean.BankCard;
import com.zz.ak.demo.bean.MyUser;
import com.zz.ak.demo.bean.Person;
import com.zz.ak.demo.bean.Users;
import com.zz.ak.demo.tool.QueryTool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Subscriber;

public class MainActivity extends BaseActivity {

    private Button btn_add,btn_update,btn_delete,btn_query,
    login,SignUp,btn_getalluser,btn_LogOut,btn_getMyUser;
    private EditText et_username,et_password;
    private TextView tv_msg;
    private int i = 1;
    private QueryTool queryTool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        viewOnClick();
        //初始化数据
        getNewView();
    }

    private void init() {
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_query = findViewById(R.id.btn_query);
        tv_msg = findViewById(R.id.tv_msg);
        login = findViewById(R.id.login);
        SignUp = findViewById(R.id.SignUp);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_getMyUser = findViewById(R.id.btn_getMyUser);
        btn_LogOut = findViewById(R.id.btn_LogOut);
        btn_getalluser = findViewById(R.id.btn_getalluser);
    }

    public void getNewView() {
//        queryTool = new QueryTool();
    }

    private void viewOnClick() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                Ak_48 ak48 = new Ak_48();
                ak48.setName("搭设"+i);
                addSubscription(ak48.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            toast("添加ak48数据成功，返回objectId为：" + s);
                            tv_msg.setText("添加ak48数据成功，返回objectId为：" + s);
                        } else {
                            toast("创建ak48数据失败");
                            tv_msg.setText("创建ak48数据失败");
                        }
                    }
                    }));
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Users users = new Users();
                users.setName("上单");
                BmobQuery<Users> bmobQuery = new BmobQuery<Users>();

                addSubscription(bmobQuery.getObject("qzuA999K", new QueryListener<Users>() {
                    @Override
                    public void done(Users users, BmobException e) {
                        if(e==null){
                            toast("查询成功"+users.getUpdatedAt());
                            tv_msg.setText("查询成功"+users.getUpdatedAt());
                        }else{
                            toast("查询失败：" + e.getMessage());
                            tv_msg.setText("查询失败");

                        }
                    }
                }));
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Users users = new Users();
                users.setNumber(99999);
                users.setName("大神");
                addSubscription(users.update("f018040782", new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            toast("更新成功:"+users.getUpdatedAt());
                            tv_msg.setText("更新成功:"+users.getUpdatedAt());

                        }else{
                            toast("更新失败：" + e.getMessage());
                            tv_msg.setText("更新失败");

                        }
                    }
                }));
            }
        });
        //删除
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Users users = new Users();
                users.setObjectId("Shbg0007");
                addSubscription(users.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            toast("删除成功:"+users.getUpdatedAt());
                            tv_msg.setText("删除成功:"+users.getUpdatedAt());

                        }else{
                            toast("删除失败：" + e.getMessage());
                            tv_msg.setText("删除失败");

                        }
                    }
                }));
            }
        });
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BmobUser user = new BmobUser();
                user.setUsername(et_username.getText().toString().trim());
                user.setPassword(et_password.getText().toString().trim());
                if (!et_username.getText().toString().equals("") && !et_password.getText().toString().equals("") ){
                    user.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
                        @Override
                        public void onCompleted() {
                            log("----onCompleted----");
                        }
                        @Override
                        public void onError(Throwable e) {
                            loge(new BmobException(e));
                            toast("登陆失败");
                            log("zhao----onCompleted----");
                        }
                        @Override
                        public void onNext(BmobUser bmobUser) {
                            toast(bmobUser.getUsername() + "登陆成功");
                            tv_msg.setText("登陆成功:"+bmobUser.getUsername());
                        }
                    });
                }else {
                    toast("账号密码不能为空");

                }

            }
        });
        //注册
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MyUser myUser = new MyUser();
                myUser.setUsername(et_username.getText().toString().trim());
                myUser.setPassword(et_password.getText().toString().trim());
                myUser.setAge(18);
                if (!et_username.getText().toString().equals("") && !et_password.getText().toString().equals("") ){
                    addSubscription(myUser.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser s, BmobException e) {
                            if (e == null) {
                                toast("注册成功:" + s.toString());
                                tv_msg.setText("登陆成功:"+s.toString());
                            } else {
                                loge(e);
                                toast("注册失败:" + e);
                            }
                        }
                    }));
                }else {
                    toast("用户密码不能为空");
                }

            }
        });

        //获取当前用户资料
        btn_getMyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testGetCurrentUser();
            }
        });

        /**
         * 清除本地用户
         */
        btn_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
            }
        });


//        /**
//         * 获取所有用户信息
//         */
//        btn_getalluser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                queryTool.queryObjectsByTable();
//
//                queryTool.queryObjects();
//            }
//        });


    }



    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    private void updateUser() {
        final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        if (bmobUser != null) {
            final MyUser newUser = new MyUser();
            //-----------------------普通setter操作-------------------------------
            //number类型
            newUser.setAge(25);
            newUser.setSex(false);
            //object类型
//			newUser.setMainCard(new BankCard("工行", "10086"));
            //BmobObject类型
            Person person = new Person();
            person.setObjectId("721fe0cdf2");
            newUser.setBanker(person);
            //---------------------数组操作(add、addAll、addUnique、addAllUnique)---------------------------------------
            //添加Object类型的数组,Object数组调用addAllUnique、addUnique方法后本地用户信息未支持去重
            List<BankCard> cards = new ArrayList<BankCard>();
            cards.add(new BankCard("建行", "111"));
            newUser.addAll("cards", cards);
//			添加String类型的数组--String数组支持去重
            newUser.addAllUnique("hobby", Arrays.asList("游泳"));
            //----------------------自增操作---------------------------------------
//			newUser.increment("num",-2);
//			//----------------------setValue方式更新用户信息（必须先保证更新的列存在，否则会报internal error）----------------------------
//			//更新number
//			newUser.setValue("age",25);
//			//更新整个Object
//			newUser.setValue("banker",person);
//			//更新String数组
//			newUser.setValue("hobby",Arrays.asList("看书","游泳"));
////			//更新某个Object的值
//			newUser.setValue("mainCard.cardNumber","10011");
//			//更新数组中某个Object
//			newUser.setValue("cards.0", new BankCard("工行", "10086"));
            //更新数组中某个Object的某个字段的值
//			newUser.setValue("cards.0.bankName", "中行");
            addSubscription(newUser.update(bmobUser.getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        testGetCurrentUser();
                    } else {
                        loge(e);
                    }
                }
            }));
        } else {
            toast("本地用户为null,请登录。");
        }
    }


    /**
     * 获取本地用户
     */
    private void testGetCurrentUser() {
//		MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
//		if (myUser != null) {
//			log("本地用户信息:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername()
//					+ ",age = "+ myUser.getAge());
//		} else {
//			toast("本地用户为null,请登录。");
//		}
        //V3.4.5版本新增加getObjectByKey方法获取本地用户对象中某一列的值
        String username = (String) BmobUser.getObjectByKey("username");
        Integer age = (Integer) BmobUser.getObjectByKey("age");
        Boolean sex = (Boolean) BmobUser.getObjectByKey("sex");
        JSONArray hobby = (JSONArray) BmobUser.getObjectByKey("hobby");
        JSONArray cards = (JSONArray) BmobUser.getObjectByKey("cards");
        JSONObject banker = (JSONObject) BmobUser.getObjectByKey("banker");
        JSONObject mainCard = (JSONObject) BmobUser.getObjectByKey("mainCard");
        log("username：" + username + ",\nage：" + age + ",\nsex：" + sex);
        log("hobby:" + (hobby != null ? hobby.toString() : "为null") + "\ncards:" + (cards != null ? cards.toString() : "为null"));
        log("banker:" + (banker != null ? banker.toString() : "为null") + "\nmainCard:" + (mainCard != null ? mainCard.toString() : "为null"));
    }

    /**
     * 验证旧密码是否正确
     *
     * @param
     * @return void
     */
    private void checkPassword() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        // 如果你传的密码是正确的，那么arg0.size()的大小是1，这个就代表你输入的旧密码是正确的，否则是失败的
        query.addWhereEqualTo("password", "123456");
        query.addWhereEqualTo("username", bmobUser.getUsername());
        addSubscription(query.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
                    toast("查询密码成功:" + object.size());
                } else {
                    loge(e);
                }
            }

        }));
    }

    /**
     * 重置密码
     */
    private void testResetPasswrod() {
        final String email = "123456789@qq.com";
        addSubscription(BmobUser.resetPasswordByEmail(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                } else {
                    loge(e);
                }
            }
        }));
    }

    /**
     * 查询用户
     */
    private void testFindBmobUser() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", "lucky");
        addSubscription(query.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
                    toast("查询用户成功：" + object.size());
                } else {
                    loge(e);
                }
            }

        }));
    }


}
