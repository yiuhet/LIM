package com.example.yiuhet.lim.presenter.imp1;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.presenter.ConversationPresenter;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.ConversationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by yiuhet on 2017/5/8.
 */

public class ConversationPresenterImp1 extends BasePresenter<ConversationView> implements ConversationPresenter {
    private ConversationView mConversationView;
    private List<EMConversation> mEMConversations;

    public ConversationPresenterImp1(ConversationView mConversationView) {
        this.mConversationView = mConversationView;
        mEMConversations = new ArrayList<EMConversation>();
    }

    @Override
    public void loadAllConversations() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
                mEMConversations.clear();
                mEMConversations.addAll(conversationMap.values());
                Collections.sort(mEMConversations, new Comparator<EMConversation>() {
                    @Override
                    public int compare(EMConversation o1, EMConversation o2) {
                        return (int) (o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime());
                    }
                });
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConversationView.onAllConversationsLoad();
                    }
                });
            }

        });
    }

    @Override
    public List<EMConversation> getConversations() {
        return mEMConversations;
    }
}
