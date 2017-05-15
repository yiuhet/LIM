package com.example.yiuhet.lim.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.yiuhet.lim.adapter.ConversationAdapter;
import com.hyphenate.chat.EMConversation;

import java.util.Collections;
import java.util.List;

/**
 * Created by yiuhet on 2017/5/15.
 */

public class ConvercationItemTouchCallback extends ItemTouchHelper.Callback {

    private ConversationAdapter mConversationAdapter;
    private List<EMConversation> mData;


    public ConvercationItemTouchCallback(ConversationAdapter conversationAdapter, List<EMConversation> emConversations) {
        mConversationAdapter = conversationAdapter;
        mData = emConversations;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN; //上下拖拽
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END ;//左右滑动
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(mData, from, to);
        mConversationAdapter.notifyItemMoved(from, to);
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        mData.remove(pos);
        mConversationAdapter.notifyItemRemoved(pos);
    }



    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            ConversationAdapter.ConversationItemHolder holder = (ConversationAdapter.ConversationItemHolder) viewHolder;
            holder.mConversationItemView.setBackgroundColor(0xffbcbcbc);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ConversationAdapter.ConversationItemHolder holder = (ConversationAdapter.ConversationItemHolder) viewHolder;
        holder.mConversationItemView.setBackgroundColor(0xffeeeeee);
    }
}
