package com.example.yiuhet.lim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiuhet.lim.widget.ReceiveMessageItemView;
import com.example.yiuhet.lim.widget.SendMessageItemView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/9.
 */

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context mContext;

    private List<EMMessage> mMessageList;

    private static final int SEND_MESSAGE = 0;
    private static final int RECEIVE_MESSAGE = 1;

    public MessageListAdapter(Context context, List<EMMessage> emMessages) {
        mContext = context;
        mMessageList = emMessages;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SEND_MESSAGE) {
            return new SendItemViewHolder(new SendMessageItemView(mContext));
        } else {
            return new ReceiveItemViewHolder(new ReceiveMessageItemView(mContext));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean showTime = false;
        if (position == 0 || isShow(position)) {
            showTime = true;
        }
        if (holder instanceof SendItemViewHolder) {
            ((SendItemViewHolder) holder).mSendMessageItemView.binView(mMessageList.get(position), showTime);
        } else {
            ((ReceiveItemViewHolder) holder).mReceiveMessageItemView.binView(mMessageList.get(position), showTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = mMessageList.get(position);
        return message.direct() == EMMessage.Direct.SEND ? SEND_MESSAGE : RECEIVE_MESSAGE;
    }

    private boolean isShow(int position) {
        Long currentTime = mMessageList.get(position).getMsgTime();
        Long preTime = mMessageList.get(position - 1).getMsgTime();
        return !DateUtils.isCloseEnough(currentTime, preTime);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void addNewMessage(EMMessage message) {
        mMessageList.add(message);
        notifyDataSetChanged();
    }

    public class ReceiveItemViewHolder extends RecyclerView.ViewHolder {
        public ReceiveMessageItemView mReceiveMessageItemView;
        public ReceiveItemViewHolder(ReceiveMessageItemView itemView) {
            super(itemView);
            mReceiveMessageItemView = itemView;
        }
    }

    public class SendItemViewHolder extends RecyclerView.ViewHolder {
        public  SendMessageItemView mSendMessageItemView;
        public SendItemViewHolder(SendMessageItemView itemView) {
            super(itemView);
            mSendMessageItemView = itemView;
        }
    }
}
