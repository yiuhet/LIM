package com.example.yiuhet.lim.widget;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yiuhet.lim.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yiuhet on 2017/5/9.
 */

public class SendMessageItemView extends RelativeLayout {
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.send_progress)
    ContentLoadingProgressBar mSendProgress;

    public SendMessageItemView(Context context) {
        this(context, null);
    }

    public SendMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_item, this);
        ButterKnife.bind(this,this);
    }

    public void binView(EMMessage emMessage, boolean showTime) {
        updateTime(emMessage, showTime);
        updateMessageBody(emMessage);
        updateSendStatus(emMessage);
    }

    private void updateSendStatus(EMMessage emMessage) {
        switch (emMessage.status()) {
            case INPROGRESS:
                mSendProgress.show();
                break;
            case SUCCESS:
                mSendProgress.hide();
                break;
            case FAIL:
                mSendProgress.show();
        }
    }

    private void updateMessageBody(EMMessage emMessage) {
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            mSendTv.setText(((EMTextMessageBody) body).getMessage());
        } else {
            mSendTv.setText("非文本信息");
        }
    }

    private void updateTime(EMMessage emMessage, boolean showTime) {
        if (showTime) {
            mTimeTv.setVisibility(VISIBLE);
            mTimeTv.setText(DateUtils.getTimestampString(new Date(emMessage.getMsgTime())));
        } else {
            mTimeTv.setVisibility(GONE);
        }
    }
}
