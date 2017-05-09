package com.example.yiuhet.lim.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yiuhet.lim.MVPBaseActivity;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.adapter.MessageListAdapter;
import com.example.yiuhet.lim.presenter.imp1.ChatPresenterImp1;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.ChatView;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends MVPBaseActivity<ChatView, ChatPresenterImp1> implements ChatView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.msg_edt)
    EditText MsgEdt;
    @BindView(R.id.btn_send)
    Button mSendbtn;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private String mUsername;

    private LinearLayoutManager mLinearLayoutManager;

    private MessageListAdapter mMessageListAdapter;


    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        mUsername = getIntent().getStringExtra("user_name");
        initToolBar();
        //监控确认行为
        MsgEdt.setOnEditorActionListener(mOnEditorActionListener);
        //如果输入框含有字符则使发送按钮启动
        MsgEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSendbtn.setEnabled(s.length() != 0);
            }
        });

        initRecycleView();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
        mPresenter.loadMessages(mUsername);
    }

    private void initRecycleView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mMessageListAdapter = new MessageListAdapter(this, mPresenter.getMessages());
        mRecycleView.setAdapter(mMessageListAdapter);

    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("与" + mUsername + "聊天中");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected ChatPresenterImp1 createPresenter() {
        return new ChatPresenterImp1(this);
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        sendMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        }
    };

    private void sendMessage() {
        mPresenter.sendMessage(mUsername, MsgEdt.getText().toString());
        MsgEdt.getText().clear();
    }

    @Override
    public void onStartSendMessage() {
        updateList();
    }

    private void updateList() {
        mMessageListAdapter.notifyDataSetChanged();
        smoothScrollToBottom();
    }

    @Override
    public void onSendMessageSuccess() {
        updateList();
    }

    @Override
    public void onSendMessageFailed(String error) {

    }

    @Override
    public void onMessageLoad() {
        mMessageListAdapter.notifyDataSetChanged();
        mRecycleView.scrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    @Override
    public void onMoreMessageLoad(int size) {
        mMessageListAdapter.notifyDataSetChanged();
        mRecycleView.scrollToPosition(size);
    }

    @Override
    public void onNoMoreData() {

    }
    private void smoothScrollToBottom() {
        mRecycleView.smoothScrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    private EMMessageListener mEMMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EMMessage msg = messages.get(0);
                    if (msg.getUserName().equals(mUsername)){
                        mPresenter.isRead(mUsername);
                        mMessageListAdapter.addNewMessage(msg);
                        smoothScrollToBottom();
                    }
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListener);

    }
}
