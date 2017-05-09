package com.example.yiuhet.lim.widget;

import android.content.Context;
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

public class ReceiveMessageItemView extends RelativeLayout {


    @BindView(R.id.time_tv1)
    TextView mTimeTv;
    @BindView(R.id.receive_tv)
    TextView mReceiveTv;

    public ReceiveMessageItemView(Context context) {
        this(context, null);
    }

    public ReceiveMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_receive_item, this);
        ButterKnife.bind(this,this);
        //mReceiveTv = (TextView) findViewById(R.id.receive_tv);

    }

    public void binView(EMMessage emMessage, boolean showTime) {
        updateTime(emMessage, showTime);
        updateMessageBody(emMessage);
    }

    private void updateMessageBody(EMMessage emMessage) {
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            mReceiveTv.setText(((EMTextMessageBody) body).getMessage());
        } else {
            mReceiveTv.setText("非文本信息");
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
