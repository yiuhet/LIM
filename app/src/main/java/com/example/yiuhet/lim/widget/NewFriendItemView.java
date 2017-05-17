package com.example.yiuhet.lim.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.model.Contact;
import com.example.yiuhet.lim.utils.AddFromViewListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yiuhet on 2017/5/11.
 */

public class NewFriendItemView extends RelativeLayout {
    @BindView(R.id.user_iv)
    ImageView mUserIv;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    AddFromViewListener mAddFromViewListener;

    public NewFriendItemView(Context context) {
        this(context, null);
    }

    public NewFriendItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_newfriend_item, this);
        ButterKnife.bind(this, this);
    }


    public void bindView(String contact, AddFromViewListener mAddFromViewListener,boolean isAdd) {
        this.mAddFromViewListener = mAddFromViewListener;
        mUserName.setText(contact);
        if (isAdd) {
            mBtnAdd.setEnabled(false);
            mBtnAdd.setText("已添加");
        }
    }
    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        if (mAddFromViewListener != null) {
            mAddFromViewListener.add(mUserName.getText().toString());
        }
    }


}
