package com.example.yiuhet.lim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiuhet.lim.view.ConversationView;
import com.example.yiuhet.lim.widget.ConvercationItemView;
import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/8.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationItemHolder>{
    public Context mContext;
    public List<EMConversation> mConversations;

    public ConversationAdapter(Context mContext,List<EMConversation> conversations) {
        this.mContext = mContext;
        mConversations = conversations;
    }

    @Override
    public ConversationItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConversationItemHolder(new ConvercationItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ConversationItemHolder holder, int position) {
        holder.mConversationItemView.bindView(mConversations.get(position));
    }


    @Override
    public int getItemCount() {
        return mConversations.size();
    }

    public class ConversationItemHolder extends RecyclerView.ViewHolder{

        public ConvercationItemView mConversationItemView;

        public ConversationItemHolder(ConvercationItemView itemView) {
            super(itemView);
            mConversationItemView = itemView;
        }
    }
}
