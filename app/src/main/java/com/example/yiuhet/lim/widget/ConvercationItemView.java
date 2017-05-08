package com.example.yiuhet.lim.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yiuhet.lim.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yiuhet on 2017/5/8.
 */

public class ConvercationItemView extends RelativeLayout {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUsernametx;
    @BindView(R.id.last_message)
    TextView mLastMessagetx;
    @BindView(R.id.timestamp)
    TextView mTimestamptx;
    @BindView(R.id.unread_count)
    TextView mUnreadCounttx;
    @BindView(R.id.conversation_item_container)
    RelativeLayout mConversationItemContainer;

    public ConvercationItemView(Context context) {
        //super(context);
        this(context, null);
    }

    public ConvercationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_convercation_item, this);
        ButterKnife.bind(this,this);
    }

    public void bindView(final EMConversation emconversation) {
        EMMessage emmessage = emconversation.getLastMessage();
        mUsernametx.setText(emmessage.getUserName());
        updateLastMessage(emmessage);
        updateUnreadCount(emconversation);
        mConversationItemContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //getContext().startActivity();
            }
        });
    }

    private void updateUnreadCount(EMConversation emconversation) {
        int unreadMsgCount = emconversation.getUnreadMsgCount();
        if (unreadMsgCount > 0) {
            mUnreadCounttx.setVisibility(VISIBLE);
            mUnreadCounttx.setText(String.valueOf(unreadMsgCount));
        } else {
            mUnreadCounttx.setVisibility(GONE);
        }
    }

    private void updateLastMessage(EMMessage emmessage) {
        if (emmessage.getBody() instanceof EMTextMessageBody) {
            mLastMessagetx.setText(((EMTextMessageBody) emmessage.getBody()).getMessage());
        } else {
            mLastMessagetx.setText("非文本信息");
        }
        mTimestamptx.setText(DateUtils.getTimestampString(new Date(emmessage.getMsgTime())));
    }
}
