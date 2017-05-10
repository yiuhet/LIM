package com.example.yiuhet.lim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.yiuhet.lim.model.Contact;
import com.example.yiuhet.lim.widget.ContactView;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/10.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private Context mContext;
    private List<Contact> mContactList;
    private OnItemClickListener mItemClickListener;

    public ContactListAdapter(Context context, List<Contact> contactList) {
        mContext = context;
        mContactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactView view = new ContactView(mContext);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        final Contact contact = mContactList.get(position);
        holder.mContactView.bindView(contact);
        holder.mContactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(contact.userName);
                }
            }
        });
        holder.mContactView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemLongClick(contact.userName);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        public ContactView mContactView;
        public ContactViewHolder(ContactView itemView) {
            super(itemView);
            mContactView = itemView;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String userName);
        void onItemLongClick(String userName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}
