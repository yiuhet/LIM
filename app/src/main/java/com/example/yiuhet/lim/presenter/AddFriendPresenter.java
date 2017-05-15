package com.example.yiuhet.lim.presenter;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.model.Contact;
import com.example.yiuhet.lim.model.User;
import com.example.yiuhet.lim.view.AddFriendView;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/11.
 */

public interface AddFriendPresenter {

    void queryUserFromBomb(String userNmae);

    List<String> getQueryContact();

    void addFriend(String userName,String info);

}
