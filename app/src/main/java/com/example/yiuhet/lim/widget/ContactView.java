package com.example.yiuhet.lim.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yiuhet on 2017/5/10.
 */

public class ContactView extends RelativeLayout {
    @BindView(R.id.user_iv)
    ImageView mUserIv;
    @BindView(R.id.user_name)
    TextView mUserName;

    public ContactView(Context context) {
        this(context, null);
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_contact_item, this);
        ButterKnife.bind(this,this);
    }

    public void bindView(Contact contact) {
        mUserName.setText(contact.userName);
    }
}
