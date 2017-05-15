package com.example.yiuhet.lim.view;

/**
 * Created by yiuhet on 2017/5/11.
 */

public interface AddFriendView {

    void onStartAddFriend();

    void onAddSuccess();

    void onAddFailed(String error);

    void onStartFind();

    void onFoundSuccess();

    void onNotFound();
}
