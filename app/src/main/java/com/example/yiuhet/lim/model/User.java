package com.example.yiuhet.lim.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by yiuhet on 2017/5/4.
 */

public class User extends BmobUser{

    public User(String userName, String password) {
        setUsername(userName);
        setPassword(password);
    }
}
