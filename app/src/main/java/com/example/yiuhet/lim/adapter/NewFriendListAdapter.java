package com.example.yiuhet.lim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.yiuhet.lim.model.Contact;
import com.example.yiuhet.lim.utils.AddFromViewListener;
import com.example.yiuhet.lim.widget.NewFriendItemView;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/11.
 */

public class NewFriendListAdapter extends RecyclerView.Adapter<NewFriendListAdapter.NewFriendHolder>{

    private Context mContext;
    private List<String> mContact;
    private AddFromViewListener mAddFromViewListener;

    public NewFriendListAdapter(Context context, List<String> contact, AddFromViewListener listener) {
        mContext = context;
        mContact = contact;
        mAddFromViewListener = listener;
    }

    @Override
    public NewFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewFriendItemView newFriendItemView = new NewFriendItemView(mContext);
        return new NewFriendHolder(newFriendItemView);
    }

    @Override
    public void onBindViewHolder(NewFriendHolder holder, int position) {
        Log.d("ccccc","onBindViewHolder   "+mContact);
        holder.mNewFriendItemView.bindView(mContact.get(position),mAddFromViewListener);
    }

    @Override
    public int getItemCount() {
        return  mContact.size() ;
    }

    public class NewFriendHolder extends RecyclerView.ViewHolder {

            public NewFriendItemView mNewFriendItemView;

            public NewFriendHolder(NewFriendItemView itemView) {
                super(itemView);
                mNewFriendItemView = itemView;
            }
    }

}
