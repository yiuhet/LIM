package com.example.yiuhet.lim.view;

/**
 * Created by yiuhet on 2017/5/9.
 */

public interface ChatView {

    void onStartSendMessage();

    void onSendMessageSuccess();

    void onSendMessageFailed(String error);

    void onMessageLoad();

    void onMoreMessageLoad(int size);

    void onNoMoreData();
}
