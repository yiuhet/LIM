package com.example.yiuhet.lim.model;

import com.example.yiuhet.lim.R;

import java.util.UUID;

/**
 * Created by yiuhet on 2017/5/10.
 */

public class Contact {
    public Contact() {
    }
    public Contact(String id,String userName) {
        this.id = id;
        this.userName = userName;
    }
    public String id;

    public int imageId = R.drawable.login_heard;

    public String userName;

}
