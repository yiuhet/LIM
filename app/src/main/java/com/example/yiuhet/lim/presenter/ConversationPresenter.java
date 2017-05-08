package com.example.yiuhet.lim.presenter;

import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/8.
 */

public interface ConversationPresenter {
    void loadAllConversations();

    List<EMConversation> getConversations();

}
