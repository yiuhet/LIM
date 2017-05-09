package com.example.yiuhet.lim.presenter.imp1;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.presenter.ChatPresenter;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.ChatView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiuhet on 2017/5/9.
 */

public class ChatPresenterImp1 extends BasePresenter<ChatView> implements ChatPresenter{

    public static final int DEFAULT_PAGE_SIZE = 20;

    private ChatView mChatView;
    private List<EMMessage> mEmmessages;
    private boolean isMore = true;

    public ChatPresenterImp1(ChatView chatView) {
        mChatView = chatView;
        mEmmessages = new ArrayList<>();
    }
    @Override
    public void loadMessages(final String userName) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
                if (conversation != null) {
                    List<EMMessage> messages = conversation.getAllMessages();
                    mEmmessages.addAll(messages);
                    conversation.markAllMessagesAsRead();
                }
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onMessageLoad();
                    }
                });
            }
        });
    }

    @Override
    public void loadMoreMessages(final String userName) {
        if (isMore) {
            ThreadUtils.runOnBackgroundThread(new Runnable() {
                @Override
                public void run() {
                    EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
                    EMMessage firstMessage = mEmmessages.get(0);

                    final List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(firstMessage.getMsgId(), DEFAULT_PAGE_SIZE);
                    isMore = (emMessages.size() == DEFAULT_PAGE_SIZE);
                    mEmmessages.addAll(0, emMessages);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatView.onMoreMessageLoad(emMessages.size());
                        }
                    });

                }
            });
        } else {
            mChatView.onNoMoreData();
        }
    }

    @Override
    public List<EMMessage> getMessages() {
        return mEmmessages;
    }

    @Override
    public void isRead(final String userNmae) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userNmae);
                conversation.markAllMessagesAsRead();
            }
        });
    }

    @Override
    public void sendMessage(final String userName, final String message) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                EMMessage emMessage = EMMessage.createTxtSendMessage(message,userName);
                emMessage.setStatus(EMMessage.Status.INPROGRESS);
                emMessage.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mChatView.onSendMessageSuccess();
                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {
                        mChatView.onSendMessageFailed(error);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
                mEmmessages.add(emMessage);
                EMClient.getInstance().chatManager().saveMessage(emMessage);
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onStartSendMessage();
                    }
                });
            }
        });
    }
}
